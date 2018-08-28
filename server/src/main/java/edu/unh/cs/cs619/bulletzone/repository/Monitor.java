package edu.unh.cs.cs619.bulletzone.repository;

/**
 * Singleton mutex to lock actions that access common memory
 * within different classes.
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 4/11/2018
 */

public final class Monitor {

    private static Monitor _instance;

    /**
     * Constructor
     */
    private Monitor()
    {
    }

    /**
     * Create Monitor if one doesn't already exist. Return monitor
     *
     * @return Monitor
     */
    public static Monitor getInstance()
    {
        if(_instance == null)
            _instance = new Monitor();
        return _instance;
    }

}
