package edu.unh.cs.cs619.bulletzone.SoldierStrategies;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Soldier;

/**
 * Define values to return for soldier for it's death animation
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 5/6/2018
 */

public class SoldierGetIntValueDead implements SoldierGetIntValueStrategy {

    private int value = 250;

    /**
     * Return a countdown for the soldier death animation
     *
     * @param soldier Soldier for reference
     * @param id ID of soldier
     * @param life life of soldier
     * @param direction direction of soldier
     * @return int
     */
    public int getIntValue(Soldier soldier, long id, int life, Direction direction)
    {
        int ret = value--;
        if(value < 200)
            soldier.getParent().clearTankItem();
        return ret;
    }

    /**
     * Return that this strategy is not Normal
     *
     * @return false
     */
    public boolean isNormal()
    {
        return false;
    }
}
