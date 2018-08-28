package edu.unh.cs.cs619.bulletzone.BulletStrategies;

import edu.unh.cs.cs619.bulletzone.model.Bullet;

/**
 * This is the strategy for getting a bullet's ID value as it is
 * exploding.
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 4/28/2018
 */

public class BulletGetIntValueExplode implements BulletGetIntValueStrategy {

    private int value = 100;

    /**
     * Send back the current value of the explosion cycle then decrease the value
     * to go to the next part of the cycle
     *
     * @param bullet The bullet that is exploding
     * @param tankId ID of tank to which this bullet belongs
     * @param damage The damage of this bullet
     * @param direction direction of this bullet
     * @return int
     */
    public int getIntValue(Bullet bullet, int tankId, int damage, int direction) {
        int ret = value;
        value--;
        if(value < 93)
            clear(bullet);
        return ret;
    }

    /**
     * The bullet has finished exploding, so clear it from the game.
     *
     * @param bullet Bullet to clear
     */
    private void clear(Bullet bullet)
    {
        bullet.getParent().clearTankItem();
    }

}
