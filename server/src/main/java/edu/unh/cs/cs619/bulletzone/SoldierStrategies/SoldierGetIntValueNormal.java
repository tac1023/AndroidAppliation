package edu.unh.cs.cs619.bulletzone.SoldierStrategies;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Soldier;

/**
 * Strategy for getting the integer value of a live soldier
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 5/6/2018
 */

public class SoldierGetIntValueNormal implements SoldierGetIntValueStrategy {

    /**
     * Return integer value of alive soldier
     *
     * @param soldier Soldier whose's value to get
     * @param id ID of soldier
     * @param life Life of soldier
     * @param direction Direction of soldier
     * @return int
     */
    public int getIntValue(Soldier soldier, long id, int life, Direction direction)
    {
        return (int) (1000000 + 1000 * id + 10 * life + Direction
                .toByte(direction));
    }

    /**
     * Return that this is the normal strategy
     *
     * @return true
     */
    public boolean isNormal()
    {
        return true;
    }
}
