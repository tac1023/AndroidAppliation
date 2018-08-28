package edu.unh.cs.cs619.bulletzone.tank_action;

import java.util.concurrent.TimeUnit;

import edu.unh.cs.cs619.bulletzone.Constraint.ConstraintHandler;
import edu.unh.cs.cs619.bulletzone.fire.FireBullet;
import edu.unh.cs.cs619.bulletzone.model.Coast;
import edu.unh.cs.cs619.bulletzone.model.DebrisField;
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.IllegalTransitionException;
import edu.unh.cs.cs619.bulletzone.model.LimitExceededException;
import edu.unh.cs.cs619.bulletzone.model.Missile;
import edu.unh.cs.cs619.bulletzone.model.RockAndDirt;
import edu.unh.cs.cs619.bulletzone.model.Soldier;
import edu.unh.cs.cs619.bulletzone.model.SoldierDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.Tank;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.Water;
import edu.unh.cs.cs619.bulletzone.repository.Monitor;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Contains the actions that a player can make. Then the actions of other
 * action classes are called depending on whether the player is controlling
 * a soldier or a tank.
 *
 * @author Tyler Currier
 * @version 2.0
 * @since 5/2/2018
 */

public class Action {

    private Game game = null;
    private final Monitor monitor = Monitor.getInstance();
    private DoesAction currentAction = new TankAction();
    private FireBullet fireBullet = new FireBullet();


    /**
     * Constructor
     */
    public Action()
    {
        game = Game.getInstance();
        edu.unh.cs.cs619.bulletzone.observer.Observer.getInstance(this);
    }


    /**
     *  Turns the tank to the specified direction. If there is an invalid input the
     *  method returns the Tank's current location.
     *
     * @param tankId ID of tank to turn
     * @param direction Direction to turn tank
     * @return The new direction of the tank.
     * @throws TankDoesNotExistException There is not such tank
     * @throws IllegalTransitionException Can't turn that way
     * @throws LimitExceededException Can't turn again
     */
    public boolean turn(long tankId, Direction direction) throws TankDoesNotExistException,
            SoldierDoesNotExistException, IllegalTransitionException, LimitExceededException {
        try {
                checkNotNull(direction);

                // Find user
                Tank tank = game.getTanks().get(tankId);

                if (tank == null) {
                    //Log.i(TAG, "Cannot find user with id: " + tankId);
                    throw new TankDoesNotExistException(tankId);
                }

                //Don't turn if player is dead
                if (!tank.isAlive())
                    return false;

                if(tank.isDrilling() || tank.getSoldier().isTransforming())
                    return false;

                if(tank.isTransforming() || tank.getSoldier().isTransforming())
                    return false;

                ConstraintHandler constraintHandler = tank.getConstraintHandler();

                currentAction = tank.getCurrentAction();

                return currentAction.turn(constraintHandler, tank, direction);
        }
        catch(TankDoesNotExistException | SoldierDoesNotExistException | IllegalTransitionException
                | LimitExceededException e)
        {
            return false;
        }
    }


    /**
     * Moves the tank forward or backward, respectively. If there is an invalid input. The tank's
     * location remains constant.
     *
     * @param tankId ID of tank to move
     * @param direction Direction to move tank
     * @return whether move was successful or not.
     * @throws TankDoesNotExistException There is not such tank
     * @throws IllegalTransitionException Can's move that way
     * @throws LimitExceededException Can't move again
     */
    public boolean move(long tankId, Direction direction) throws TankDoesNotExistException,
            IllegalTransitionException, LimitExceededException, SoldierDoesNotExistException
    {
        try {
            Tank tank = game.getTanks().get(tankId);

            if (tank == null) {
                //Log.i(TAG, "Cannot find user with id: " + tankId);
                //return false;
                throw new TankDoesNotExistException(tankId);
            }

            //Don't move if player is dead
            if (!tank.isAlive())
                return false;

            if(tank.isTransforming() || tank.getSoldier().isTransforming())
                return false;

            ConstraintHandler constraintHandler = tank.getConstraintHandler();

            currentAction = tank.getCurrentAction();

            return currentAction.move(this, constraintHandler, tank, direction);

        }
        catch(TankDoesNotExistException | IllegalTransitionException | LimitExceededException
                | SoldierDoesNotExistException e)
        {
            return false;
        }
    }

