package edu.unh.cs.cs619.bulletzone.TankStrategies;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Tank;

/**
 * Explode strategy for tank
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 4/28/2018
 */

public class TankGetIntValueExplode implements TankGetIntValueStrategy {
    private int value = 100;

    /**
     * Return the current explode value for a tank then decrement one.
     * Remove tank when finished.
     *
     * @param tank Tank to explode
     * @param id ID of tank to explode
     * @param life life of tank
     * @param direction direction of tank
     * @return int
     */
    public int getIntValue(Tank tank, long id, int life, Direction direction)
    {
        int ret = value;
        value--;
        if(value < 93)
            clear(tank);
        return ret;
    }

    /**
     * Return that this isn't the onFireStrategy
     *
     * @return false
     */
    public boolean isOnFire()
    {
        return false;
    }

    /**
     * Return that this isn't the normalStrategy
     *
     * @return false
     */
    public boolean isNormal()
    {
        return false;
    }

    /**
     * Remove the tank from the game
     *
     * @param tank Tank to remove
     */
    private void clear(Tank tank)
    {
        tank.getParent().clearTankItem();
        if (!tank.isSoldierOut())
            tank.setIsAlive(); //this sets it to false
    }
}
