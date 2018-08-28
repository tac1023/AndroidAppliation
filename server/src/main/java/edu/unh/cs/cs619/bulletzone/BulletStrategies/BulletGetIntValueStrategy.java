package edu.unh.cs.cs619.bulletzone.BulletStrategies;

import edu.unh.cs.cs619.bulletzone.model.Bullet;

/**
 * Common interface for the BulletGetIntValue strategy pattern
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 4/28/2018
 */

public interface BulletGetIntValueStrategy {

    /**
     * Defines the method for return the int value of a bullet
     *
     * @param bullet Bullet whose ID to return
     * @param tankId Tank to which bullet belongs
     * @param damage Damage of bullet
     * @param direction direction of bullet
     * @return int
     */
    int getIntValue(Bullet bullet, int tankId, int damage, int direction);
}
