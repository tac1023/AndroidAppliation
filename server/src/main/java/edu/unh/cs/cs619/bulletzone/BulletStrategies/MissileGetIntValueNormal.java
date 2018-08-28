package edu.unh.cs.cs619.bulletzone.BulletStrategies;

import edu.unh.cs.cs619.bulletzone.model.Bullet;

/**
 * Strategy for getting a Missile's integer value
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 5/6/2018
 */

public class MissileGetIntValueNormal implements BulletGetIntValueStrategy {

    /**
     * Return the normal ID of this bullet.
     *
     * @param bullet Missile whose ID to return
     * @param tankId ID of tank to which missile belongs
     * @param damage Damage of bullet
     * @param direction direction of missile
     * @return int
     */
    public int getIntValue(Bullet bullet, int tankId, int damage, int direction)
    {
        return (10000 + tankId * 10 + direction);
    }
}
