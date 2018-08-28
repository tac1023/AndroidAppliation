package edu.unh.cs.cs619.bulletzone.model;

/**
 * Created by Tyler on 4/27/2018.
 *
 * @author Tyler Currier
 * @version 1.1
 * @since 5/2/2018
 */

public class Water extends FieldEntity{
    int value;

    /**
     * Default Constructor
     */
    public Water()
    {
        value = 2;
    }

    /**
     * Return integer value of this object
     *
     * @return int
     */
    @Override
    public int getIntValue()
    {
        return value;
    }

    /**
     * copy this object
     *
     * @return FieldEntity
     */
    @Override
    public FieldEntity copy()
    {
        return new Water();
    }

    /**
     * Return string value of this object
     *
     * @return String
     */
    @Override
    public String toString()
    {
        return "WT";
    }

}
