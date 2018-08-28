package edu.unh.cs.cs619.bulletzone.SoldierStrategies;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Soldier;

/**
 * Interface that defines how to structure a strategy for getting the integer
 * value of a soldier
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 5/6/2018
 */
public interface SoldierGetIntValueStrategy {

    /**
     * Get the integer value for the soldier
     *
     * @param soldier Soldier whose's value to get
     * @param id ID of soldier
     * @param life Life of soldier
     * @param direction Direction of soldier
     * @return int
     */
    int getIntValue(Soldier soldier, long id, int life, Direction direction);

    /**
     * Return whether this is the normal strategy
     *
     * @return boolean
     */
    boolean isNormal();
}
