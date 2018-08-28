package edu.unh.cs.cs619.bulletzone.TankStrategies;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Tank;

/**
 * Define strategy for getting the integer value of a burning ship
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 5/6/2018
 */

public class ShipGetIntValueOnFire implements TankGetIntValueStrategy {

    private int value = -1;

    /**
     * get integer value of burning ship. This counts down. When countdown finishes,
     * switch to explode state
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
            value = 5000000 + 1000 * (int)id + 100 * Direction.toByte(direction) + 40;
        int ret = value--;
        if(value % 100 == 0)
            tank.explode();
        return ret;
    }

    /**
     * Return that this is the on fire strategy
     *
     * @return true
     */
    public boolean isOnFire()
    {
        return true;
    }

    /**
     * Return that this isn't the normal strategy
     *
     * @return
     */
    public boolean isNormal()
    {
        return false;
    }
}
