package edu.unh.cs.cs619.bulletzone.model;

import edu.unh.cs.cs619.bulletzone.BulletStrategies.BulletGetIntValueExplode;
import edu.unh.cs.cs619.bulletzone.BulletStrategies.BulletGetIntValueNormal;
import edu.unh.cs.cs619.bulletzone.BulletStrategies.BulletGetIntValueStrategy;

/**
 * The Bullet object.
 * @author ???, Tyler Currier
 * @version 2.0
 * @since 4/17/18
 */
public class Bullet extends FieldEntity {

    protected long tankId;
    protected Direction direction;
    protected int damage;
    int bulletId;
    private Integer soldierBulletId;
    private boolean cleared = false;
    private boolean exploding = false;
    BulletGetIntValueStrategy valueStrategy;

    public Bullet()
    {
        this.damage = 30;
    }

    /**
     * Constructor
     * @param tankId ID of tank whose bullet this is
     * @param direction direction to fire bullet
     * @param damage damage this bullet carries
     */
    public Bullet(long tankId, Direction direction, int damage) {
        this.damage = damage;
        this.setTankId(tankId);
        this.setDirection(direction);
        valueStrategy = new BulletGetIntValueNormal();
        bulletId = 0;
    }

    /**
     * gets the value of bullet.
     *
     * @return int
     */
    @Override
    public int getIntValue() {
        return valueStrategy.getIntValue(this, (int)tankId, damage, Direction.toByte(direction));
    }

    /**
     * Start explosion animation
     */
    public void explode() {
        exploding = true;
        valueStrategy = new BulletGetIntValueExplode();
    }

    /**
     * Return whether or not the bullet is exploding
     *
     * @return boolean
     */
    public boolean isExploding()
    {
        return exploding;
    }

    /**
     * Returns the string.
     *
     * @return "B"
     */
    @Override
    public String toString() {
        return "B";
    }

    /**
     * Creates a new bullet
     *
     * @return identical bullet
     */
    @Override
    public FieldEntity copy() {
        return new Bullet(tankId, direction, damage);
    }

    /**
     * Gets the associated tank's id
     *
     * @return tankID
     */
    public long getTankId() {
        return tankId;
    }

    /**
     * Pairs bullet with tank.
     *
     * @param tankId ID of tank to which this belongs
     */
    public void setTankId(long tankId) {
        this.tankId = tankId;
    }

    /**
     * Gets the direction that the bullet is going in.
     *
     * @return Direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the direction that the bullet is going in.
     *
     * @param direction direction this bullet is traveling
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Gets the damage of the bullet.
     *
     * @return int
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Sets the damage of the bullet.
     *
     * @param damage damage value of this bullet
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Set ID for tracking soldier bullets
     *
     * @param bulletId ID
     */
    public void setSoldierBulletId(Integer bulletId)
    {
        soldierBulletId = bulletId;
    }

    /**
     * return ID for tracking soldier bullets
     *
     * @return ID
     */
    public Integer getSoldierBulletId()
    {
        return soldierBulletId;
    }

    /**
     * Set whether this bullet, if a soldier's bullet, has been cleared
     * by firing from a tank
     */
    void setCleared()
    {
        this.cleared = true;
    }

    /**
     * return whether this bullet, if a soldier's bullet, has been cleared
     * by firing form a tank
     *
     * @return true for cleared
     */
    public boolean isCleared()
    {
        return cleared;
    }

    /**
     * Return that this is not a Missile
     *
     * @return false
     */
    public boolean isMissile()
    {
        return false;
    }
}
