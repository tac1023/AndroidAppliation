package edu.unh.cs.cs619.bulletzone.Constraint;


import java.util.concurrent.TimeUnit;

import edu.unh.cs.cs619.bulletzone.model.Coast;
import edu.unh.cs.cs619.bulletzone.model.DebrisField;
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.FieldEntity;
import edu.unh.cs.cs619.bulletzone.model.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.Hill;
import edu.unh.cs.cs619.bulletzone.model.RockAndDirt;
import edu.unh.cs.cs619.bulletzone.model.Soldier;
import edu.unh.cs.cs619.bulletzone.model.Tank;
import edu.unh.cs.cs619.bulletzone.model.Water;
import edu.unh.cs.cs619.bulletzone.model.WaterCurrent;
import edu.unh.cs.cs619.bulletzone.repository.Monitor;
import edu.unh.cs.cs619.bulletzone.tank_action.TankAction;

import static edu.unh.cs.cs619.bulletzone.model.Direction.Above;
import static edu.unh.cs.cs619.bulletzone.model.Direction.Below;
import static edu.unh.cs.cs619.bulletzone.model.Direction.Down;
import static edu.unh.cs.cs619.bulletzone.model.Direction.Left;
import static edu.unh.cs.cs619.bulletzone.model.Direction.Right;
import static edu.unh.cs.cs619.bulletzone.model.Direction.Up;

/**
 * Created by Tyler on 4/4/2018.
 *
 * @author Tyler Currier, Jason Vettese
 * @version 3.0
 * @since 5/2/2018
 */
public class ConstraintHandler {
    private static final int soldierMaxLife = 25;
    private final Monitor monitor = Monitor.getInstance();

    /**
     * Empty Constructor.
     */
    public ConstraintHandler()
    {

    }

    //Public Methods:

    /**
     * Checks to see if the Tanks movement is illegal or not.
     *
     * @param tank tank to check
     * @param millis current time
     * @param direction direction of desired move
     * @return true if tank can move
     */
    public boolean checkTankMoveTime(Tank tank, long millis, Direction direction)
    {
        if(tank.getParent().isPresent() && tank.getParent().getEntity() instanceof WaterCurrent)
        {
            return (millis >= tank.getLastMoveTime() +
                    ((WaterCurrent) tank.getParent().getEntity()).getModifier(direction));
        }
        return (millis >= tank.getLastMoveTime());
    }


    /**
     * Checks to see if the tank is making illegal turn
     *
     * @param tank tank to check
     * @param direction requested direction
     * @return true if tank is allowed to turn
     */
    public boolean checkTankMoveDirection(Tank tank, Direction direction)
    {

        switch (tank.getDirection()){
            case Up:
                if( (direction == Left) || (direction == Right)) return false;
                break;
            case Down:
                if( (direction == Left) ||(direction == Right)) return false;
                break;
            case Right:
                if( (direction == Up) || (direction == Down)) return false;
                break;
            case Left:
                if( (direction == Up) || (direction == Down)) return false;
                break;

        }
        if(tank.isDrilling()) {
            return (direction == Above || direction == Below);
        }
        return true;
    }

    /**
     * Checks if the desired location is occupied.
     *
     * @param tank tank to check
     * @param parent tank's parent
     * @param nextField parent's neighbor
     * @param millis Current time
     * @return whether tank can move or not
     */
    public boolean checkTankParentNeighbor(Tank tank, FieldHolder parent, FieldHolder nextField, long millis)
    {
        boolean clearDirt = false;
        if( tank.getIntValue() >= 30000000 && tank.getIntValue() < 40000000 )
            return checkShipParentNeighbor( tank, parent, nextField, millis );

        if(!tank.canEnter(nextField)) {
            return false;
        }
        else if(nextField.isPresent() && nextField.getEntity() instanceof Hill) {
            try {
                TimeUnit.MILLISECONDS.sleep(tank.getAllowedMoveInterval());
                millis += tank.getAllowedFireInterval();
            }
            catch(InterruptedException e)
            {
                System.err.println("Tank Wait On Hill Entry Interrupted");
            }
        }
        else if(nextField.isPresent() && nextField.getEntity() instanceof RockAndDirt)
        {
            tank.setDrilling(true);
            if(((RockAndDirt) nextField.getEntity()).isDestructible())
            {
                try
                {
                    TimeUnit.MILLISECONDS.sleep(tank.getAllowedDigInterval());
                }
                catch(InterruptedException e)
                {
                    System.err.println("Tank Wait On Dig Interrupted");
                }
                clearDirt = true;
            }
        }
        synchronized (this.monitor) {
            if(!tank.canEnter(nextField))
                return false;
            if(clearDirt)
                ((RockAndDirt) nextField.getEntity()).clearDirt();
            tank.setLastMoveTime(millis + tank.getAllowedMoveInterval());
            parent.clearTankItem();

            nextField.setTankEntity(tank);
            tank.setParent(nextField);
            tank.setDrilling(false);

            return true;
        }

    }

