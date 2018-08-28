package edu.unh.cs.cs619.bulletzone.tank_action;


import edu.unh.cs.cs619.bulletzone.Constraint.ConstraintHandler;
import edu.unh.cs.cs619.bulletzone.fire.FireBullet;
import edu.unh.cs.cs619.bulletzone.model.Bullet;
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.IllegalTransitionException;
import edu.unh.cs.cs619.bulletzone.model.LimitExceededException;
import edu.unh.cs.cs619.bulletzone.model.Soldier;
import edu.unh.cs.cs619.bulletzone.model.SoldierDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.Tank;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.Tunnel;
import edu.unh.cs.cs619.bulletzone.model.Water;

import static com.google.common.base.Preconditions.checkNotNull;
/**
 * Created by Tyler on 4/13/2018.
 * Actions that a soldier can take.
 * @author Jason Vettese, Tyler Currier
 * @version 1.0
 * @since 4/13/2018
 */
public class SoldierAction implements DoesAction {

    private Game game = null;
    private FireBullet fireBullet;

    private Integer bulletIdCount = 0;

    /**
     * Constructor.
     */
    SoldierAction()
    {
        game = Game.getInstance();
        fireBullet = new FireBullet();
    }


    /**
     * Turn the soldier with the given tankId to face the given direction
     *
     * @param constraintHandler Constraint Handler to use
     * @param tank Tank
     * @param direction Direction to turn entity
     * @return success or failure
     * @throws TankDoesNotExistException There is no such tank
     * @throws IllegalTransitionException Can't turn that way
     * @throws SoldierDoesNotExistException There is no such soldier
     */
    public boolean turn(ConstraintHandler constraintHandler, Tank tank, Direction direction) throws TankDoesNotExistException,
            IllegalTransitionException, SoldierDoesNotExistException
    {
            Soldier soldier = tank.getSoldier();

            if (soldier == null) throw new SoldierDoesNotExistException(tank.getId());

            if(!constraintHandler.checkSoldierTurnDirection(soldier, direction))
                return false;

            soldier.setDirection(direction);
            return true;
    }

    /**
     * Move the soldier being controlled by the player.
     *
     * @param tank ID of soldier to move
     * @param constraintHandler Direction to move direction
     * @param direction direction to move
     * @param action reference to action class
     * @return boolean, if successful
     * @throws TankDoesNotExistException no soldier with that ID
     * @throws IllegalTransitionException can't move that way
     * @throws LimitExceededException ??
     */
    public boolean move(Action action, ConstraintHandler constraintHandler, Tank tank, Direction direction) throws TankDoesNotExistException,
            SoldierDoesNotExistException, IllegalTransitionException, LimitExceededException
    {
            Soldier soldier = tank.getSoldier();
            if( soldier == null ) throw new SoldierDoesNotExistException(tank.getId());


            long millis = System.currentTimeMillis();
            if(!constraintHandler.checkSoldierMoveTime(soldier, millis, direction))
                return false;

            if(!constraintHandler.checkSoldierMoveDirection(soldier, direction))
                return false;

            FieldHolder parent = soldier.getParent();

            FieldHolder nextField = parent.getNeighbor(direction);

            checkNotNull(parent.getNeighbor(direction), "Neightbor is not available");

            return constraintHandler.checkSoldierParentNeighbor(soldier, parent, nextField, millis);
    }

    /**
     * Fire a bullet from the soldier being controlled by the player
     *
     * @param constraintHandler Constraint Handler to use
     * @param tank ID of soldier from which to fire bullet
     * @param bulletType type of bullet to fire
     * @param relativeDirection Direction relative to soldier to fire bullet
     * @return boolean, if successful
     * @throws TankDoesNotExistException no soldier with that ID
     * @throws LimitExceededException too many bullets
     */
    public boolean fire(ConstraintHandler constraintHandler, Tank tank, int bulletType, int relativeDirection ) throws TankDoesNotExistException,
            SoldierDoesNotExistException, LimitExceededException
    {

            Soldier soldier = tank.getSoldier();
            if( soldier == null ) throw new SoldierDoesNotExistException(tank.getId());

            //If in open water, can't fire, return false
            if(soldier.getParent().isPresent() && soldier.getParent().getEntity() instanceof Water)
                return false;

            //If already 6 bullets, don't fire
            if(constraintHandler.checkSoldierBulletCount(soldier)) return false;

            //if not enough time has passed, don't fire
            long millis = System.currentTimeMillis();
            if(!constraintHandler.checkSoldierFireTime(soldier, millis))
                return false;

            Direction direction = soldier.getDirection();
            FieldHolder parent = soldier.getParent();
            soldier.setNumberOfBullets(soldier.getNumberOfBullets() + 1);

            //current time plus delay for bullet equals next time can fire
            soldier.setLastFireTime(millis + soldier.getAllowedFireInterval());

            int newDir = ((int)Direction.toByte(direction) + relativeDirection) % 8;


            // Create a new bullet to fire
            final Bullet bullet = new Bullet(tank.getId(), Direction.fromByte((byte)newDir), 5);

            bulletIdCount = (bulletIdCount + 1) % 10000;
            bullet.setSoldierBulletId(bulletIdCount);
            soldier.storeBulletReference(bulletIdCount, bullet);

            // Set the same parent for the bullet.
            // This should be only a one way reference.
            bullet.setParent(parent);
            //bullet.setBulletId(bulletId);

            // TODO make it nicer
            System.out.println("Active Bullet: " + soldier.getNumberOfBullets() + "---- Bullet ID: " + bullet.getIntValue());
            fireBullet.fire(game, soldier, bullet);

            return true;

    }

    /**
     * Override drill to allow soldiers to travel through tunnels.
     *
     * @param action Reference to action
     * @param tank Tank to reference
     * @return boolean
     */
    public boolean drill(Action action, Tank tank)
    {
        Soldier soldier = tank.getSoldier();
        soldier.setDrilling(true);

        FieldHolder originalParent = soldier.getParent();

        if(originalParent.getNeighbor(Direction.Below) == null)
        {
            if(originalParent.getNeighbor(Direction.Above).isPresent())
            {
                if(!(originalParent.getNeighbor(Direction.Above).getEntity() instanceof Tunnel)) {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else if(!originalParent.isPresent())
            return false;
        else if(!(originalParent.getEntity() instanceof Tunnel)) {
            return false;
        }

        if(originalParent.getNeighbor(Direction.Above) == null) {
            try {
                return move(action, tank.getConstraintHandler(), tank, Direction.Below);
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
                return move(action, tank.getConstraintHandler(), tank, Direction.Above);
            }
            catch (TankDoesNotExistException | IllegalTransitionException | LimitExceededException
                    | SoldierDoesNotExistException e)
            {
                return false;
            }
        }
    }

    /**
     * Return whether this can transform. Always false
     *
     * @return boolean
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
     * Return that this is a soldier action
     *
     * @return true
     */
    public boolean isSoldierAction()
    {
        return true;
    }

    /**
     * Return that this isn't a missile action
     *
     * @return false
     */
    public boolean isMissileAction()
    {
        return false;
    }
}
