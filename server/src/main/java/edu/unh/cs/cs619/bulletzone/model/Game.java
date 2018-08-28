package edu.unh.cs.cs619.bulletzone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import edu.unh.cs.cs619.bulletzone.repository.GameRepository;

/**
 * The Game itself.
 *
 * @author Simon, Tyler Currier
 * @version 3.1
 * @since 4/17/18
 */
public final class Game {
    /**
     * Field dimensions
     */
    private static final int FIELD_DIM = 16;
    private final long id;
    private final ArrayList<FieldHolder> holderGrid = new ArrayList<>();
    private final ArrayList<FieldHolder> subHolderGrid = new ArrayList<>();

    private final ConcurrentMap<Long, Tank> tanks = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Long> playersIP = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long>ipTimers = new ConcurrentHashMap<>();
    private final Vector<String>ipList = new Vector<>();

    private volatile GameRepository gameRepository;

    private static Game _instance;

    /**
     * Constructor.
     */
    private Game() {
        this.id = 0;
        incrementTimers();
    }

    /**
     * Make instance if there isn't one already and return the instance
     *
     * @return Game
     */
    public static Game getInstance()
    {
        if(_instance == null)
        {
            _instance = new Game();
        }
        return _instance;
    }

    /**
     * Reset the timer for the given IP
     *
     * @param ip IP whose timer to reset
     */
    public void resetTimer(String ip)
    {
        ipTimers.put(ip, (long)0);
    }

    /**
     * Set a reference to game repository to handle kicking of players whose
     * games probably crashed or who just left the app.
     *
     * @param gameRepository reference to game repository
     */
    public void setGameRepository(GameRepository gameRepository)
    {
        this.gameRepository = gameRepository;
    }

    /**
     * Gets the id of the player.
     *
     * @return id
     */
    @JsonIgnore
    public long getId() {
        return id;
    }

    /**
     * Gets the holder for the grid.
     *
     * @return underlying arrayList
     */
    @JsonIgnore
    public ArrayList<FieldHolder> getHolderGrid() {
        return holderGrid;
    }

    /**
     * Gets the holder for the subterranean grid
     *
     * @return underlying arrayList
     */
    @JsonIgnore
    public ArrayList<FieldHolder> getSubHolderGrid()
    {
        return subHolderGrid;
    }

    /**
     * Adds the tank to game.
     *
     * @param ip IP of player
     * @param tank player's new tank
     */
    public void addTank(String ip, Tank tank) {
        synchronized (tanks) {
            tanks.put(tank.getId(), tank);
            playersIP.put(ip, tank.getId());
            ipTimers.put(ip, (long)0);
            ipList.add(ip);
        }
    }

    /**
     * Checks to see if Tank exists and returns it
     *
     * @param tankId ID of tank to get
     * @return Tank
     */
    private Tank getTank(long tankId) {
        return tanks.get(tankId);
    }

    /**
     * Map of tanks in the game.
     *
     * @return the concurrent map
     */
    public ConcurrentMap<Long, Tank> getTanks() {
        return tanks;
    }

    /**
     * Gets the tank.
     *
     * @param ip IP address of player
     * @return the tank
     */
    public Tank getTank(String ip){
        if (playersIP.containsKey(ip)){
            return tanks.get(playersIP.get(ip));
        }
        return null;
    }

    /**
     * Removes the tank from the game.
     *
     * @param tankId ID of tank
     */
    public void removeTank(long tankId){
        synchronized (tanks) {
            Tank t = tanks.remove(tankId);
            if (t != null) {
                playersIP.remove(t.getIp());
                ipTimers.remove(t.getIp());
                ipList.remove(t.getIp());
            }
        }
    }

    /**
     * Returns map item grid as 2D array.
     *
     * @return 2D array of map items
     */
    public int[][] getGrid2D(long tankId) {
        int[][] grid = new int[FIELD_DIM][FIELD_DIM];

        synchronized (holderGrid) {
            FieldHolder holder;
            for (int i = 0; i < FIELD_DIM; i++) {
                for (int j = 0; j < FIELD_DIM; j++) {
                    if(aboveOrBelow(tankId))
                        holder = holderGrid.get(i * FIELD_DIM + j);
                    else
                        holder = subHolderGrid.get(i * FIELD_DIM + j);

                    if (holder.isPresent()) {
                        grid[i][j] = holder.getEntity().getIntValue();
                    } else {
                        grid[i][j] = 0;
                    }
                }
            }
        }

        return grid;
    }

    /**Returns player item grid as 2D array
     *
     * @return 2D array of player items
     */
    public int[][] getTankGrid2D(long tankId) {
        int[][] grid = new int[FIELD_DIM][FIELD_DIM];

        synchronized (holderGrid){
            FieldHolder holder;
            for(int i = 0; i < FIELD_DIM; i++)
            {
                for(int j = 0; j < FIELD_DIM; j++)
                {
                    if(aboveOrBelow(tankId))
                        holder = holderGrid.get(i * FIELD_DIM + j);
                    else
                        holder = subHolderGrid.get(i * FIELD_DIM + j);

                    if(holder.isTankPresent())
                    {
                        if(censorSoldiers(tankId, holder))
                            grid[i][j] = 0;
                        else
                            grid[i][j] = holder.getTankItem().getIntValue();
                    }
                    else
                    {
                        grid[i][j] = 0;
                    }
                }
            }
        }
        
        return grid;
    }

    /**
     * If a soldier is in a forest and that soldier doesn't belong to
     * the client asking for an update, remove that soldier's ID from
     * the return array
     *
     * @param tankId ID of client's soldier
     * @param holder current holder
     * @return boolean
     */
    private boolean censorSoldiers(long tankId, FieldHolder holder)
    {
        if(holder.getTankItem() instanceof Soldier) {
            if (holder.isPresent() && holder.getEntity() instanceof DebrisField) {
                if (((DebrisField) holder.getEntity()).isForest()) {
                    if(((Soldier)holder.getTankItem()).getId() != tankId)
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * Return whether to return the above ground portion of the
     * map or below ground portion of the map
     *
     * @param tankId ID of player to check
     * @return true for above, false for below
     */
    private boolean aboveOrBelow(long tankId)
    {
        Tank tank = getTank(tankId);
        if(tank != null)
        {
            if(tank.getCurrentAction().isTankAction())
            {
                return tank.getParent().getNeighbor(Direction.Above) == null;
            }
            else if(tank.getCurrentAction().isSoldierAction())
            {
                return tank.getSoldier().getParent().getNeighbor(Direction.Above) == null;
            }
            else
            {
                return tank.getParent().getNeighbor(Direction.Above) == null;
            }
        }
        return true;
    }

    /**
     * This thread periodically increments timers for each tank. The player's
     * timer is constantly resent every time their client uses the "grid" function.
     * If they disconnect without leaving the game, their timer here will continue
     * to increment. If it reaches 3, they will be removed from the game.
     */
    private void incrementTimers()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    long doneTime = System.currentTimeMillis() + 5000;
                    while(System.currentTimeMillis() < doneTime){}
                    for(int i = 0; i < ipList.size(); i++)
                    {
                        String ip = ipList.get(i);
                        ipTimers.put(ip, ipTimers.get(ip) + 1);
                        if(ipTimers.get(ip) > 2) {
                            try {
                                gameRepository.leave(playersIP.get(ip));
                            }
                            catch(TankDoesNotExistException e)
                            {
                                System.err.println("Failed to remove Tank");
                            }
                        }
                    }
                }
            }
        }).start();
    }
}