    /**
     * Checks the neighbor of the ship to ensure that it can move.
     *
     * @param tank tank to check
     * @param parent tank's parent
     * @param nextField parent's neighbor
     * @param millis current time
     * @return true if it can move to that space
     */
    private boolean checkShipParentNeighbor(Tank tank, FieldHolder parent, FieldHolder nextField, long millis) {

        if( !nextField.isPresent() )
            return false;

        if((nextField.getEntity() instanceof Coast) || (nextField.getEntity() instanceof Water)) {
            tank.setLastMoveTime(millis + tank.getAllowedMoveInterval());
            parent.clearTankItem();

            if(parent.isPresent() && parent.getEntity() instanceof WaterCurrent)
            {
                ((WaterCurrent) parent.getEntity()).removeOccupier();
            }

            nextField.setTankEntity(tank);
            tank.setParent(nextField);

            if(tank.getParent().isPresent() && tank.getParent().getEntity() instanceof WaterCurrent)
            {
                ((WaterCurrent) tank.getParent().getEntity()).setOccupier(tank);
            }

            return true;
        }
        return false;
    }

    /**
     * Checks to see if tank is turning to fast.
     *
     * @param tank tank to check
     * @param millis current time
     * @return whether tank can turn or not
     */
    public boolean checkTankTurnTime(Tank tank, long millis)
    {
        return (millis >= tank.getLastTurnTime());
    }

    /**
     * Checks to see if the tank turn is a valid direction.
     *
     * @param tank tank to check
     * @param direction requested direction
     * @return whether tank can turn
     */
    public boolean checkTankTurnDirection(Tank tank, Direction direction)
    {

        switch (tank.getDirection()){
            case Up:
                if( direction == Down) return false;
                break;
            case Down:
                if( direction == Up) return false;
                break;
            case Right:
                if( direction == Left) return false;
                break;
            case Left:
                if( direction == Right) return false;
                break;

        }
        return true;
    }

    /**
     * Checks if tank is firing too quickly
     *
     * @param tank tank to check
     * @param millis current time
     * @return true if 500ms passed
     */
    public boolean checkTankFireTime(Tank tank, long millis)
    {
        return (millis >= tank.getLastFireTime());
    }

    /**
     * Ensures that tank can have more than 2 bullets on screen.
     *
     * @param tank tank to check
     * @return true if bullets are <= 2
     */
    public boolean checkTankBulletCount(Tank tank)
    {
        return tank.getNumberOfBullets() >= tank.getAllowedNumberOfBullets();
    }

    /**
     * Ejects a soldier if possible.
     *
     * @param tank tank to check
     * @param millis current time
     * @return true if ejection was successful
     */
    public boolean checkLastEjectTime( Tank tank, long millis){
        return (millis >= tank.getLastEjectTime());
    }

    /**
     * Decides whether a soldier is turning correctly.
     *
     * @param soldier soldier to check
     * @param direction requested direction
     * @return true if soldier makes a valid turn
     */
    public boolean checkSoldierTurnDirection(Soldier soldier, Direction direction) {
        switch (soldier.getDirection()){
            case Up:
                if( direction == Down) return false;
                break;
            case Down:
                if( direction == Up) return false;
                break;
            case Right:
                if( direction == Left) return false;
                break;
            case Left:
                if( direction == Right) return false;
                break;

        }
        return true;
    }

    /**
     * Check if enough time has passed for soldier to move
     *
     * @param soldier soldier to check
     * @param millis current time
     * @return true if soldier can move
     */
    public boolean checkSoldierMoveTime(Soldier soldier, long millis, Direction direction) {
        if((soldier.getParent().getNeighbor(direction) != null) && (soldier.getParent().getNeighbor(direction).isPresent()))
        {
            if(soldier.getParent().getNeighbor(direction).getEntity() instanceof Coast) {
                return (millis >= soldier.getLastMoveTime() + 500);
            }
            if(soldier.getParent().getNeighbor(direction).getEntity() instanceof Water) {
                return (millis >= soldier.getLastMoveTime() + 1000);
            }
        }
        return (millis >= soldier.getLastMoveTime());
    }

