package edu.unh.cs.cs619.bulletzone.events;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import org.androidannotations.annotations.EBean;

/**
 * Singleton that holds the app-wide eventbus
 *
 * @author Stephen Asherson.
 * @version 1.0
 * @since ???
 */
@EBean(scope = EBean.Scope.Singleton)
public class BusProvider
{
    private static Bus eventBus;

    /**
     * Lazy load the event bus
     */
    public synchronized Bus getEventBus()
    {
        if (eventBus == null)
        {
            eventBus = new Bus(ThreadEnforcer.ANY);
        }

        return eventBus;
    }
}