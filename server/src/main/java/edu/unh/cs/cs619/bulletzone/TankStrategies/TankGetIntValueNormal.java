package edu.unh.cs.cs619.bulletzone.TankStrategies;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.Tank;

/**
 * Normal getIntValue strategy for tanks
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 4/28/2018
 */

public class TankGetIntValueNormal implements TankGetIntValueStrategy {

    /**
     * Return integer value for this tank
     *
     * @param tank      Tank
     * @param id        ID of tank
     * @param life      life of tank
     * @param direction Direction of tank
     * @return int
     */
    public int getIntValue(Tank tank, long id, int life, Direction direction) {
        return (int) (10000000 + 10000 * id + 10 * life + Direction
                .toByte(direction));
    }

    /**
     * Return that this isn't the onFireStrategy
     *
     * @return false
     */
    public boolean isOnFire() {
        return false;
    }

    /**
     * Return that this is the normalStrategy
     *
     * @return true
     */
    public boolean isNormal()
    {
        return true;
    }
}
