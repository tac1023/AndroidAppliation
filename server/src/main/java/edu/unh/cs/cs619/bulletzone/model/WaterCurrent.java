package edu.unh.cs.cs619.bulletzone.model;

import edu.unh.cs.cs619.bulletzone.observer.Observer;

/**
 * Water with current properties. Ships moving into the current take longer.
 * Ships moving with the current move faster. Ships moving parallel to the
 * current are unaffected. A ship sitting in water with current for 4
 * seconds will be pushed to the next spot in the direction of the
 * current.
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 5/4
 */

public class WaterCurrent extends Water {
    private volatile Direction direction;
    private volatile Controllable occupier;
    private final int waitTime = 4000;
    private volatile long moveTime = -1;
    private volatile boolean occupied = false;
    private volatile Observer observer;
    private final int absoluteModifier = 500;

    /**
     * Construction for Water with a defined current
     *
     * @param direction Direction of current
     */
    public WaterCurrent(Direction direction)
    {
        value = 20 + Direction.toByte(direction);
        this.direction = direction;
        observer = Observer.getInstance(null);
    }

    /**
     * copy this object
     *
     * @return FieldEntity
     */
    @Override
    public FieldEntity copy()
    {
        return new WaterCurrent(direction);
    }

    /**
     * A new Controllable has entered this water.
     *
     * @param occupier New Controllable
     */
    public void setOccupier(Controllable occupier)
    {
        this.occupier = occupier;
        occupied = true;
        if(this.occupier instanceof Tank)
            pushControllable();
    }

    /**
     * The Controllable has left this water
     */
    public void removeOccupier()
    {
        occupier = null;
        occupied = false;
    }

    /**
     * Get the Controllable in this water.
     *
     * @return Controllable
     */
    public Controllable getOccupier()
    {
        return occupier;
    }

    /**
     * Return the modifier for the movement direction of the
     * controllable moving. Return negative modifier (faster) if moving
     * in same direction as current, positive (slower) if in opposite
     * direction, or 0 (no modifier) if perpendicular.
     *
     * @param direction Direction Controllable is moving
     * @return modifier
     */
    public int getModifier(Direction direction)
    {
        int diff = Direction.toByte(this.direction) - Direction.toByte(direction);

        if(diff == 0)
            return (absoluteModifier * -1);
        else if(diff == 4 || diff == -4)
            return absoluteModifier;
        else
            return 0;
    }

    /**
     * Push the occupier after 4 seconds if it is still there.
     */
    private void pushControllable()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean stop = false;
                while(!stop) {
                    moveTime = System.currentTimeMillis() + waitTime;
                    while (System.currentTimeMillis() < moveTime) {
                        //Wait until Current time equal moveTime
                        if (!occupied)
                            return;
                    }
                    if (occupied) {
                        stop = observer.notifyControllablePushedByCurrent(occupier, direction);
                    }
                }
            }
        }).start();
    }
}
