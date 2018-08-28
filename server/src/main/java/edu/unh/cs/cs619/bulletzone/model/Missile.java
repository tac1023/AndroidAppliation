package edu.unh.cs.cs619.bulletzone.model;

import edu.unh.cs.cs619.bulletzone.BulletStrategies.MissileGetIntValueNormal;

/**
 * Server side representation of a missile
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 5/6/2018
 */
public class Missile extends Bullet {

    private int splashDamage1, splashDamage2;
    private Direction shift = null;

    /**
     * Constructor with an associated tank ID and a direction
     * in which to travel
     *
     * @param tankId ID of parent tank
     * @param direction Direction of travel
     */
    Missile(long tankId, Direction direction)
    {
        this.tankId = tankId;
        this.direction = direction;
        this.damage = 50;
        this.splashDamage1 = 35;
        this.splashDamage2 = 25;
        this.valueStrategy = new MissileGetIntValueNormal();
        this.bulletId = 0;
    }

    /**
     * Return first level splash damage
     *
     * @return int
     */
    public int getSplashDamage1()
    {
        return splashDamage1;
    }

    /**
     * Return second level splash damage
     *
     * @return int
     */
    public int getSplashDamage2()
    {
        return splashDamage2;
    }

    /**
     * Set the next shift direction
     *
     * @param direction direction to shift
     */
    public void setShift(Direction direction)
    {
        this.shift = direction;
    }

    /**
     * Get and clear the last shift direction
     *
     * @return Direction
     */
    public Direction getShift()
    {
        Direction ret = shift;
        shift = null;
        return ret;
    }

    /**
     * Return that this is a Missile
     *
     * @return false
     */
    @Override
    public boolean isMissile()
    {
        return true;
    }
}
