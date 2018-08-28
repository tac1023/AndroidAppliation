package edu.unh.cs.cs619.bulletzone.model;

/**
 * This is a state for the DebrisField Class. This state lets the DebrisField
 * Class represent a Forest.
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 4/26/2018
 */

public class forestState implements forestOrDebrisState {

    /**
     * Return a new version of the Debris Field
     *
     * @return DebrisField
     */
    public FieldEntity copy()
    {
        return new DebrisField(true);
    }

    /**
     * define what a hit means for the DebrisField
     *
     * @param damage damage to deal
     */
    public void hit(int damage, DebrisField user)
    {
        int temp = user.getIntValue();
        temp = temp - damage;
        if(temp < user.DF_DEFAULT)
        {
            user.setIntValue(user.DF_DEFAULT);
            user.setState(new debrisFieldState());
        }
        else
            user.setIntValue(temp);
    }

    /**
     * return string representation of current state
     *
     * @return String
     */
    public String toString()
    {
        return "F";
    }

    /**
     * return whether the state is a forest or not
     *
     * @return boolean
     */
    public boolean isForest()
    {
        return true;
    }
}
