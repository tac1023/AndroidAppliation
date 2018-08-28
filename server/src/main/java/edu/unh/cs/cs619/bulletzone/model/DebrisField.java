package edu.unh.cs.cs619.bulletzone.model;

/**
 * Represents a DebrisField or a Forest. The name of this class is
 * misleading due to bad foresight in implementation and difficulties
 * with android studio not letting me commit after changing the name.
 *
 * @author Tyler Currier
 * @version 2.0
 * @since 4/26/2018
 */

public class DebrisField extends FieldEntity {
    private int value, pos;
    final int DF_DEFAULT = 3000;
    private final int F_DEFAULT = 3200;
    private forestOrDebrisState state;

    /**
     * Default Constructor
     *
     * @param forest Whether or not this is a forest
     */
    public DebrisField(boolean forest)
    {
        if(forest)
        {
            this.value = F_DEFAULT;
            state = new forestState();
        }
        else {
            this.value = DF_DEFAULT;
            state = new debrisFieldState();
        }
    }

    /**
     * Constructor for a DebrisField that knows its position
     *
     * @param forest Whether or not this is a forest
     * @param pos position of DebrisField
     */
    public DebrisField(boolean forest, int pos)
    {
        if(forest)
        {
            this.value = F_DEFAULT;
            state = new forestState();
        }
        else {
            this.value = DF_DEFAULT;
            state = new debrisFieldState();
        }
        this.pos = pos;
    }

    /**
     * Compute a hit on this object
     *
     * @param damage amount of damage dealt
     */
    @Override
    public void hit(int damage)
    {
        state.hit(damage, this);
    }

    /**
     * Copy constructor, creates a new basic DebrisField
     *
     * @return new DebrisField
     */
    @Override
    public FieldEntity copy()
    {
        return state.copy();
    }

    /**
     * Returns the Integer representation of this DebrisField
     *
     * @return ID of DebrisField
     */
    @Override
    public int getIntValue()
    {
        return value;
    }

    /**
     * Set a new value for this object, probably as a result of a
     * damage calculation
     *
     * @param value new value
     */
    void setIntValue(int value)
    {
        this.value = value;
    }

    /**
     * Returns the string representation of this DebrisField (as
     * opposed to pictographic representation)
     *
     * @return String
     */
    @Override
    public String toString()
    {
        return state.toString();
    }

    /**
     * Return the position of this DebrisField
     *
     * @return position
     */
    public int getPos()
    {
        return pos;
    }

    /**
     * Set a new state for this object
     *
     * @param state new debrisFieldState or forestState.
     */
    void setState(forestOrDebrisState state)
    {
        this.state = state;
    }

    /**
     * Return whether or not this is a forest
     *
     * @return boolean
     */
    public boolean isForest()
    {
        return state.isForest();
    }
}