    /**
     * This move method is for used by the observer when moving a ship inside a
     * WaterCurrent cell. This movement method ignores all directional movement
     * constraints so that a ship may be pushed by the current when sideways.
     *
     * @param tankId ID of Controllable to move
     * @param direction Direction to move
     * @return success or failure of move
     * @throws TankDoesNotExistException There is no such tank
     */
    public boolean currentMove(long tankId, Direction direction) throws TankDoesNotExistException
    {
        try
        {
            Tank tank = game.getTanks().get(tankId);

            if (tank == null) {
                //Log.i(TAG, "Cannot find user with id: " + tankId);
                //return false;
                throw new TankDoesNotExistException(tankId);
            }

            //Don't move if player is dead
            if (!tank.isAlive())
                return false;

            if(tank.getParent() == null)
                return false;
            FieldHolder parent = tank.getParent();

            if(tank.getParent().getNeighbor(direction) == null)
                return false;
            FieldHolder neighbor = tank.getParent().getNeighbor(direction);

            ConstraintHandler constraintHandler = tank.getConstraintHandler();

            return constraintHandler.checkTankParentNeighbor(tank, parent, neighbor, System.currentTimeMillis());
        }
        catch (TankDoesNotExistException e)
        {
            return false;
        }
    }

    /**
     * Fires a bullet in the direction that the tank is facing. If there are more than two of the
     * tank's bullets on the screen then, no new bullets are created and the method is false;
     *
     * @param tankId ID of tank to fire from
     * @param bulletType Type of bullet to fire
     * @param direction Direction in which to fire
     * @return whether a bullet was successfully created
     * @throws TankDoesNotExistException There is not such tank
     * @throws LimitExceededException Can't fire again
     */
    public boolean fire(long tankId, int bulletType, int direction )
            throws TankDoesNotExistException, LimitExceededException, SoldierDoesNotExistException
    {
        try {
            // Find tank
            Tank tank = game.getTanks().get(tankId);

            if (tank == null) {
                //Log.i(TAG, "Cannot find user with id: " + tankId);
                //return false;
                throw new TankDoesNotExistException(tankId);
            }

            //Don't fire if player is dead
            if (!tank.isAlive())
                return false;

            if(tank.isDrilling() || tank.getSoldier().isDrilling())
                return false;

            if(tank.isTransforming() || tank.getSoldier().isTransforming())
                return false;

            ConstraintHandler constraintHandler = tank.getConstraintHandler();

            currentAction = tank.getCurrentAction();

            return currentAction.fire(constraintHandler, tank, bulletType, direction);

        }
        catch(TankDoesNotExistException | LimitExceededException | SoldierDoesNotExistException e)
        {
            return false;
        }
    }

    /**
     * Fire a guided Missile, switch control to the missile, destroy missile if
     * there already is one.
     *
     * @param tankId ID of tank from which to fire
     * @return boolean
     */
    public boolean fireMissile(long tankId)
            throws TankDoesNotExistException, LimitExceededException, SoldierDoesNotExistException
    {
        Tank tank = game.getTanks().get(tankId);
        if(tank == null)
            return false;

        if(tank.getCurrentAction().isSoldierAction())
            return false;

        Missile missile = tank.fireMissile();
        if(missile == null) {
            missile = tank.getMissile();
            missile.explode();
            tank.explodeMissile();
            return true;
        }

        missile.setParent(tank.getParent());

        fireBullet.fire(game, tank, missile);
        return true;
    }

