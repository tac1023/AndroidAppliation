package edu.unh.cs.cs619.bulletzone.tank_action;

import edu.unh.cs.cs619.bulletzone.Constraint.ConstraintHandler;
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.IllegalTransitionException;
import edu.unh.cs.cs619.bulletzone.model.LimitExceededException;
import edu.unh.cs.cs619.bulletzone.model.SoldierDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.Tank;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;

/**
 * Interface that defines what player action classes
 * should look like
 *
 * @author Tyler Currier
 * @version 2.0
 * @since 5/2/18
 */

public interface DoesAction {

    /**
     * Turn the entity currently being controlled by the player
     *
     * @param constraintHandler constraints to use
     * @param tank related tank to turn
     * @param direction Direction to turn entity
     * @return boolean, if successful
     * @throws TankDoesNotExistException no tank with that ID
     * @throws IllegalTransitionException can't turn that way
     * @throws LimitExceededException ??
     */
    boolean turn(ConstraintHandler constraintHandler, Tank tank,
                 Direction direction) throws TankDoesNotExistException,
            IllegalTransitionException, LimitExceededException, SoldierDoesNotExistException;

    /**
     * Move the entity currently being controlled by the player
     *
     * @param constraintHandler constraints to use
     * @param tank related tank to turn
     * @param currentDirection Direction to move entity
     * @return boolean, if successful
     * @throws TankDoesNotExistException no thank with that ID
     * @throws IllegalTransitionException can't move that direction
     * @throws LimitExceededException ??
     */
    boolean move(Action action, ConstraintHandler constraintHandler, Tank tank, Direction currentDirection) throws TankDoesNotExistException,
            IllegalTransitionException, LimitExceededException, SoldierDoesNotExistException;

    /**
     * Fire a bullet from the entity currently being controlled by
     * the player
     *
     * @param constraintHandler constraints to use
     * @param tank related tank to turn
     * @param bulletType type of bullet to fire
     * @param relativeDirection Direction relative to the tank's forward direction
     *                          in which to fire the bullet
     * @return boolean, if successful
     * @throws TankDoesNotExistException no tank with that ID
     * @throws LimitExceededException too many bullets
     */
    boolean fire(ConstraintHandler constraintHandler, Tank tank, int bulletType, int relativeDirection) throws TankDoesNotExistException,
            LimitExceededException, SoldierDoesNotExistException;

    /**
     * As a Tunneler, Drill down or up if there is no tunnel, or just travel through the
     * tunnel if there is one. As a tank or soldier, travel through the tunnel if there is
     * one.
     *
     * @param action Reference to action
     * @param tank Tank to reference
     * @return boolean
     */
    boolean drill(Action action, Tank tank);


    /**
     * Return whether the vehicle can transform
     *
     * @return boolean
     */
    boolean canTransform();

    /**
     * Return whether the action is a tank action
     *
     * @return boolean
     */
    boolean isTankAction();

    /**
     * Return whether the action is a soldier action
     *
     * @return boolean
     */
    boolean isSoldierAction();

    /**
     * Return whether the action is a Missile action
     *
     * @return boolean
     */
    boolean isMissileAction();
}
