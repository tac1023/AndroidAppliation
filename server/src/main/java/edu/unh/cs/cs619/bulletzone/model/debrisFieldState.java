package edu.unh.cs.cs619.bulletzone.model;


/**
 * This is a state for the DebrisField Class. This state lets the DebrisField
 * Class represent a DebrisField
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 4/26/2018
 */

public class debrisFieldState implements forestOrDebrisState {

    /**
     * Return a new version of the Debris Field
     *
     * @return DebrisField
     */
    public FieldEntity copy()
    {
        return new DebrisField(false);
    }

    /**
     * define what a hit means for the DebrisField
     *
     * @param damage damage to deal
     */
    public void hit(int damage, DebrisField user)
    {

    }

    /**
     * return string representation of current state
     *
     * @return String
     */
    public String toString()
    {
        return "DF";
    }

    /**
     * return whether the state is a forest or not
     *
     * @return boolean
     */
    public boolean isForest()
    {
        return false;
    }

}
