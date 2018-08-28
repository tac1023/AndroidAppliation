package edu.unh.cs.cs619.bulletzone.model;

import edu.unh.cs.cs619.bulletzone.tank_action.Action;

/**
 * Defines the interface for a tank state
 *
 * @author Jasson Vettese, Tyler Currier
 * @version 1.1
 * @since 5/2/2018
 */

public interface TankStateInterface {
    /**
     * Return allowed turn interval
     *
     * @return long
     */
    long getAllowedTurnInterval();

    /**
     * Return allowed move interval
     *
     * @return long
     */
    long getAllowedMoveInterval();

    /**
     * Return allowed fire interval
     *
     * @return long
     */
    long getAllowedFireInterval();

    /**
     * Return allowed number of bullets
     *
     * @return int
     */
    int getAllowedNumberOfBullets();

    /**
     * Return Integer Value
     *
     * @param tank Tank of player
     * @param id ID of player
     * @param life life of player
     * @param direction current direction
     * @return int
     */
    int getIntValue(Tank tank, long id, int life, Direction direction);

    /**
     * Switch getIntValue to on fire strategy
     */
    void catchFire();

    /**
     * Switch getIntValue to explode strategy
     */
    void explode();

    /**
     * Return allowed eject interval
     *
     * @return long
     */
    long getAllowedEjectInterval();

    /**
     * Return whether it is a Tank state
     *
     * @return boolean
     */
    boolean isTankState();

    /**
     * Return whether it is a Ship state
     *
     * @return boolean
     */
    boolean isShipState();

    /**
     * Return whether the vehicle can enter the given field holder
     *
     * @param nextField holder to enter
     * @return boolean
     */
    boolean canEnter(FieldHolder nextField);

    /**
     * As a Tunneler, Drill down or up if there is no tunnel, or just travel through the
     * tunnel if there is one. As a tank, travel through the tunnel if there is one. As
     * a ship, return false.
     *
     * @param action Reference to action
     * @param tank Tank to reference
     * @return boolean
     */
    boolean drill(Action action, Tank tank);

    /**
     * Return the Bullet Damage that this vehicle has
     *
     * @return int
     */
    int getBulletDamage();

    /**
     * Sets the allowed fire interval for multi-directional firing.
     *
     * @param allowedFireInterval new allowed fire interval
     */
    void setAllowedFireInterval( int allowedFireInterval);
}
