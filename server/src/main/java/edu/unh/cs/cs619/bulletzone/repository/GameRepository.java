package edu.unh.cs.cs619.bulletzone.repository;

import edu.unh.cs.cs619.bulletzone.model.Tank;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;

/**
 * Interface that is used to handle actions of the game.
 * @author Simon, Tyler Currier
 * @version 2.0
 * @since 4/17/18
 */
public interface GameRepository {

    /**
     * Helps player to join game.
     *
     * @param ip IP address of new player
     * @return new tank
     */
    Tank join(String ip);

    /**
     * Reset timer for kicking players
     *
     * @param ip IP address of player
     */
    void resetTimer(String ip);

    /**
     * Gets the grid to update UI.
     *
     * @return the grid of game
     */
    int[][] getGrid(long tankId);

    /**
     * Gets tank grid to update UI.
     *
     * @return grid of player related items
     */
    int[][] getTankGrid(long tankId);

    /**
     * Handles leaving the player leaving the game.
     *
     * @param tankId ID of tank to remove from game
     * @throws TankDoesNotExistException There is not such tank
     */
    void leave(long tankId)
            throws TankDoesNotExistException;

    /**
     * Return whether the player is alive
     *
     * @param tankId ID of player's tank
     * @return boolean, true if alive
     * @throws TankDoesNotExistException There is not such tank
     */
    boolean getIsAlive(long tankId) throws TankDoesNotExistException;

    /**
     * Get the specified tank's health
     *
     * @param id ID of tank whose health to get
     * @return int
     */
    int getTankHealth(long id) throws TankDoesNotExistException;

    /**
     * Get the specified soldier's health
     *
     * @param id ID of soldier whose health to get
     * @return int
     */
    int getSoldierHealth(long id) throws TankDoesNotExistException;
}
