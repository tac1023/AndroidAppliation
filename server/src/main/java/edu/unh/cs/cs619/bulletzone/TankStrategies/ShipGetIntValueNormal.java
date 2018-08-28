package edu.unh.cs.cs619.bulletzone.TankStrategies;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Tank;

/**
 * Defines strategy for getting the integer value of a normal ship
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 5/6/2018
 */
public class ShipGetIntValueNormal implements TankGetIntValueStrategy {

    /**
     * Get integer value for a normal ship
     *
     * @param tank Tank
     * @param id ID of tank
     * @param life life of tank
     * @param direction direction of tank
     * @return int
     */
    public int getIntValue(Tank tank, long id, int life, Direction direction)
    {
        return (int) (30000000 + 10000 * id + 10 * life + Direction
                .toByte(direction));
    }

    /**
     * Return that this isn't the on fire strategy
     *
     * @return false
     */
    public boolean isOnFire()
    {
        return false;
    }

    /**
     * Return that this is the normal strategy
     *
     * @return ture
     */
    public boolean isNormal()
    {
        return true;
    }
}
