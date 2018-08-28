package edu.unh.cs.cs619.bulletzone.TankStrategies;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Tank;

/**
 * Interface defining getIntStrategy for tanks
 *
 * @author Tyler currier
 * @version 1.0
 * @since 4/28/2018
 */

public interface TankGetIntValueStrategy {

    /**
     * Return Integer Value of the tank
     *
     * @param tank Tank
     * @param id ID of tank
     * @param life life of tank
     * @param direction direction of tank
     * @return int
     */
    int getIntValue(Tank tank, long id, int life, Direction direction);

    /**
     * Return whether the current strategy is on fire
     *
     * @return boolean
     */
    boolean isOnFire();

    /**
     * Return whether the current strategy is normal
     *
     * @return boolean
     */
    boolean isNormal();
}
