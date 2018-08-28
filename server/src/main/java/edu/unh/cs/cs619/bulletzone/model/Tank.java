package edu.unh.cs.cs619.bulletzone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

import edu.unh.cs.cs619.bulletzone.Constraint.ConstraintHandler;
import edu.unh.cs.cs619.bulletzone.tank_action.Action;
import edu.unh.cs.cs619.bulletzone.tank_action.DoesAction;
import edu.unh.cs.cs619.bulletzone.tank_action.MissileAction;
import edu.unh.cs.cs619.bulletzone.tank_action.TankAction;

/**
 * The Tank object.
 * @author ???, Tyler Currier
 * @version 1.2
 * @since 4/17/18
 */
public class Tank extends FieldEntity implements Controllable{

    private final long id;

    private final String ip;

    private long lastMoveTime;

    private long lastTurnTime;

    private long lastFireTime;

    private int numberOfBullets;
    private int preTransformNumberOfBullets;

    private int life;
    private boolean isAlive = true;
    private boolean soldierOut = false;

    private Direction direction;

    private Soldier soldier;
    private int soldierLife;
    private long lastEjectTime;

    private Missile missile = null;

    private int shipTransformInterval;
    private int tunnelerTransformInterval;

    private int allowedDigInterval;
    private TankStateInterface state;

    private DoesAction currentAction;
    private DoesAction beforeMissileAction;
    private ConstraintHandler constraintHandler;

    private boolean isDrilling = false;
    private boolean isTransforming = false;

    private Map<Integer, Bullet> activeBullets;


    /**
     * Constructor.
     *
     * @param id ID of player
     * @param direction Direction to face
     * @param ip IP of player
     */
    public Tank(long id, Direction direction, String ip) {
        state = new TankState();

        this.id = id;
        this.direction = direction;
        this.ip = ip;


        numberOfBullets = 0;
        preTransformNumberOfBullets = 0;
        lastFireTime = 0;
        lastMoveTime = 0;
        lastTurnTime = 0;
        soldier = null;
        soldierLife = 25;
        lastEjectTime = System.currentTimeMillis() + 3000;
        constraintHandler = new ConstraintHandler();
        soldier = new Soldier(id, direction, ip, soldierLife);

        shipTransformInterval = 1000;
        tunnelerTransformInterval = 250;

        allowedDigInterval = 2000;

        currentAction = new TankAction();
        activeBullets = new HashMap<>();
    }

    /**
     * Return the Constraint Handler for this tank
     *
     * @return ConstraintHandler
     */
    public ConstraintHandler getConstraintHandler()
    {
        return constraintHandler;
    }

    /**
     * Creates new tank.
     *
     * @return new tank
     */
    @Override
    public FieldEntity copy() {
        return new Tank(id, direction, ip);
    }

    /**
     * Depletes life count
     *
     * @param damage damage to do
     */
    @Override
    public void hit(int damage) {
        life = life - damage;
        System.out.println("Tank life: " + id + " : " + life);
//		Log.d(TAG, "TankId: " + id + " hit -> life: " + life);
    }

    /**
     * gets last turn time.
     *
     * @return time
     */
    public long getLastTurnTime()
    {
        return lastTurnTime;
    }

    /**
     * Sets the last turn time.
     *
     * @param lastTurnTime new last turn time
     */
    public void setLastTurnTime(long lastTurnTime)
    {
        this.lastTurnTime = lastTurnTime;
    }

    /**
     * Gets the allowed turn of Tank.
     *
     * @return turn interval
     */
    public long getAllowedTurnInterval() {
        return state.getAllowedTurnInterval();
    }

    /**
     * Gets the last move time.
     *
     * @return lastMoveTime
     */
    public long getLastMoveTime() {
        return lastMoveTime;
    }

    /**
     * Sets the last move time.
     *
     * @param lastMoveTime new last move time
     */
    public void setLastMoveTime(long lastMoveTime) {
        this.lastMoveTime = lastMoveTime;
    }

    /**
     * Gets the allowed move interval.
     *
     * @return move interval
     */
    public long getAllowedMoveInterval() {
        return state.getAllowedMoveInterval();
        //return allowedMoveInterval;
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
        return state.getAllowedFireInterval();
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
        return state.getAllowedNumberOfBullets();
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
     * Gets tank ID
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
        return state.getIntValue(this, id, life, direction);
    }

    /**
     * T.
     *
     * @return "T"
     */
    @Override
    public String toString() {
        return "T";
    }

    /**
     * Gets life of Tank.
     *
     * @return life
     */
    public int getLife() {
        return life;
    }

    /**
     * Return whether or not the player is alive. Could be dead either to tank being
     * dead (with soldier inside) or with soldier dead (outside).
     *
     * @return whether or not player is alive
     */
    public boolean isAlive()
    {
        return isAlive;
    }

    /**
     * Set whether or not the player is alive. Could be dead either due to the tank being
     * dead (with soldier inside) or with soldier dead(outside).
     */
    public void setIsAlive()
    {
        isAlive = false;
    }

    /**
     * This variable tracks whether this tank's soldier is inside or outside
     * the tank
     *
     * @param out true if soldier is outside tank, false if inside.
     */
    public void setSoldierOut(boolean out)
    {
        soldierOut = out;
    }