    /**
     * Ejects a soldier from the tank that the player then controls instead of
     * the tank.
     *
     * @param tankId id of tank from which to eject soldier
     * @return success
     * @throws TankDoesNotExistException There is not such tank
     * @throws LimitExceededException Can't eject again
     */
    public boolean eject( long tankId ) throws TankDoesNotExistException, LimitExceededException {
        //setPlayerState(1);
        try {
                Tank tank = game.getTanks().get(tankId);

                if (tank == null) throw new TankDoesNotExistException(tankId);
                ConstraintHandler constraintHandler = tank.getConstraintHandler();

                //Don't eject if player is dead
                if (!tank.isAlive())
                    return false;

                if(tank.isDrilling() || tank.getSoldier().isDrilling())
                    return false;

                if(tank.isTransforming() || tank.getSoldier().isTransforming())
                return false;

                if(tank.getCurrentAction().isMissileAction())
                    return false;

                long millis = System.currentTimeMillis();
                if (!constraintHandler.checkLastEjectTime(tank, millis)) return false;

                FieldHolder parent = tank.getParent();

                tank.setLastEjectTime(millis + tank.getAllowedEjectInterval());

                final Soldier s = tank.eject();
                if (s == null)
                    return false;
                s.setParent(parent);

                boolean ret;
                synchronized (this.monitor) {
                    ret = placeNewSoldier(s);
                }
                if (ret) {
                    //constraintHandler.setPlayerState(1); //give control to soldier
                    tank.setCurrentAction(new SoldierAction());
                    tank.setSoldierOut(true);
                }
                return ret;
        }
        catch(TankDoesNotExistException e)
        {
            return false;
        }

    }

    /**
     * Turns the tank into a tunneler object.
     *
     * @param tankId id of tank from which to eject soldier
     * @return success
     * @throws TankDoesNotExistException There is not such tank
     * @throws LimitExceededException Can't transform again
     */
    public boolean toTunneler( long tankId ) throws TankDoesNotExistException, LimitExceededException {
        try {
            Tank tank = game.getTanks().get(tankId);
            if (tank == null) throw new TankDoesNotExistException(tankId);
            if (tank.getCurrentAction().canTransform()) {

                ConstraintHandler constraintHandler = tank.getConstraintHandler();

                //Don't transform if player is dead
                if (!tank.isAlive())
                    return false;

                if(tank.isDrilling() || tank.getSoldier().isDrilling())
                    return false;

                if (!constraintHandler.checkTankToTunnelParent(tank)) return false;

                tank.setTransforming(true);

                try {
                    TimeUnit.MILLISECONDS.sleep(tank.getTunnelerTransformInterval());
                }
                catch(InterruptedException e)
                {
                    System.err.println("Sleep on tunneler transformation wait interrupted");
                }

                //DO TRANSFORMATION
                tank.setTunnelerState();
                tank.setTransforming(false);
                return true;

            }
            return false;
        }
        catch (TankDoesNotExistException e) {
            return false;
        }
    }

    /**
     * Turns the tank into a ship object.
     *
     * @param tankId id of tank from which to eject soldier
     * @return success
     * @throws TankDoesNotExistException There is not such tank
     * @throws LimitExceededException Can't transform again
     */
    public boolean toShip( long tankId ) throws TankDoesNotExistException, LimitExceededException {
        try {
            Tank tank = game.getTanks().get(tankId);
            if (tank == null) throw new TankDoesNotExistException(tankId);
            if (tank.getCurrentAction().canTransform()) {

                ConstraintHandler constraintHandler = tank.getConstraintHandler();

                //Don't transform if player is dead
                if (!tank.isAlive())
                    return false;

                if(tank.isDrilling() || tank.getSoldier().isDrilling())
                    return false;

                if (!constraintHandler.checkTankToShipParent(tank)) return false;

                tank.setTransforming(true);

                try {
                    TimeUnit.MILLISECONDS.sleep(tank.getShipTransformInterval());
                }
                catch(InterruptedException e)
                {
                    System.err.println("Sleep on ship transformation wait interrupted");
                }

                tank.setShipState();
                tank.setTransforming(false);
                return true;
            }

            return false;
        }
        catch (TankDoesNotExistException e) {
            return false;
        }
    }

