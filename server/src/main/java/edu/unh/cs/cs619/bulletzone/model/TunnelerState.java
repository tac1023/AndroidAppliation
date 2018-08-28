package edu.unh.cs.cs619.bulletzone.model;

import edu.unh.cs.cs619.bulletzone.TankStrategies.TankGetIntValueExplode;
import edu.unh.cs.cs619.bulletzone.TankStrategies.TankGetIntValueStrategy;
import edu.unh.cs.cs619.bulletzone.TankStrategies.TunnelerGetIntValueNormal;
import edu.unh.cs.cs619.bulletzone.TankStrategies.TunnelerGetIntValueOnFire;
import edu.unh.cs.cs619.bulletzone.tank_action.Action;

import static edu.unh.cs.cs619.bulletzone.model.Direction.Above;
import static edu.unh.cs.cs619.bulletzone.model.Direction.Below;

/**
 * Created by Jason on 4/28/2018.
 *
 * @author Jason Vettese, Tyler Currier
 * @version 1.1
 * @since 5/2/2018
 */

public class TunnelerState implements TankStateInterface {

    private int allowedMoveInterval;
    private int allowedTurnInterval;
    private int allowedFireInterval;
    private int allowedNumberOfBullets;
    private int allowedEjectInterval;
    private int allowedDigInterval;
    private int bulletDamage;

    private TankGetIntValueStrategy intValueStrategy;


    /**
     * Constructor initializes all variables that were declared.
     */
    TunnelerState() {
        allowedNumberOfBullets = 2;
        allowedFireInterval = 500;
        allowedMoveInterval = 1000;
        allowedTurnInterval = 500;
        allowedEjectInterval = 3000;
        allowedDigInterval = 2000;
        bulletDamage = 30;
        intValueStrategy = new TunnelerGetIntValueNormal();
    }

    /**
     * Return allowedTurnInterval for Tunnelers
     *
     * @return long
     */
    public long getAllowedTurnInterval() {
        return allowedTurnInterval;
    }

    /**
     * Return allowedMoveInterval for Tunnelers
     *
     * @return long
     */
    public long getAllowedMoveInterval() {
        return allowedMoveInterval;
    }

    /**
     * Return allowedFireInterval for Tunnelers
     *
     * @return long
     */
    public long getAllowedFireInterval() {
        return allowedFireInterval;
    }

    /**
     * Return allowedNumberOfBullets
     *
     * @return int
     */
    public int getAllowedNumberOfBullets() {
        return allowedNumberOfBullets;
    }

    /**
     * Return Integer Value for this Tunneler
     *
     * @param tank Tank of player
     * @param id ID of player
     * @param life life of player
     * @param direction current direction
     * @return int
     */
    public int getIntValue(Tank tank, long id, int life, Direction direction)
    {
        return intValueStrategy.getIntValue(tank, id, life, direction);
    }

    /**
     * Switch getIntValue to onFire strategy (Not yet implemented
     */
    public void catchFire()
    {
        if(intValueStrategy.isNormal())
            intValueStrategy = new TunnelerGetIntValueOnFire();
    }

    /**
     * Switch getIntValue to explode strategy (Not yet implemented)
     */
    public void explode()
    {
        if(intValueStrategy.isOnFire())
            intValueStrategy = new TankGetIntValueExplode();
    }

    /**
     * Return allowedEjectInterval for Tunnelers
     *
     * @return long
     */
    public long getAllowedEjectInterval() {
        return allowedEjectInterval;
    }

    /**
     * Return if this is a tank state. This is false, but helps avoid use of
     * instanceof
     *
     * @return boolean
     */
    public boolean isTankState()
    {
        return false;
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
     * Return whether the tunneler can enter the given holder
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
            if(((RockAndDirt) nextField.getEntity()).isDestructible()) {
                return true;
            }
        }
        if(nextField.getEntity() instanceof Wall)
        {
            if(((Wall)nextField.getEntity()).canEnter())
                return true;
        }
        return false;
    }

    /**
     * Try to drill underground or travel through a tunnel
     *
     * @param action Reference to action
     * @param tank Tank to reference
     * @return boolean
     */
    public boolean drill(Action action, Tank tank)
    {
        try {
            FieldHolder originalParent = tank.getParent();

            boolean ret;
            boolean isTunnel = false;
            FieldHolder nextField;
            if (originalParent.getNeighbor(Direction.Above) == null) {
                nextField = originalParent.getNeighbor(Below);
                if(originalParent.isPresent() && (originalParent.getEntity() instanceof Tunnel))
                    isTunnel = true;
            }
            else {
                nextField = originalParent.getNeighbor(Above);
                if(nextField.isPresent() && (nextField.getEntity() instanceof Tunnel))
                    isTunnel = true;
            }

            ret = canEnter(nextField);

            if(ret) {
                if(!isTunnel) {
                    try {
                        Thread.sleep(allowedDigInterval);
                    } catch (InterruptedException e) {
                        System.err.println("Interrupted Dig Wait");
                    }
                }
                nextField.setTankEntity(tank);
                tank.setParent(nextField);
                originalParent.clearTankItem();

                if ((originalParent.getNeighbor(Direction.Above) == null)
                        && !(originalParent.isPresent() && originalParent.getEntity() instanceof Tunnel)) {
                    originalParent.setFieldEntity(new Tunnel());
                    ((RockAndDirt)tank.getParent().getEntity()).clearDirt();
                } else if ((tank.getParent().getNeighbor(Direction.Above) == null)
                        && !(tank.getParent().isPresent() && tank.getParent().getEntity() instanceof Tunnel)) {
                    tank.getParent().setFieldEntity(new Tunnel());
                }
            }


            return ret;

        } catch (NullPointerException e ) {
            return false;
        }
    }

    /**
     * Return the Bullet damage for a Tunneler
     *
     * @return int
     */
    public int getBulletDamage()
    {
        return bulletDamage;
    }

    /**
     * Used for state pattern purposes.
     * @param allowedFireInterval new allowed fire interval
     */
    public void setAllowedFireInterval( int allowedFireInterval ){
        //do nothing
    }
}
