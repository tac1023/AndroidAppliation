package edu.unh.cs.cs619.bulletzone.BulletStrategies;

import edu.unh.cs.cs619.bulletzone.model.Bullet;

/**
 * This is the strategy for getting a bullet's ID value as it is
 * traveling normally.
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 4/28/2018
 */

public class BulletGetIntValueNormal implements BulletGetIntValueStrategy {

    /**
     * Return the normal ID of this bullet.
     *
     * @param bullet Bullet whose ID to return
     * @param tankId ID of tank to which bullet belongs
     * @param damage Damage of bullet
     * @param direction direction of bullet
     * @return int
     */
    public int getIntValue(Bullet bullet, int tankId, int damage, int direction)
    {
        return (2000000 + 1000 * tankId + damage * 10 + direction);
    }
}
