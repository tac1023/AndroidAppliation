package edu.unh.cs.cs619.bulletzone.ui;

import java.lang.reflect.Field;

/**
 * This class is responsible for holding a collection of objects to
 * represent the battlefield. It will have a 16 by 16 array to hold
 * Field Entity objects, which will then be given to the Grid Adapter
 * for rendering.
 *
 * @author Tyler Currier
 * @version 1.2
 * @since 4/14/2018
 */

public class Battlefield {

    //Class Variables:
    private FieldEntity[][] battlefield;
    private FieldEntity[][] tankfield;
    private int numRows = 16;
    private int numCols = 16;
    private int size = 256;
    //End Class Variables

    /**
     * Constructor for Battlefield
     */
    public Battlefield()
    {
        battlefield = new FieldEntity[numCols][numRows];
        tankfield = new FieldEntity[numCols][numRows];
    }

    //Public Methods:
    /**
     * This method returns a single FieldEntity from the Battlefield.
     * This entity will represent a map object such as a wall, hill,
     * or forest.
     *
     * @param index  A linear index by which to find a FieldEntity
     * @return FieldEntity  Returns the FieldEntity found at the index.
     */
    public FieldEntity getFieldEntity(int index)
    {
        return getFieldEntity(index / numRows, index % numCols);
    }

    /**
     * This method returns a single FieldEntity from the Battlefield.
     * This entity will represent a player object such as a tank, bullet,
     * or soldier.
     *
     * @param index  A linear index by which to find a FieldEntity
     * @return FieldEntity  Returns the FieldEntity found at the index.
     */
    public FieldEntity getTankEntity(int index)
    {
        return getTankEntity(index / numRows, index % numCols);
    }


    /**
     * This method returns a single FieldEntity from the Battlefield.
     * This entity will represent a map object such as a wall, hill,
     * or forest.
     *
     * @param col  A x-coordinate to find a FieldEntity.
     * @param row  A y-coordinate to find a FieldEntity.
     * @return FieldEntity  Returns the FieldEntity found at the coordinates.
     */
    public FieldEntity getFieldEntity(int col, int row)
    {
        return battlefield[row][col]; //written in (y, x) format
    }

    /**
     * This method returns a single FieldEntity from the Battlefield.
     * This entity will represent a player object such as a tank, bullet,
     * or soldier.
     *
     * @param col  A x-coordinate to find a FieldEntity.
     * @param row  A y-coordinate to find a FieldEntity.
     * @return FieldEntity  Returns the FieldEntity found at the coordinates.
     */
    public FieldEntity getTankEntity(int col, int row)
    {
        return tankfield[row][col];
    }

    /**
     * This method sets a single FieldEntity in the battlefield if it is not a player
     * item such as a tank, bullet, or soldier.
     *
     * @param index  A linear index by which to find a FieldEntity.
     * @param fe  A FieldEntity to place in the Battlefield.
     */
    public void setFieldEntity(int index, FieldEntity fe)
    {
        setFieldEntity(index / numRows, index % numCols, fe);
    }

    /**
     * This method sets a single FieldEntity in the battlefield if it is a player
     * item such as a tank, bullet, or soldier.
     *
     * @param index  A linear index by which to find a FieldEntity.
     * @param fe  A FieldEntity to place in the Battlefield.
     */
    public void setTankEntity(int index, FieldEntity fe)
    {
        setTankEntity(index / numRows, index % numCols, fe);
    }


    /**
     * This method sets a single FieldEntity in the battlefield if it is not a player
     * item such as a tank, bullet, or soldier
     *
     * @param col  A x-coordinate at which to place the FieldEntity.
     * @param row  A y-coordinate at which to place the FieldEntity.
     * @param fe  A FieldEntity to place in the Battlefield.
     */
    public void setFieldEntity(int col, int row, FieldEntity fe)
    {
        battlefield[row][col] = fe;
    }


    /**
     * This method sets a single FieldEntity in the battlefield if it is a player
     * item such as a tank, bullet, or soldier
     *
     * @param col  A x-coordinate at which to place the FieldEntity.
     * @param row  A y-coordinate at which to place the FieldEntity.
     * @param fe  A FieldEntity to place in the Battlefield.
     */
    public void setTankEntity(int col, int row, FieldEntity fe)
    {
        tankfield[row][col] = fe;
    }

    /**
     * Get the size of this battlefield; how many objects
     *
     * @return int, the size
     */
    public int getSize()
    {
        return size;
    }
    //End Public Methods

    //Private Methods:

    //End Private Methods
}
