package edu.unh.cs.cs619.bulletzone.ui;

import android.util.Log;

import edu.unh.cs.cs619.bulletzone.R;

/**
 * This class represents a Bullet on the screen.
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 3/28/2018
 */

public class BulletItem extends FieldEntity {

    //Class Variables:
    private long tID = -1;
    private int bulletNum = -1;
    private int damage = -1;
    private byte direction = 0;
    //End Class Variables

    /**
     * Default Constructor
     */
    public BulletItem()
    {
        super();
    }

    /**
     * Constructor that creates a usable bulletItem.
     *
     * @param id The ID from the server
     * @param pID The ID of the Player's tank
     * @param col The x-location
     * @param row The y-location
     */
    public BulletItem(Integer id, long pID, int col, int row)
    {
        super(id, pID, col, row);
        resources = new Integer[4];
        if(id / 10000 == 1)
        {
            tID = ((id / 10) % 1000);
            setType(id);
            damage = 50;
            direction = (byte)(id % 10);
            setResourceID();
        }
        else if(id / 1000000 == 2){
            tID = ((id / 1000) % 1000);
            setType(id);
            damage = (id % 1000);
            direction = (byte)(id % 10);
            setResourceID();
        }
        else
        {
            tID = -1;
            setType(id);
            damage = -1;
            direction = 0;
            setResourceID();
        }
    }

    //Public Methods:

    /**
     * Update important information about the bullet
     *
     * @param id id from server, not important
     * @param col x-location
     * @param row y-location
     */
    public void update(Integer id, int col, int row)
    {
        colNum = col;
        rowNum = row;
    }
    //End Public Methods

    //Private Methods:

    /**
     * Set the current image for this object
     */
    private void setResourceID()
    {
        try
        {
            switch (direction)
            {
                case 0:
                    resourceID = resources[0];
                    break;
                case 2:
                    resourceID = resources[1];
                    break;
                case 4:
                    resourceID = resources[2];
                    break;
                case 6:
                    resourceID = resources[3];
                    break;

                default:
                    resourceID = R.drawable.error;
                    break;
            }

        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            Log.d(eTAG, "Missing ID for FieldItem");
            resourceID = R.drawable.error; //error resource for easy debugging
        }
    }

    /**
     * Set the available images for this object.
     *
     * @param id The ID from the server
     */
    private void setType(Integer id)
    {
        if((id >= 2000000) && (id < 3000000))
        {
            resources[0] = R.drawable.bullet;
            resources[1] = R.drawable.bullet;
            resources[2] = R.drawable.bullet;
            resources[3] = R.drawable.bullet;
        }
        else if ((id >= 10000) && (id < 20000))
        {
            resources[0] = R.drawable.bulletup;
            resources[1] = R.drawable.bulletright;
            resources[2] = R.drawable.bulletdown;
            resources[3] = R.drawable.bulletleft;
        }
        else if(id == 100)
            resources[0] = R.drawable.explosion1;
        else if(id == 99)
            resources[0] = R.drawable.explosion2;
        else if(id == 98)
            resources[0] = R.drawable.explosion3;
        else if(id == 97)
            resources[0] = R.drawable.explosion4;
        else if(id == 96)
            resources[0] = R.drawable.explosion5;
        else if(id == 95)
            resources[0] = R.drawable.explosion6;
        else if(id == 94)
            resources[0] = R.drawable.explosion7;
        else if(id == 93)
            resources[0] = R.drawable.explosion8;
        else
        {
            resources[0] = R.drawable.error;
            Log.d(UNKNOWN_ID, "Set type for unknown FieldItem ID");
        }
    }
    //End Private Methods
}
