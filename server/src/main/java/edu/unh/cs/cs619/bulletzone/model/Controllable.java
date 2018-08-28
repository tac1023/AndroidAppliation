package edu.unh.cs.cs619.bulletzone.model;

/**
 * Interface that defines a controllable object, either a Vehicle
 * or a Soldier
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 5/1/2018
 */
public interface Controllable {

    /**
     * Return a copy of this Controllable object
     *
     * @return FieldEntity
     */
    FieldEntity copy();

    /**
     * Hit and do damage to this Controllable object
     *
     * @param damage damage to do
     */
    void hit(int damage);

    /**
     * Get the next time that this Controllable is allowed to move
     *
     * @return long
     */
    long getLastMoveTime();

    /**
     * Set the next time that this Controllable is allowed to move
     *
     * @param lastMoveTime new last move time
     */
    void setLastMoveTime(long lastMoveTime);

    /**
     * Get the allowed interval on which this Controllable is allowed to move
     *
     * @return long
     */
    long getAllowedMoveInterval();

    /**
     * Get the next time that this Controllable fired
     *
     * @return long
     */
    long getLastFireTime();

    /**
     * Set the next time that this Controllable is allowed to fire
     *
     * @param lastFireTime new last fire time
     */
    void setLastFireTime(long lastFireTime);

    /**
     * Get the allowed interval on which this Controllable is allowed to fire
     *
     * @return long
     */
    long getAllowedFireInterval();

    /**
     * Get the number of bullets that this Controllable has active
     *
     * @return ing
     */
    int getNumberOfBullets();

    /**
     * Set the number of bullets that this Controllable is allowed to
     * have active
     *
     * @param numberOfBullets new allowed number of bullets
     */
    void setNumberOfBullets(int numberOfBullets);

    /**
     * Get the number of bullets that this Controllable is allowed to
     * have active
     *
     * @return ing
     */
    int getAllowedNumberOfBullets();

    /**
     * Get the Controllable's direction
     *
     * @return Direction
     */
    Direction getDirection();

    /**
     * Set the Controllable's direction
     *
     * @param direction new direction
     */
    void setDirection(Direction direction);

    /**
     * Get the Controllable's ID
     *
     * @return long
     */
    long getId();

    /**
     * Get the Controllable's Integer Value
     *
     * @return ing
     */
    int getIntValue();

    /**
     * Get the Controllable's String representation
     *
     * @return String
     */
    String toString();

    /**
     * Get the Controllable's life
     *
     * @return ing
     */
    int getLife();

    /**
     * Set the Controllable's life
     *
     * @param life life of Controllable object
     */
    void setLife(int life);

    /**
     * Get the IP address of the player associated with this
     * Controllable
     *
     * @return String
     */
    String getIp();

    /**
     * Delete the Controllable's stored reference to the bullet with
     * this ID if applicable
     *
     * @param id ID of bullet whose reference to delete
     */
    void deleteBulletReference(Integer id);

    /**
     * Return whether the Controllable is currently moving between planes
     *
     * @return boolean
     */
    boolean isDrilling();

    /**
     * Set whether the Controllable is currently moving between planes
     *
     * @param drilling Is or Isn't drilling
     */
    void setDrilling(boolean drilling);

    /**
     * Return whether the Controllable is currently transforming
     *
     * @return boolean
     */
    boolean isTransforming();

    /**
     * Set whether the Controllable is currently transforming
     *
     * @param transforming Is or Isn't transforming
     */
    void setTransforming(boolean transforming);
}
