package edu.unh.cs.cs619.bulletzone.tank_action;

import edu.unh.cs.cs619.bulletzone.Constraint.ConstraintHandler;
import edu.unh.cs.cs619.bulletzone.fire.FireBullet;
import edu.unh.cs.cs619.bulletzone.model.Bullet;
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.IllegalTransitionException;
import edu.unh.cs.cs619.bulletzone.model.LimitExceededException;
import edu.unh.cs.cs619.bulletzone.model.Tank;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Actions that a tank can take
 *
 * @author Tyler Currier
 * @version 2.0
 * @since 4/13/2018
 */

public class TankAction implements DoesAction {

    private Game game = null;
    //private int bulletDamage[]={10,30,50};
    //private int bulletDelay[]={500,1000,1500}; //Use for Milestone 2
    private int bulletDelay[]={500,500,500,250}; //Use for Milestone 1
    //private int trackActiveBullets[]={0,0};
    //private final Timer timer = new Timer();
    private FireBullet fireBullet;
    private Integer bulletIdCount = 0;

    /**
     * Constructor
     */
    public TankAction()
    {
        game = Game.getInstance();
        fireBullet = new FireBullet();
    }

    /**
     *  Turns the tank to the specified direction. If there is an invalid input the
     *  method returns the Tank's current location.
     *
     * @param constraintHandler Constraint handler to use
     * @param tank Tank to reference
     * @param direction direction to turn
     * @return boolean
     * @throws TankDoesNotExistException There is no such tank
     * @throws IllegalTransitionException Can't turn that way
     * @throws LimitExceededException Can't turn again
     */
    public boolean turn(ConstraintHandler constraintHandler, Tank tank,  Direction direction) throws TankDoesNotExistException,
            IllegalTransitionException, LimitExceededException
    {
            long millis = System.currentTimeMillis();
            if(!constraintHandler.checkTankTurnTime(tank, millis))
                return false;



            if(!constraintHandler.checkTankTurnDirection(tank, direction))
                return false;

            tank.setLastTurnTime(millis+tank.getAllowedTurnInterval());
            tank.setDirection(direction);

            return true;
    }

    /**
     * Moves the tank forward or backward, respectively. If there is an invalid input. The tank's
     * location remains constant.
     *
     * @param action Reference to Action
     * @param constraintHandler Constraint Handler to use
     * @param tank Tank to reference
     * @param direction direction to move
     * @return whether move was successful or not.
     * @throws TankDoesNotExistException There is no such tank
     * @throws IllegalTransitionException Can't move that way
     * @throws LimitExceededException Can't move again
     */
    public boolean move(Action action, ConstraintHandler constraintHandler, Tank tank, Direction direction) throws TankDoesNotExistException,
            IllegalTransitionException, LimitExceededException
    {
            long millis = System.currentTimeMillis();
            if(!constraintHandler.checkTankMoveTime(tank, millis, direction))
                return false;

            if(!constraintHandler.checkTankMoveDirection(tank, direction))
                return false;

            FieldHolder parent = tank.getParent();

            FieldHolder nextField = parent.getNeighbor(direction);

            checkNotNull(parent.getNeighbor(direction), "Neighbor is not available");

            return constraintHandler.checkTankParentNeighbor(tank, parent, nextField, millis);
    }

    /**
     * Fires a bullet in the direction that the tank is facing. If there are more than two of the
     * tank's bullets on the screen then, no new bullets are created and the method is false;
     *
     * @param constraintHandler Constraint Handler to use
     * @param tank tank to reference
     * @param bulletType type of bullet to fire
     * @param relativeDirection Direction relative to tank in which to fire
     * @return whether a bullet was successfully created
     * @throws TankDoesNotExistException There is no such tank
     * @throws LimitExceededException Can't fire again
     */
    public boolean fire(ConstraintHandler constraintHandler, Tank tank, int bulletType, int relativeDirection) throws TankDoesNotExistException,
            LimitExceededException
    {
        //If already 2 bullets, don't fire
        if(constraintHandler.checkTankBulletCount(tank))
            return false;

        //if not enough time has passed, don't fire
        long millis = System.currentTimeMillis();
        if(!constraintHandler.checkTankFireTime(tank, millis))
            return false;



        if( tank.getAllowedMoveInterval() == 500 && relativeDirection != 0 ) return false;

        //Log.i(TAG, "Cannot find user with id: " + tankId);
        Direction direction = tank.getDirection();
        FieldHolder parent = tank.getParent();
        tank.setNumberOfBullets(tank.getNumberOfBullets() + 1);

        //If invalid bullet type, default to 1
        if(!(bulletType>=1 && bulletType<=3)) {
            System.out.println("Bullet type must be 1, 2 or 3, set to 1 by default.");
            bulletType = 1;
        }



        tank.getSoldier().clearBullets();
        if(!tank.getState().isShipState())
            tank.clearBullets();

        int newDir = ((int)Direction.toByte(direction) + relativeDirection) % 8;
        // Create a new bullet to fire

        final Bullet bullet;
        if( relativeDirection != 0) {
            bullet = new Bullet(tank.getId(), Direction.fromByte((byte) newDir), 5 );
            bulletType = 4;
        } else
            bullet = new Bullet(tank.getId(), Direction.fromByte((byte) newDir), tank.getBulletDamage());
        //current time plus delay for bullet equals next time can fire
        tank.setLastFireTime(millis + bulletDelay[bulletType - 1]);

        if(tank.getState().isShipState())
        {
            bulletIdCount = (bulletIdCount + 1) % 10000;
            bullet.setSoldierBulletId(bulletIdCount);
            tank.storeBulletReference(bulletIdCount, bullet);
        }
        bullet.setParent(parent);
        fireBullet.fire(game, tank, bullet);
        return true;
    }

    /**
     * Pass along the drill invocation
     *
     * @param action Reference to action
     * @param tank Tank to reference
     * @return boolean
     */
    public boolean drill(Action action, Tank tank)
    {
        tank.setDrilling(true);
        return tank.drill(action);
    }

    /**
     * Return whether this can transform. Always true
     *
     * @return boolean
     */
    public boolean canTransform()
    {
        return true;
    }

    /**
     * Return that this is a tank action
     *
     * @return true
     */
    public boolean isTankAction()
    {
        return true;
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
     * Return that this isn't a missile action
     *
     * @return false
     */
    public boolean isMissileAction()
    {
        return false;
    }
}
