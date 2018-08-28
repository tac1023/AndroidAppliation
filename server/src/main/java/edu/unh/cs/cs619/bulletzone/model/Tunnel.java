package edu.unh.cs.cs619.bulletzone.model;

/**
 * Server side representation of a tunnel
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 4/27/2018
 */

public class Tunnel extends FieldEntity {
    int value;

    /**
     * Default Constructor
     */
    public Tunnel()
    {
        value = 5;
    }

    /**
     * Return Integer Value
     *
     * @return int
     */
    @Override
    public int getIntValue()
    {
        return value;
    }

    /**
     * Return a new tunnel
     *
     * @return FieldEntity
     */
    @Override
    public FieldEntity copy()
    {
        return new Tunnel();
    }
}
