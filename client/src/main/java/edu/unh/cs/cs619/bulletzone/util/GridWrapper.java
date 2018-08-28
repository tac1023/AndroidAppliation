package edu.unh.cs.cs619.bulletzone.util;

/**
 * Encapsulates data inside of this wrapper class so multiple variables
 * of different types can be returned as a "grid".
 *
 * @author Simon, Tyler Currier
 * @version 2.0
 * @since 4/17/18
 */
public class GridWrapper {
    private int[][] grid;
    private int[][] tankGrid;
    private boolean isAlive;
    private long timeStamp;
    private int tankHealth;
    private int soldierHealth;

    /**
     * Default Constructor
     */
    public GridWrapper() {
    }

    /**
     * Constructor that sets the grid field of this wrapper
     *
     * @param grid The grid to include
     */
    public GridWrapper(int[][] grid, int[][] tankGrid, boolean isAlive, int tHealth, int sHealth) {
        this.grid = grid;
        this.tankGrid = tankGrid;
        this.isAlive = isAlive;
        this.tankHealth = tHealth;
        this.soldierHealth = sHealth;
    }

    /**
     * Getter that returns the grid field of map items
     *
     * @return This wrapper's grid
     */
    public int[][] getGrid() {
        return this.grid;
    }


    /**
     * Getter that returns the tankGrid field of player items
     *
     * @return This wrapper's tankGrid
     */
    public int[][] getTankGrid()
    {
        return this.tankGrid;
    }

    /**
     * Sets this wrapper's grid field
     *
     * @param grid The grid to give to this wrapper
     */
    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    /**
     * Sets this wrapper's tankGrid field
     *
     * @param tankGrid The tankGrid to give to this wrapper
     */
    public void setTankGrid(int[][] tankGrid)
    {
        this.tankGrid = tankGrid;
    }

    /**
     * Getter for the timestamp of this wrapper.
     *
     * @return The timeStamp field
     */
    public long getTimeStamp() {
        return timeStamp;
    }

    /**
     * Setter for the timeStamp of this wrapper.
     *
     * @param timeStamp The timeStamp to give to this wrapper
     */
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Setter for whether or not player is alive
     *
     * @param alive boolean
     */
    public void setAlive(boolean alive)
    {
        isAlive = alive;
    }

    /**
     * Getter for whether or not player is alive
     *
     * @return boolean
     */
    public boolean isAlive()
    {
        return isAlive;
    }

    /**
     * Get the health of the requested tank
     *
     * @return int
     */
    public int getTankHealth()
    {
        return tankHealth;
    }

    /**
     * get the health of the requested soldier
     *
     * @return int
     */
    public int getSoldierHealth()
    {
        return soldierHealth;
    }
}
