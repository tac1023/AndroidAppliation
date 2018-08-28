package edu.unh.cs.cs619.bulletzone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

import edu.unh.cs.cs619.bulletzone.SoldierStrategies.SoldierGetIntValueDead;
import edu.unh.cs.cs619.bulletzone.SoldierStrategies.SoldierGetIntValueNormal;
import edu.unh.cs.cs619.bulletzone.SoldierStrategies.SoldierGetIntValueStrategy;

/**
 * Sever side representation of a soldier
 *
 * @author Tyler Currier
 * @version 2.0
 * @since 4/17/2018
 */
public class Soldier extends FieldEntity implements Controllable{
        private final long id;

        private final String ip;

        private long lastMoveTime;
        private int allowedMoveInterval;

        private long lastFireTime;
        private int allowedFireInterval;

        private int numberOfBullets;
        private int allowedNumberOfBullets;

        private int life;

        private Direction direction;

        private Map<Integer, Bullet> activeBullets;

        private SoldierGetIntValueStrategy intValueStrategy;

        private boolean isDrilling = false;
        private boolean isTransforming = false;

    /**
     * Constructor for Soldier.
     *
     * @param id The ID of the tank this soldier owns.
     * @param direction The direction this soldier is facing.
     * @param ip ???
     */
    public Soldier(long id, Direction direction, String ip, int life)
    {
        this.id = id;
        this.direction = direction;
        this.ip = ip;
        numberOfBullets = 0;
        allowedNumberOfBullets = 6;
        lastFireTime = 0;
        allowedFireInterval = 200;
        lastMoveTime = 0;
        allowedMoveInterval = 1000;
        //allowedCoastalMoveInterval = 1500;
        this.life = life;
        activeBullets = new HashMap<>();
        intValueStrategy = new SoldierGetIntValueNormal();
    }


    /**
     * Copies a soldier and creates a new equal soldier
     *
     * @return a new soldier
     */
    @Override
    public FieldEntity copy()
    {
        return new Soldier(id, direction, ip, life);
    }

    /**
     * Used when the soldier is shot.
     *
     * @param damage damage to do
     */
    @Override
    public void hit(int damage)
    {
        life = life - damage;
        System.out.println("Soldier Life: " + id + " : " + life);
//      Log.d(TAG, "SoldierId" + id + " hit -> life: " + life);
    }

    /**
     * Gets the last move time.
     *
     * @return lastMoveTime
     */
    public long getLastMoveTime()
    {
        return lastMoveTime;
    }

    /**
     * Sets the last move time.
     *
     * @param lastMoveTime new last move time
     */
    public void setLastMoveTime(long lastMoveTime)
    {
        this.lastMoveTime = lastMoveTime;
    }

    /**
     * Gets the allowed move interval.
     *
     * @return move interval
     */
    public long getAllowedMoveInterval() {
        return allowedMoveInterval;
    }

    /**
     * Gets the last Fire Time.
     *
     * @return last fire time
     */
    public long getLastFireTime() {
        return lastFireTime;
    }

    /**
     * Sets the last fire time of the of the bullet.
     *
     * @param lastFireTime new last fire time
     */
    public void setLastFireTime(long lastFireTime) {
        this.lastFireTime = lastFireTime;
    }

    /**
     * Gets the allowed fire interval.
     *
     * @return the fire interval
     */
    public long getAllowedFireInterval() {
        return allowedFireInterval;
    }

    /**
     * Gets no. of bullets on screen.
     *
     * @return no. of bullets
     */
    public int getNumberOfBullets() {
        return numberOfBullets;
    }

    /**
     * Sets the number of bullets.
     *
     * @param numberOfBullets new number of bullets
     */
    public void setNumberOfBullets(int numberOfBullets) {
        this.numberOfBullets = numberOfBullets;
    }

    /**
     * Gets 2.
     *
     * @return 2
     */
    public int getAllowedNumberOfBullets() {
        return allowedNumberOfBullets;
    }


    /**
     * Gets current direction.
     *
     * @return Direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets a new direction.
     *
     * @param direction new direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Gets soldier ID
     *
     * @return id
     */
    @JsonIgnore
    public long getId() {
        return id;
    }

    /**
     * Gets the int value
     *
     * @return int
     */
    @Override
    public int getIntValue() {
        return intValueStrategy.getIntValue(this, id, life, direction);
    }

    /**
     * Switch the intValueStrategy to dead
     */
    public void die()
    {
        if(intValueStrategy.isNormal())
            intValueStrategy = new SoldierGetIntValueDead();
    }

    /**
     * Not used in current implementation.
     *
     * @return "S"
     */
    @Override
    public String toString()
    {
        return "S";
    }

    /**
     * Gets life of soldier.
     *
     * @return life
     */
    public int getLife() {
        return life;
    }

    /**
     * Sets the life of soldier.
     *
     * @param life This soldier's life
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * gets the IP
     *
     * @return IP
     */
    public String getIp(){
        return ip;
    }

    /**
     * When a soldier fires a bullet, track it.
     *
     * @param id ID of bullet
     * @param bullet bullet
     */
    public void storeBulletReference(Integer id, Bullet bullet)
    {
        activeBullets.put(id, bullet);
    }

    /**
     * This bullet hit something, so stop tracking it.
     *
     * @param id ID of bullet to remove.
     */
    public void deleteBulletReference(Integer id)
    {
        activeBullets.remove(id);
    }

    /**
     * Clear all active bullets for this soldier.
     */
    public void clearBullets()
    {
        if(!activeBullets.isEmpty()) {
            for (Integer entry : activeBullets.keySet()) {
                //((Bullet) entry).getParent().clearTankItem();
                activeBullets.get(entry).setCleared();
            }
            activeBullets.clear();
        }
    }

    /**
     * Return whether this soldier is allowed to enter the given holder
     *
     * @param nextField holder to enter
     * @return boolean
     */
    public boolean canEnter(FieldHolder nextField)
    {
        if(nextField.isTankPresent())
        {
            return (nextField.getTankItem() instanceof Tank && ((Tank) nextField.getTankItem()).getId() == getId());
        }
        if(!nextField.isPresent())
            return true;
        if(nextField.getEntity() instanceof Wall)
            return false;
        if(nextField.getEntity() instanceof RockAndDirt)
        {
            if(!((RockAndDirt) nextField.getEntity()).isOpen())
                return false;
        }

        return true;
    }

    /**
     * Return whether the tank is actively in a drill
     *
     * @return boolean
     */
    public boolean isDrilling()
    {
        return isDrilling;
    }

    /**
     * Set whether the tank is actively in a drill
     *
     * @param drilling Is or Isn't drilling
     */
    public void setDrilling(boolean drilling)
    {
        isDrilling = drilling;
    }

    /**
     * Return whether the soldier is actively transforming
     *
     * @return boolean
     */
    public boolean isTransforming()
    {
        return isTransforming;
    }

    /**
     * Set whether the soldier is actively transforming
     *
     * @param transforming Is or Isn't transforming
     */
    public void setTransforming(boolean transforming)
    {
        isTransforming = transforming;
    }
}
