package edu.unh.cs.cs619.bulletzone.model;

/**
 * Interface for the DebrisField and Forest states
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 4/26/2018
 */
public interface forestOrDebrisState {

    /**
     * Return a new version of the current state
     *
     * @return some object
     */
    FieldEntity copy();

    /**
     * define what a hit means for the current state
     *
     * @param damage damage to deal
     */
    void hit(int damage, DebrisField user);

    /**
     * return string representation of current state
     *
     * @return String
     */
    String toString();

    /**
     * return whether the state is a forest or not
     *
     * @return boolean
     */
    boolean isForest();

}
