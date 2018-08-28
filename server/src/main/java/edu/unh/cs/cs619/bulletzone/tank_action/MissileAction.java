package edu.unh.cs.cs619.bulletzone.tank_action;

import edu.unh.cs.cs619.bulletzone.Constraint.ConstraintHandler;
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.IllegalTransitionException;
import edu.unh.cs.cs619.bulletzone.model.Missile;
import edu.unh.cs.cs619.bulletzone.model.Tank;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;

/**
 * What the controls should do when the player is controlling a Missile
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 5/6/2018
 */

public class MissileAction implements DoesAction {

    /**
     * Override turn to instead tell the missile to shift left or right
     *
     * @param constraintHandler constraints to use
     * @param tank related tank to turn
     * @param direction Direction to turn entity
     * @return boolean
     * @throws TankDoesNotExistException There is no such tank
     * @throws IllegalTransitionException Can't move there
     */
    public boolean turn(ConstraintHandler constraintHandler, Tank tank, Direction direction)
            throws TankDoesNotExistException, IllegalTransitionException
    {
        Missile missile = tank.getMissile();
        if(missile == null)
            return false;
        missile.setShift(direction);
        return true;
    }

    /**
     * Missiles can't "move", so just return false
     *
     * @param action reference to action
     * @param constraintHandler constraints to use
     * @param Tank reference to tank
     * @param direction direction
     * @return false
     */
    public boolean move(Action action, ConstraintHandler constraintHandler, Tank Tank,
                        Direction direction)
    {
        return false;
    }

    /**
     * Missiles can't "fire", so just return false
     *
     * @param constraintHandler constraints to use
     * @param tank related tank to turn
     * @param bulletType type of bullet to fire
     * @param relativeDirection Direction relative to the tank's forward direction
     *                          in which to fire the bullet
     * @return false
     */
    public boolean fire(ConstraintHandler constraintHandler, Tank tank, int bulletType,
                        int relativeDirection)
    {
        return false;
    }

    /**
     * Missiles can't drill, return false
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
     * Missiles can't transform, return false
     *
     * @return false
     */
    public boolean canTransform()
    {
        return false;
    }

    /**
     * Return that this isn't a tank action
     *
     * @return false
     */
    public boolean isTankAction()
    {
        return false;
    }

    /**
     * Return that this isn't a soldier action
     *
     * @return false
     */
    public boolean isSoldierAction()
    {
        return false;
    }

    /**
     * Return that this is a missile action
     *
     * @return true
     */
    public boolean isMissileAction()
    {
        return true;
    }
}
