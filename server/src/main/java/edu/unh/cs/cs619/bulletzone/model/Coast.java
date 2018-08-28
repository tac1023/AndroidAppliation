package edu.unh.cs.cs619.bulletzone.model;

/**
 * Server side representation of the Coast land type
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 4/27/2018
 */
public class Coast extends FieldEntity {
    int value;

    /**
     * Default Constructor
     */
    public Coast()
    {
        value = 1;
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
        return new Coast();
    }

    /**
     * Return string value of this object
     *
     * @return String
     */
    @Override
    public String toString()
    {
        return "C";
    }
}
