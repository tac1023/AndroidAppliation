package edu.unh.cs.cs619.bulletzone.repository;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import edu.unh.cs.cs619.bulletzone.model.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.Soldier;
import edu.unh.cs.cs619.bulletzone.model.Tank;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.WaterCurrent;

import static edu.unh.cs.cs619.bulletzone.model.Direction.Up;

/**
 *
 * Handles remember states of the game.
 * @author ???, Tyler Currier
 * @version 2.1
 * @since 5/2/18
 *
 */
@Component
public class InMemoryGameRepository implements GameRepository {

    /**
     * Field dimensions
     */
    private static final int FIELD_DIM = 16;

    /**
     * Tank's default life [life]
     */
    private static final int TANK_LIFE = 100;
    private final AtomicLong idGenerator = new AtomicLong();
    private final Monitor monitor = Monitor.getInstance();
    private Game game = null;


    /**
     *  Join creates a unique tank id and tank for new players.
     *
     * @param ip IP address of new player
     * @return Tank
     */
    @Override
    public Tank join(String ip) {
        synchronized (this.monitor) {
            Tank tank;
            if (game == null) {
                this.create();
            }

            if( (tank = game.getTank(ip)) != null){
                return tank;
            }

            Long tankId = this.idGenerator.getAndIncrement();

            tank = new Tank(tankId, Up, ip);
            tank.setLife(TANK_LIFE);

            Random random = new Random();
            int x;
            int y;

            // This may run for forever.. If there is no free space. XXX
            for (; ; ) {
                x = random.nextInt(FIELD_DIM);
                y = random.nextInt(FIELD_DIM);
                FieldHolder fieldElement = game.getHolderGrid().get(x * FIELD_DIM + y);
                if (!fieldElement.isPresent() && !fieldElement.isTankPresent()) {
                    //fieldElement.setFieldEntity(tank);
                    fieldElement.setTankEntity(tank);
                    tank.setParent(fieldElement);
                    break;
                }
            }

            game.addTank(ip, tank);

            return tank;
        }
    }

    /**
     * Reset the Timer for the given IP address
     *
     * @param ip IP address of player
     */
    public void resetTimer(String ip)
    {
        game.resetTimer(ip);
    }

    /**
     * Gets the newest version of the grid stored in game.
     *
     * @return The 2D array stored in Game object
     */
    @Override
    public int[][] getGrid(long tankId) {
        synchronized (this.monitor) {
            if (game == null) {
                this.create();
            }
        }
        return game.getGrid2D(tankId);
    }

    /**
     * Gets the newest TankItem locations in the game.
     *
     * @return 2D array of TankItems
     */
    @Override
    public int[][] getTankGrid(long tankId)
    {
        synchronized (this.monitor) {
            if(game == null)
            {
                this.create();
            }
        }
        return game.getTankGrid2D(tankId);
    }

    /**
     * Removes the tankId and attempts to leave game. If successful the tank will disappear.
     *
     * @param tankId ID of tank to remove
     * @throws TankDoesNotExistException There is not such tank
     */
    @Override
    public void leave(long tankId)
        throws TankDoesNotExistException {

        synchronized (this.monitor) {
            if (!this.game.getTanks().containsKey(tankId)) {
                throw new TankDoesNotExistException(tankId);
            }

            System.out.println("leave() called, tank ID: " + tankId);

            Tank tank = game.getTanks().get(tankId);
            FieldHolder parent = tank.getParent();
            Soldier soldier = tank.getSoldier();
            FieldHolder sParent = soldier.getParent();

            if(parent != null)
                parent.clearTankItem();
            if(sParent != null)
                sParent.clearTankItem();

            //If in WaterCurrent, remove reference
            if(parent != null && parent.isPresent() && (parent.getEntity() instanceof WaterCurrent))
                ((WaterCurrent) parent.getEntity()).removeOccupier();

            game.removeTank(tankId);

            //When last player leaves, reset map
            if(game.getTanks().isEmpty())
                MapLoader.loadMap(game);
        }
    }

    /**
     * Creates a new game for new player.
     */
    private void create() {
        if (game != null) {
            return;
        }
        synchronized (this.monitor) {

            this.game = Game.getInstance();
            game.setGameRepository(this);
            //createFieldHolderGrid(game);
            MapLoader.createFieldHolderGrid(game);
            MapLoader.loadMap(game);
        }
    }

    /**
     * Test Version of create that loads an empty map.
     */
    public void createTest()
    {
        if(game != null)
        {
            return;
        }
        synchronized (this.monitor)
        {
            this.game = Game.getInstance();
            game.setGameRepository(this);
            MapLoader.createFieldHolderGrid(game);
        }
    }

    /**
     * Return whether or not the player is alive
     *
     * @param tankId ID of player to check
     * @return boolean
     */
    public boolean getIsAlive(long tankId) throws TankDoesNotExistException
    {
        Tank tank = game.getTanks().get(tankId);
        return (tank != null && tank.isAlive());
    }

    /**
     * Return the health of the requested tank
     *
     * @param id ID of tank whose health to get
     * @return int
     */
    public int getTankHealth(long id) throws TankDoesNotExistException
    {
        Tank tank = game.getTanks().get(id);
        if(tank == null)
            return 0;
        int ret = tank.getLife();
        if(ret < 0)
            ret = 0;
        return ret;
    }

    /**
     * Return the health of the requested soldier
     *
     * @param id ID of soldier whose health to get
     * @return int
     */
    public int getSoldierHealth(long id) throws TankDoesNotExistException
    {
        Tank tank = game.getTanks().get(id);
        if(tank == null)
            return 0;
        int ret = tank.getSoldier().getLife();
        if(ret < 0)
            ret = 0;
        return ret;
    }
}
