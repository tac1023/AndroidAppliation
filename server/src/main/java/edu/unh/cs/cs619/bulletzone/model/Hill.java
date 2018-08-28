package edu.unh.cs.cs619.bulletzone.model;

/**
 * Represents a Hill
 *
 * @author Tyler Currier
 * @version 1.1
 * @since 5/2/2018
 */

public class Hill extends FieldEntity {
    int value;

    /**
     * Default Constructor
     */
    public Hill()
    {
        this.value = 2000;
    }

    /**
     * Copy Constructor, returns new basic Hill
     *
     * @return new Hill
     */
    @Override
    public FieldEntity copy()
    {
        return new Hill();
    }

    /**
     * Return the integer representation of this Hill.
     *
     * @return ID of this Hill
     */
    @Override
    public int getIntValue()
    {
        return value;
    }

    /**
     * Return the string representation of this Hills (as
     * opposed to pictographic representation
     *
     * @return String
     */
    @Override
    public String toString()
    {
        return "H";
    }

    /**
     * Return the position of this Hill
     *
     * @return position
     */
    public int getPos()
    {
        return pos;
    }
}
