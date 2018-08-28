package edu.unh.cs.cs619.bulletzone.model;

import edu.unh.cs.cs619.bulletzone.TankStrategies.ShipGetIntValueNormal;
import edu.unh.cs.cs619.bulletzone.TankStrategies.ShipGetIntValueOnFire;
import edu.unh.cs.cs619.bulletzone.TankStrategies.TankGetIntValueExplode;
import edu.unh.cs.cs619.bulletzone.TankStrategies.TankGetIntValueStrategy;
import edu.unh.cs.cs619.bulletzone.tank_action.Action;

/**
 * Defines what a Tank should do when it is in the Ship state
 *
 * @author Jason Vetesse, Tyler Currier
 * @version 1.1
 * @since 5/2/2018
 */
public class ShipState implements TankStateInterface {
    //private final long id;

    //private final String ip;
    private int allowedMoveInterval;
    private int allowedTurnInterval;
    private int allowedFireInterval;
    private int allowedNumberOfBullets;
    private int allowedEjectInterval;
    private int bulletDamage;

    private TankGetIntValueStrategy intValueStrategy;


    /**
     * Constructor initializes all variables that were declared.
     */
    ShipState() {
        allowedNumberOfBullets = 8;
        allowedFireInterval = 500;
        allowedMoveInterval = 600;
        allowedTurnInterval = 300;
        allowedEjectInterval = 3000;
        bulletDamage = 25;
        intValueStrategy = new ShipGetIntValueNormal();
    }

    /**
     * Return the allowedTurnInterval for a ship
     *
     * @return long
     */
    public long getAllowedTurnInterval() {
        return allowedTurnInterval;
    }

    /**
     * Return the allowedMoveInterval for a ship
     *
     * @return long
     */
    public long getAllowedMoveInterval() {
        return allowedMoveInterval;
    }

    /**
     * Return teh allowedFireInterval for a ship
     *
     * @return long
     */
    public long getAllowedFireInterval() {
        return allowedFireInterval;
    }

    /**
     * Return the allowedNumberOFBullets for a ship
     *
     * @return int
     */
    public int getAllowedNumberOfBullets() {
        return allowedNumberOfBullets;
    }

    /**
     * Return the Integer Value for a ship
     *
     * @param tank Tank that is this ship
     * @param id ID of player
     * @param life life of this ship
     * @param direction Direction facing right now
     * @return int
     */
    public int getIntValue(Tank tank, long id, int life, Direction direction)
    {
        return intValueStrategy.getIntValue(tank, id, life, direction);
    }

    /**
     * Set getIntValue to the CatchFire Strategy (Not yet implemented)
     */
    public void catchFire()
    {
        if(intValueStrategy.isNormal())
            intValueStrategy = new ShipGetIntValueOnFire();
    }

    /**
     * Set getIntValue to the Explode Strategy (Not yet implemented)
     */
    public void explode()
    {
        if(intValueStrategy.isOnFire())
            intValueStrategy = new TankGetIntValueExplode();
    }

    /**
     * Get allowedEjectInterval for a ship
     *
     * @return long
     */
    public long getAllowedEjectInterval() {
        return allowedEjectInterval;
    }

    /**
     * Return whether this is a tank state. It's not, but this helps to avoid
     * using instanceof.
     *
     * @return boolean, always false
     */
    public boolean isTankState()
    {
        return false;
    }

    /**
     * Return that this is a ship state
     *
     * @return true
     */
    public boolean isShipState()
    {
        return true;
    }

    /**
     * Return whether a ship is allowed to enter the given holder
     *
     * @param nextField holder to enter
     * @return boolean
     */
    public boolean canEnter(FieldHolder nextField)
    {
        if(nextField.isTankPresent())
            return false;
        else if(!nextField.isPresent())
            return false;
        else if(nextField.getEntity() instanceof Water)
            return true;
        else if(nextField.getEntity() instanceof Coast)
            return true;

        return false;
    }

    /**
     * Override drill to fail with a ship
     *
     * @param action Reference to action
     * @param tank Tank to reference
     * @return false
     */
    public boolean drill(Action action, Tank tank)
    {
        return false;
    }

    /**
     * Return the bullet damage for a shit
     *
     * @return int
     */
    public int getBulletDamage(){
        return bulletDamage;
    }

    /**
     * Used for state pattern purposes.
     *
     * @param allowedFireInterval new fire interval
     */
    public void setAllowedFireInterval( int allowedFireInterval ){
        this.allowedFireInterval = allowedFireInterval;
    }
}