    /**
     * Return whether the tank's soldier is inside the tank or outside
     *
     * @return true for outside, false for inside
     */
    public boolean isSoldierOut()
    {
        return soldierOut;
    }

    /**
     * Sets the life of Tank.
     *
     * @param life new life
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
     * Switch the getIntValue strategy to the onFire one.
     */
    public void catchFire()
    {
        state.catchFire();
    }

    /**
     * Switch the getIntValue strategy to the explode one.
     */
    public void explode()
    {
        state.explode();
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////
    //                              Soldier related code
    ///////////////////////////////////////////////////////////////////////////////////////////////



    /**
     * Returns the soldier or null.
     *
     * @return soldier
     */
    public Soldier getSoldier() {
        return soldier;
    }

    /**
     * Creates a soldier assuming that one can be created.
     *
     * @return a new soldier
     */
    public Soldier eject() {
        if(soldierOut)
            return null;
        if(soldier == null)
            soldier = new Soldier(id, direction, ip, soldierLife);
        return soldier;
    }

    /**
     * Sets the passes in parameter to lastEjectTime.
     *
     * @param millis new last eject time
     */
    public void setLastEjectTime( long millis ) {
        lastEjectTime = millis;
    }

    /**
     * Return the time that the tank is allowed to eject again
     *
     * @return next allowed eject time
     */
    public long getLastEjectTime() {
        return lastEjectTime;
    }

    /**
     * return the interval on which tanks are allowed to eject soldiers
     *
     * @return interval
     */
    public long getAllowedEjectInterval() {
        return state.getAllowedEjectInterval();
        //return allowedEjectInterval;
    }

    //State dependent

    /**
     * Sets the state of the object to a Ship State.
     */
    public void setShipState() {
        if( !state.isTankState() ) {
            state = new TankState();
            restoreNumberOfBullets();
        }
        else {
            state = new ShipState();
            storeNumberOfBullets();
        }
    }

    /**
     * Sets the state of the object to a tunneler State.
     */
    public void setTunnelerState() {
        if( !state.isTankState() )
            state = new TankState();
        else
            state = new TunnelerState();
    }

    /**
     * Attempts to use a tunneler to drill.
     *
     * @return true if tunneler
     */
    public boolean drill() {
        return state instanceof TunnelerState;
    }

    /**
     * Get the time it takes to transform into a tunneler
     *
     * @return int
     */
    public int getTunnelerTransformInterval()
    {
        return tunnelerTransformInterval;
    }

    /**
     * Get the time it takes to transform into a ship
     *
     * @return int
     */
    public int getShipTransformInterval()
    {
        return shipTransformInterval;
    }

    /**
     * Return the allowed dig interval
     *
     * @return int
     */
    public int getAllowedDigInterval()
    {
        return allowedDigInterval;
    }

    //Action for Vehicle vs Soldier

    /**
     * Return the current action being used by the tank
     *
     * @return DoesAction
     */
    public DoesAction getCurrentAction()
    {
        return currentAction;
    }

    /**
     * Set the current action being used by the tank
     *
     * @param newAction new action
     */
    public void setCurrentAction(DoesAction newAction)
    {
        currentAction = newAction;
    }

    /**
     * Return whether the tank can enter the given field holder
     *
     * @param nextField holder to enter
     * @return boolean
     */
    public boolean canEnter(FieldHolder nextField)
    {
        return state.canEnter(nextField);
    }

    /**
     * Return the success value of the current state's drill method
     *
     * @param action Reference to action
     * @return boolean
     */
    public boolean drill(Action action)
    {
        return state.drill(action, this);
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
     * Return whether the tank is actively transforming
     *
     * @return boolean
     */
    public boolean isTransforming()
    {
        return isTransforming;
    }

    /**
     * Set whether the tank is actively transforming
     *
     * @param transforming Is or Isn't transforming
     */
    public void setTransforming(boolean transforming)
    {
        isTransforming = transforming;
    }

    /**
     * Return the Bullet Damage based on the State of the tank
     *
     * @return int
     */
    public int getBulletDamage()
    {
        return state.getBulletDamage();
    }

    /**
     * Create a new Missile and return it. If there is already a Missile there
     * can't be another, so just return null.
     *
     * @return new Missile
     */
    public Missile fireMissile()
    {
        if(missile != null)
            return null;

        missile = new Missile(id, direction);
        beforeMissileAction = currentAction;
        currentAction = new MissileAction();
        return missile;
    }

    /**
     * The Missile has exploded, so clear the reference to it.
     */
    public void explodeMissile()
    {
        missile = null;
        currentAction = beforeMissileAction;
    }

    /**
     * Return the reference to missile
     *
     * @return Missile
     */
    public Missile getMissile()
    {
        return missile;
    }

    /**
     * Get the current state
     *
     * @return TankStateInterface
     */
    public TankStateInterface getState()
    {
        return state;
    }

    //--Ship Only-------------------------------------------------

    /**
     * When a ship fires a bullet, track it.
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
     * Clear all active bullets for this ship.
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
     * Store the number of bullets before transforming into a tank
     */
    private void storeNumberOfBullets()
    {
        preTransformNumberOfBullets = numberOfBullets;
    }

    /**
     * Restore the number of bullets to what it was before transforming into a tank
     */
    private void restoreNumberOfBullets()
    {
        numberOfBullets = preTransformNumberOfBullets;
    }
}


