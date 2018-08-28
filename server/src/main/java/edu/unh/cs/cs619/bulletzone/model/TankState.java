package edu.unh.cs.cs619.bulletzone.model;

import edu.unh.cs.cs619.bulletzone.TankStrategies.TankGetIntValueExplode;
import edu.unh.cs.cs619.bulletzone.TankStrategies.TankGetIntValueNormal;
import edu.unh.cs.cs619.bulletzone.TankStrategies.TankGetIntValueOnFire;
import edu.unh.cs.cs619.bulletzone.TankStrategies.TankGetIntValueStrategy;
import edu.unh.cs.cs619.bulletzone.tank_action.Action;

/**
 * State for what a tank should do when it is a tank
 *
 * @author Jason Vetesse, Tyler Currier
 * @version 1.0
 * @since 4/28/2018
 */

public class TankState implements TankStateInterface {

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
    TankState() {
        allowedNumberOfBullets = 2;
        allowedFireInterval = 500;
        allowedMoveInterval = 500;
        allowedTurnInterval = 500;
        allowedEjectInterval = 3000;
        bulletDamage = 30;
        intValueStrategy = new TankGetIntValueNormal();
    }

    /**
     * Return the allowedTurnInterval for tanks
     *
     * @return long
     */
    public long getAllowedTurnInterval() {
        return allowedTurnInterval;
    }

    /**
     * Return the allowedMoveInterval for tanks
     *
     * @return long
     */
    public long getAllowedMoveInterval() {
        return allowedMoveInterval;
    }

    /**
     * Return the allowedFireInterval for tanks
     *
     * @return long
     */
    public long getAllowedFireInterval() {
        return allowedFireInterval;
    }

    /**
     * Return the allowed number of bullets for tanks
     *
     * @return int
     */
    public int getAllowedNumberOfBullets() {
        return allowedNumberOfBullets;
    }

    /**
     * Get the Integer Value for this tank
     *
     * @param tank Tank whose value to get
     * @param id ID to use
     * @param life life of tank
     * @param direction current direction
     * @return int
     */
    public int getIntValue(Tank tank, long id, int life, Direction direction)
    {
        return intValueStrategy.getIntValue(tank, id, life, direction);
    }

    /**
     * Switch getIntegerValue to the on fire strategy
     */
    public void catchFire()
    {
        if(intValueStrategy.isNormal())
            intValueStrategy = new TankGetIntValueOnFire();
    }

    /**
     * Switch getIntegerValue to the explode strategy
     */
    public void explode()
    {
        if(intValueStrategy.isOnFire())
            intValueStrategy = new TankGetIntValueExplode();
    }

    /**
     * Return the allowedEjectInterval for tanks
     *
     * @return long
     */
    public long getAllowedEjectInterval() {
        return allowedEjectInterval;
    }

    /**
     * Return whether this is the tank state. Always true, helps avoid use of
     * instanceof
     *
     * @return boolean
     */
    public boolean isTankState()
    {
        return true;
    }

    /**
     * Return that this isn't a ship state
     *
     * @return false
     */
    public boolean isShipState()
    {
        return false;
    }

    /**
     * Return whether a tank can enter the given field holder
     *
     * @param nextField holder to enter
     * @return boolean
     */
    public boolean canEnter(FieldHolder nextField)
    {
        if(nextField.isTankPresent())
            return false;
        if(!nextField.isPresent())
            return true;
        if(nextField.getEntity() instanceof Coast)
            return true;
        if(nextField.getEntity() instanceof Hill)
            return true;
        if(nextField.getEntity() instanceof Tunnel)
            return true;
        if(nextField.getEntity() instanceof DebrisField)
        {
            if(!((DebrisField) nextField.getEntity()).isForest())
                return true;
        }
        if(nextField.getEntity() instanceof RockAndDirt)
        {
            if(((RockAndDirt) nextField.getEntity()).isOpen())
                return true;
        }
        if(nextField.getEntity() instanceof Wall)
        {
            if(((Wall)nextField.getEntity()).canEnter())
                return true;
        }
        return false;
    }

    /**
     * Override drill to allow tanks to travel through tunnels.
     *
     * @param action Reference to action
     * @param tank Tank to reference
     * @return boolean
     */
    public boolean drill(Action action, Tank tank)
    {
        FieldHolder originalParent = tank.getParent();
        if(originalParent.getNeighbor(Direction.Below) == null)
        {
            if(originalParent.getNeighbor(Direction.Above).isPresent())
            {
                if(!(originalParent.getNeighbor(Direction.Above).getEntity() instanceof Tunnel))
                    return false;
            }
            else
                return false;
        }
        else if(!(originalParent.isPresent()))
            return false;
        else if(!(originalParent.getEntity() instanceof Tunnel))
            return false;

        if(originalParent.getNeighbor(Direction.Above) == null) {
            try {
                return action.move(tank.getId(), Direction.Below);
            }
            catch (TankDoesNotExistException | IllegalTransitionException | LimitExceededException
                    | SoldierDoesNotExistException e)
            {
                return false;
            }
        }
        else
        {
            try
            {
                return action.move(tank.getId(), Direction.Above);
            }
            catch (TankDoesNotExistException | IllegalTransitionException | LimitExceededException
                    | SoldierDoesNotExistException e)
            {
                return false;
            }
        }
    }

    /**
     * Return the Bullet Damage for a Tank
     *
     * @return int
     */
    public int getBulletDamage()
    {
        return bulletDamage;
    }

    /**
     * Used for state pattern purposes.
     *
     * @param allowedFireInterval new allowed fire interval
     */
    public void setAllowedFireInterval( int allowedFireInterval ){
        //do nothing
    }

}