    /**
     * Drill down or up if allowed
     *
     * @param tankId ID of tank to drill
     * @return boolean
     * @throws TankDoesNotExistException There is not such tank
     * @throws IllegalTransitionException Can't drill that way
     * @throws LimitExceededException Can't drill again
     */
    public boolean drill( long tankId ) throws TankDoesNotExistException, IllegalTransitionException,
            LimitExceededException {
        try {
            Tank tank = game.getTanks().get(tankId);
            if( tank == null ) throw new TankDoesNotExistException(tankId);
            if( !tank.isAlive() ) return false;

            if(tank.isDrilling() || tank.getSoldier().isDrilling())
                return false;

            currentAction = tank.getCurrentAction();

            boolean ret = currentAction.drill(this, tank);
            tank.setDrilling(false);
            tank.getSoldier().setDrilling(false);
            return ret;

        } catch (TankDoesNotExistException e ) {
            return false;
        }
    }



    /**
     * Takes a newly created soldier and tries to place it in one of the
     * square surround the tank. It will only succeed if the square is
     * completely empty. If all squares are full, this operation fails
     * and returns false. It returns true upon success.
     *
     * @param soldier New soldier to place on the map.
     * @return success of operation (true or false)
     */
    private boolean placeNewSoldier(Soldier soldier)
    {
        FieldHolder parent = soldier.getParent();

        //Try top row
        FieldHolder trySpot = parent.getNeighbor(Direction.Up);
        if(canPlaceSoldier(trySpot))
        {
            trySpot.setTankEntity(soldier);
            soldier.setParent(trySpot);
            return true;
        }
        FieldHolder trySpot2 = trySpot.getNeighbor(Direction.Left);
        if(canPlaceSoldier(trySpot2))
        {
            trySpot2.setTankEntity(soldier);
            soldier.setParent(trySpot2);
            return true;
        }
        trySpot2 = trySpot.getNeighbor(Direction.Right);
        if(canPlaceSoldier(trySpot2))
        {
            trySpot2.setTankEntity(soldier);
            soldier.setParent(trySpot2);
            return true;
        }

        //Try bottom row
        trySpot = parent.getNeighbor(Direction.Down);
        if(canPlaceSoldier(trySpot))
        {
            trySpot.setTankEntity(soldier);
            soldier.setParent(trySpot);
            return true;
        }
        trySpot2 = trySpot.getNeighbor(Direction.Left);
        if(canPlaceSoldier(trySpot2))
        {
            trySpot2.setTankEntity(soldier);
            soldier.setParent(trySpot2);
            return true;
        }
        trySpot2 = trySpot.getNeighbor(Direction.Right);
        if(canPlaceSoldier(trySpot2))
        {
            trySpot2.setTankEntity(soldier);
            soldier.setParent(trySpot2);
            return true;
        }

        //Try sides
        trySpot = parent.getNeighbor(Direction.Left);
        if(canPlaceSoldier(trySpot))
        {
            trySpot.setTankEntity(soldier);
            soldier.setParent(trySpot);
            return true;
        }
        trySpot = parent.getNeighbor(Direction.Right);
        if(canPlaceSoldier(trySpot))
        {
            trySpot.setTankEntity(soldier);
            soldier.setParent(trySpot);
            return true;
        }

        //All spots failed
        return false;
    }

    /**
     * Return whether a soldier can be placed in the given field holder
     *
     * @param trySpot field holder to try
     * @return boolean
     */
    private boolean canPlaceSoldier(FieldHolder trySpot)
    {
        if(trySpot.isTankPresent())
            return false;
        if(!trySpot.isPresent())
            return true;
        if(trySpot.getEntity() instanceof Water)
            return true;
        if(trySpot.getEntity() instanceof Coast)
            return true;
        if(trySpot.getEntity() instanceof DebrisField)
            return false;
        if(trySpot.getEntity() instanceof RockAndDirt)
        {
            if(((RockAndDirt)trySpot.getEntity()).isOpen())
            {
                return true;
            }
        }
        return false;
    }

}