    /**
     * Check if soldier is turning in a valid direction
     *
     * @param soldier soldier to check
     * @param direction requested direction
     * @return true if soldier can move
     */
    public boolean checkSoldierMoveDirection(Soldier soldier, Direction direction) {
        switch (soldier.getDirection()){
            case Up:
                if( (direction == Left) || (direction == Right)) return false;
                break;
            case Down:
                if( (direction == Left) ||(direction == Right)) return false;
                break;
            case Right:
                if( (direction == Up) || (direction == Down)) return false;
                break;
            case Left:
                if( (direction == Up) || (direction == Down)) return false;
                break;

        }
        if(soldier.isDrilling()) {
            return (direction == Above || direction == Below);
        }
        return true;

    }

    /**
     * Check if there is space in the next field for the soldier to move
     *
     * @param soldier soldier to check
     * @param parent soldier's parent
     * @param nextField parent's neighbor
     * @param millis current time
     * @return true if soldier can move
     */
    public boolean checkSoldierParentNeighbor( Soldier soldier, FieldHolder parent,
                                              FieldHolder nextField, long millis) {
        if (!soldier.canEnter(nextField)) {
            return false;
        }

        if(nextField.isPresent() && nextField.getEntity() instanceof DebrisField) {
            if(!((DebrisField)(nextField.getEntity())).isForest())
            {
                try {
                    TimeUnit.MILLISECONDS.sleep(soldier.getAllowedMoveInterval());
                    millis += soldier.getAllowedMoveInterval();
                } catch (InterruptedException e) {
                    System.err.println("Tank Wait On Hill Entry Interrupted");
                }
            }
        }
        else if(nextField.isTankPresent() && nextField.getTankItem() instanceof Tank)
        {
            long id = ((Tank) nextField.getTankItem()).getId();
            if(soldier.getId() == id)
            {
                synchronized (this.monitor) {
                    if (!soldier.canEnter(nextField))
                        return false;
                    Tank tank = (Tank) nextField.getTankItem();
                    tank.setSoldierOut(false);
                    tank.setLastEjectTime(millis +
                            tank.getAllowedEjectInterval());
                    tank.setCurrentAction(new TankAction());

                    soldier.setLife(soldierMaxLife);

                    parent.clearTankItem();
                    soldier.setParent(null);
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
        else if(nextField.isTankPresent()) //fail if there is another tank item
            return false;

        synchronized (this.monitor) {
            if (!soldier.canEnter(nextField))
                return false;

            soldier.setLastMoveTime(millis + soldier.getAllowedMoveInterval());
            parent.clearTankItem();
            nextField.setTankEntity(soldier);
            soldier.setParent(nextField);

            return true;
        }

    }

    /**
     * Check if soldier has too many bullets
     *
     * @param soldier soldier to check
     * @return true if soldier is allowed to fire
     */
    public boolean checkSoldierBulletCount(Soldier soldier) {
        return (soldier.getNumberOfBullets() >= soldier.getAllowedNumberOfBullets());
    }

    /**
     * Check if enough time has passed for soldier to fire
     *
     * @param soldier soldier to check
     * @param millis current time
     * @return true if soldier is allowed to fire
     */
    public boolean checkSoldierFireTime(Soldier soldier, long millis) {
        return (millis >= soldier.getLastFireTime());
    }


    /**
     * Checks that tank is in correct position to transform. Only Hill and coast have been
     * accounted for since all other cases are handled by normal tank constraints.
     *
     * @param tank Tank to check
     * @return false if on hill or on coast
     */
    public boolean checkTankToTunnelParent( Tank tank ) {
        if(tank.getParent().isPresent()) {
            FieldEntity fe = tank.getParent().getEntity();
            if (fe instanceof Hill || fe instanceof Coast) return false;
        }
        return true;
    }

    /**
     * Checks whether tank can turn into ship.
     *
     * @param tank Tank to check
     * @return true if on coast
     */
    public boolean checkTankToShipParent( Tank tank ) {
        if(tank.getParent().isPresent()) {
            FieldEntity fe = tank.getParent().getEntity();
            if (fe instanceof Coast) return true;
        }
        return false;
    }

    //End Public Methods
}
