package edu.unh.cs.cs619.bulletzone.ui;

import android.util.Log;

import edu.unh.cs.cs619.bulletzone.R;

/**
 * This class by itself represents a Blank FieldEntity. The other
 * FieldEntity related classes extend this one.
 *
 * @author Tyler Currier
 * @version 1.1
 * @since 4/17/2018
 */

public class FieldEntity {

    //Class Variables:
    protected Integer resourceID = 0;
    protected int rowNum = 0;
    protected int colNum = 0;
    protected Integer[] resources; //UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3
    protected long playerID = -1;
    //End Class Variables
    //Final Variables
    protected final String eTAG = "No ID";
    protected final String UNKNOWN_ID = "Unknown ID";
    //End Final Variables

    /**
     * Default Constructor just produces a blank FieldEntity at
     * location 0 x 0.
     */
    public FieldEntity()
    {
        resourceID = R.drawable.blank;
    }

    /**
     * Constructor that makes a FieldEntity based on given data.
     *
     * @param id  The id of this cell from the server.
     * @param pID The id of the Player's tank.
     * @param col  The x-position of this cell.
     * @param row  The y-position of this cell.
     */
    public FieldEntity(Integer id, long pID, int col, int row)
    {
        resources = new Integer[1];
        colNum = col;
        rowNum = row;
        playerID = pID;
        setType(id);
        setResourceID();
    }

    //Public Methods:

    /**
     * Get the Picture used by this item
     *
     * @return the id of the picture
     */
    public Integer getResourceID(){return resourceID;}

    /**
     * Update a cell (not used now)
     *
     * @param id  The id of this cell from the server
     * @param col  The x-location
     * @param row  The y-location
     */
    public void update(Integer id, int col, int row)
    {

    }

    /**
     * Get the life of a field item that has life. Field items that have a life value
     * will override this method. Those that don't will use this version that just
     * returns 0.
     *
     * @return Life of a FieldItem if applicable
     */
    public int getLife()
    {
        return 0;
    }

    /**
     * Get the direction of a field item that has direction. Field items that have
     * a direction value will override this method. Those that don'w will use this
     * version that just returns 0.
     *
     * @return Direction of a FieldItem if applicable
     */
    public int getDirection()
    {
        return 0;
    }

    //End Public Methods

    //Private Methods:

    /**
     * Set the resource ID from the available IDs
     */
    private void setResourceID()
    {
        try
        {
            resourceID = resources[0];
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            Log.d(eTAG, "Missing ID for FieldEntity");
            resourceID = R.drawable.error; //error resource for easy debugging
        }
    }

    /**
     * Set the type using the IDs and provide a set of resource IDs for this
     * object to use.
     *
     * @param id The resource ID from the server
     */
    private void setType(Integer id)
    {
        if(id == 0)
        {
            resources = new Integer[1];
            resources[0] = R.drawable.blank;
        }
        else
        {
            resources = new Integer[1];
            resources[0] = R.drawable.error;
        }
    }
    //End Private Methods
}
