package edu.unh.cs.cs619.bulletzone.TankStrategies;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Tank;

/**
 * On fire strategy for tank
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 4/28/2018
 */

public class TankGetIntValueOnFire implements TankGetIntValueStrategy {

    private int value = -1;

    /**
     * Return value then decrement by 1. If the bottom of the accepted range
     * is reached, switch to the explode strategy
     *
     * @param tank Tank
     * @param id ID of tank
     * @param life life of tank
     * @param direction direction of tank
     * @return int
     */
    public int getIntValue(Tank tank, long id, int life, Direction direction)
    {
        if(value == -1)
            value = 3000000 + 1000 * (int)id + 100 * Direction.toByte(direction) + 40;
        int ret = value--;
        if(value % 100 == 0)
            tank.explode();
        return ret;
    }

    /**
     * Return that this is the onFireStrategy
     *
     * @return true
     */
    public boolean isOnFire()
    {
        return true;
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
}
