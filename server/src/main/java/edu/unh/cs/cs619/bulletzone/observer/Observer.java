package edu.unh.cs.cs619.bulletzone.observer;

import edu.unh.cs.cs619.bulletzone.model.Controllable;
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.tank_action.Action;

/**
 * This class allows a subscriber (a WaterCurrent cell) to send a message
 * to the action class. For now, it is just being used to send a message
 * to move the Controllable that is currently sitting in that WaterCurrent
 * cell.
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 5/4/2018
 */

public class Observer {

    private Action action;
    private static Observer _instance;

    /**
     * Default Constructor, accepts
     *
     * @param action
     */
    private Observer(Action action)
    {
        this.action = action;
    }

    /**
     * Get instance method for singleton pattern. This is always called by GamesController
     * first, so action should never be null.
     *
     * @param action Reference to action
     * @return Observer
     */
    public static Observer getInstance(Action action)
    {
        if(_instance == null)
            _instance = new Observer(action);
        return _instance;
    }

    /**
     * Tell action to push the Controllable in the specified direction
     *
     * @param controllable Controllable to move
     * @param direction Direction to move
     * @return boolean
     */
    public boolean notifyControllablePushedByCurrent(Controllable controllable, Direction direction)
    {
        if(action == null)
        {
            return false;
        }
        try {
            return action.currentMove(controllable.getId(), direction);
        }
        catch(TankDoesNotExistException e)
        {
            return false;
        }
    }

}
