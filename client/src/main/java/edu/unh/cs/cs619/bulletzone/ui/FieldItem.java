package edu.unh.cs.cs619.bulletzone.ui;

import android.util.Log;

import edu.unh.cs.cs619.bulletzone.R;

import static edu.unh.cs.cs619.bulletzone.R.drawable.wall;

/**
 * This Class represents Field Items, including Walls.
 *
 * @author Tyler Currier
 * @version 1.1
 * @since 4/17/2018
 */

public class FieldItem extends FieldEntity{

    //Class Variables:
    private int life = -1;
    private boolean destructible;
    //End Class Variables
    //Final Variables:

    //End Final Variables

    /**
     * Default Constructor
     */
    public FieldItem()
    {
        super();
    }

    /**
     * Constructor that creates a FieldItem at a given location with the passed
     * in image resource(s).
     *
     * @param id  id of this item from server
     * @param pID The ID of the Player's tank
     * @param col  x-location
     * @param row  y-location
     */
    public FieldItem(Integer id, long pID, int col, int row)
    {
        super(id, pID, col, row);
        resources = new Integer[1];
        setType(id);
        setResourceID();
    }

    //Public Methods:
    /*public boolean getDestructable()
    {
        return destructible;
    }*/

    /**
     * Update the Cell with new information
     *
     * @param id ID of FieldEntity
     * @param col x-location
     * @param row y-location
     */
    public void update(Integer id, int col, int row)
    {
        if(destructible)
            life = id - 1000;
        colNum = col;
        rowNum = row;
    }
    //End Public Methods

    //Private Methods:

    /**
     * Set the correct resource ID from the array of resourceIDs.
     */
    private void setResourceID()
    {
        try
        {
            resourceID = resources[0];
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            Log.d(eTAG, "Missing ID for FieldItem");
            resourceID = R.drawable.error; //error resource for easy debugging
        }
    }

    /**
     * Sets the life of the object and the proper resource ID(s)
     *
     * @param id  The ID received from the server.
     */
    private void setType(Integer id)
    {
        if(id == 1)
        {
            resources[0] = R.drawable.coast;
            destructible = false;
        }
        else if(id == 2)
        {
            resources[0] = R.drawable.water;
            destructible = false;
        }
        else if(id == 3)
        {
            resources[0] = R.drawable.open_sub;
            destructible = false;
        }
        else if(id == 4)
        {
            resources[0] = R.drawable.open_sub_below_tunnel;
            destructible = false;
        }
        else if(id == 5)
        {
            resources[0] = R.drawable.hole;
            destructible = false;
        }
        else if(id == 20)
        {
            resources[0] = R.drawable.water_current_up;
            destructible = false;
        }
        else if(id == 22)
        {
            resources[0] = R.drawable.water_current_right;
            destructible = false;
        }
        else if(id == 24)
        {
            resources[0] = R.drawable.water_current_down;
            destructible = false;
        }
        else if(id == 26)
        {
            resources[0] = R.drawable.water_current_left;
            destructible = false;
        }
        else if(id == 1000)
        {
            resources[0] = R.drawable.immortal_wall;
            life = 1117;
            destructible = false;
        }
        else if((id > 1000) && (id < 2000))
        {
            resources[0] = R.drawable.wall;
            life = id - 1000;
            destructible = true;
        }
        else if(id == 2000)
        {
            resources[0] = R.drawable.hill;
            destructible = false;
        }
        else if(id == 2001)
        {
            resources[0] = R.drawable.wall_destroyed;
            destructible = false;
        }
        else if(id == 3000)
        {
            resources[0] = R.drawable.debris_field;
            destructible = false;
        }
        else if((id > 3000) && (id < 4000))
        {
            resources[0] = R.drawable.forest;
            life = id - 3000;
            destructible = true;
        }
        else if(id == 4000)
        {
            resources[0] = R.drawable.rock;
            destructible = false;
        }
        else if((id > 4000) && (id < 5000))
        {
            resources[0] = R.drawable.dirt;
            life = id - 4000;
            destructible = true;
        }
        else
        {
            resources[0] = R.drawable.error;
            Log.d(UNKNOWN_ID, "Set type for unknown FieldItem ID");
            destructible = false;
        }
    }
    //End Private Methods
}
