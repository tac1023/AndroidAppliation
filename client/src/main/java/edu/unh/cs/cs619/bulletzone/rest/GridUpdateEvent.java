package edu.unh.cs.cs619.bulletzone.rest;

import edu.unh.cs.cs619.bulletzone.util.GridWrapper;

/**
 * This is the event that the Poller sends over the EventBus
 * and listeners receive. It carries a grid wrapped in
 * a GridWrapper object. This object is public so listeners
 * can access it directly.
 *
 * @author Simon
 * @version 1.0
 * @since 10/3/14
 */
public class GridUpdateEvent {
    public GridWrapper gw;
    public boolean isReplay = false;

    /**
     * Constructor that accepts a GridWrapper and assigns it to the
     * Public GridWrapper variable.
     *
     * @param gw GridWrapper that this event should deliver
     */
    public GridUpdateEvent(GridWrapper gw) {
        this.gw = gw;
    }

    /**
     * Secondary constructor used to deal with the replay functionality.
     * @param gw
     * @param isReplay
     */
    public GridUpdateEvent(GridWrapper gw, boolean isReplay) {
        this.gw = gw;
        this.isReplay = isReplay;
    }
}
