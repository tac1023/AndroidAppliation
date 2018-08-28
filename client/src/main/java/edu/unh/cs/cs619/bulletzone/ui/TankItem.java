package edu.unh.cs.cs619.bulletzone.ui;

import android.util.Log;

import edu.unh.cs.cs619.bulletzone.R;

/**
 * TankItem extends the FieldItem class and is responsible for representing tanks
 * and soldiers. The class will be passed an array of resource IDs to be used
 * with it that can be chosen based on the direction that the object is currently
 * facing.
 *
 * @author Tyler Currier
 * @version 2.0
 * @since 4/17/2018
 */

public class TankItem extends FieldEntity {

    //Class Variables:
    private int tID;
    private int life = -1;
    private int direction = 0;
    //private boolean isPlayer = false;
    //End Class Variables
    //Final Variables:
    private final String UNKNOWN_DIRECTION = "Unknown Direction";
    //End Final Variables

    /**
     * Default Constructor
     */
    public TankItem()
    {
        super();
    }

    /**
     * Constructor for creating a tank item with a location and an array of
     * resource IDs.
     *
     * @param id  id of this object from the server
     * @param pID The ID of the Player's tank
     * @param col  x-position
     * @param row  y-position
     */
    public TankItem(Integer id, long pID, int col, int row)
    {

        super(id, pID, col, row);
        //Log.d( "Tank", "Tank created   " + pID);
        resources = new Integer[4];
        if(id / 10000000 == 1 || id / 10000000 == 2 || id / 10000000 == 3) { //if a tank, do this one
            tID = (id / 10000) % 1000;
            life = (id / 10) % 1000;
            setDirection(id % 10);
        }
        else if(id / 1000000 == 3 || id / 1000000 == 4 || id / 1000000 == 5) //Burning tank
        {
            tID = (id / 1000) % 1000;
            setDirection((id / 100) % 10);
        }
        else if(id / 1000000 == 1)//if a soldier, do this one
        {
            tID = (id / 1000) % 1000;
            life = (id / 10) % 100;
            setDirection(id % 10);
        }
        else //dead soldier
        {
            setDirection(0);
        }
        setType(id);

        setResourceID();
    }

    //Public Methods:

    /**
     * Return this objects tank ID
     *
     * @return int, the tank ID.
     */
    public int getTID()
    {
        return tID;
    }

    /**
     * Get the direction that the TankItem is currently facing
     *
     * @return int  The direction code.
     */
    public int getDirection()
    {
        return direction;
    }

    /**
     * Get the life of this tank or soldier
     *
     * @return life of this TankItem
     */
    public int getLife()
    {
        return life;
    }


    /**
     * Update the location and direction of this item.
     *
     * @param id  New ID from server.
     * @param col New x-position.
     * @param row New y-position.
     */
    public void update(Integer id, int col, int row)
    {
        if((id % 10) != direction)
        {
            setDirection(id % 10);
            setResourceID();
        }
        colNum = col;
        rowNum = row;
        life = (id / 10) % 1000;
    }
    //End Public Methods

    //Private Methods:
    /**
     * Choose the correct resource ID from the array of resource IDs based on
     * the direction that the tankItem is currently facing.
     */
    private void setResourceID()
    {
        try {
            switch (direction) {
                case 0: //UP
                    resourceID = resources[0];
                    break;
                case 2: //RIGHT
                    resourceID = resources[1];
                    break;
                case 4: //DOWN
                    resourceID = resources[2];
                    break;
                case 6: //LEFT
                    resourceID = resources[3];
                    break;

                default: //UNKNOWN
                    Log.d(UNKNOWN_DIRECTION, "Direction isn't defined");
                    break;
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            Log.d(eTAG, "Missing ID for TankItem");
            resourceID = R.drawable.error; //error resource for easy debugging
        }
    }

    /**
     * Set the proper resource IDs for this.
     *
     * @param id The ID of this cell from the server
     */
    private void setType(Integer id)
    {
        if((id >= 10000000) && (id < 20000000)) //Tank
        {
            setTank();
        }
        else if((id >= 20000000) && (id < 30000000)) //tunneler
        {
            setTunneler();
        }
        else if((id >= 30000000) && (id < 40000000)) //ship
        {
            setShip();
        }
        else if((id >= 3000000) && (id < 4000000)) //Burning tank
        {
            setBurningTank(id);
        }
        else if((id >= 4000000) && (id < 5000000)) //Burning tunneler
        {
            setBurningTunneler(id);
        }
        else if((id >= 5000000) && (id < 6000000)) //Burning ship
        {
            setBurningShip(id);
        }
        else if((id >= 1000000) && (id < 2000000)) //Soldier
        {
            setSoldier();
        }
        else if((id >= 200) && (id < 300)) //Dead soldier
        {
            setDeadSoldier(id);
        }
        else
        {
            resources[0] = R.drawable.error;
            resources[1] = resources[0];
            resources[2] = resources[0];
            resources[3] = resources[0];
            Log.d(UNKNOWN_ID, "Set type for unknown TankItem ID");
        }
    }

    /**
     * Set the resource images for Tank
     */
    private void setTank()
    {
        if(playerID == tID)
        {
            resources[0] = R.drawable.tank_player_up;
            resources[1] = R.drawable.tank_player_right;
            resources[2] = R.drawable.tank_player_down;
            resources[3] = R.drawable.tank_player_left;
        }
        else
        {
            resources[0] = R.drawable.tank_other_up;
            resources[1] = R.drawable.tank_other_right;
            resources[2] = R.drawable.tank_other_down;
            resources[3] = R.drawable.tank_other_left;
        }
    }

    /**
     * Set the resource images for Tunneler
     */
    private void setTunneler()
    {
        if(playerID == tID)
        {
            resources[0] = R.drawable.tunneler_player_up;
            resources[1] = R.drawable.tunneler_player_right;
            resources[2] = R.drawable.tunneler_player_down;
            resources[3] = R.drawable.tunneler_player_left;
        }
        else
        {
            resources[0] = R.drawable.tunneler_other_up;
            resources[1] = R.drawable.tunneler_other_right;
            resources[2] = R.drawable.tunneler_other_down;
            resources[3] = R.drawable.tunneler_other_left;
        }
    }

    /**
     * Set the resource images for Ship
     */
    private void setShip()
    {
        if(playerID == tID)
        {
            resources[0] = R.drawable.ship_player_up;
            resources[1] = R.drawable.ship_player_right;
            resources[2] = R.drawable.ship_player_down;
            resources[3] = R.drawable.ship_player_left;
        }
        else
        {
            resources[0] = R.drawable.ship_other_up;
            resources[1] = R.drawable.ship_other_right;
            resources[2] = R.drawable.ship_other_down;
            resources[3] = R.drawable.ship_other_left;
        }
    }

    /**
     * Set teh resource images for Burning Tank
     *
     * @param id ID from server
     */
    private void setBurningTank(int id)
    {
        if(playerID == tID)
        {
            if(id % 2 == 0)
            {
                resources[0] = R.drawable.tank_player_up_fire1;
                resources[1] = R.drawable.tank_player_right_fire1;
                resources[2] = R.drawable.tank_player_down_fire1;
                resources[3] = R.drawable.tank_player_left_fire1;
            }
            else
            {
                resources[0] = R.drawable.tank_player_up_fire2;
                resources[1] = R.drawable.tank_player_right_fire2;
                resources[2] = R.drawable.tank_player_down_fire2;
                resources[3] = R.drawable.tank_player_left_fire2;
            }
        }
        else
        {
            if(id % 2 == 0)
            {
                resources[0] = R.drawable.tank_other_up_fire1;
                resources[1] = R.drawable.tank_other_right_fire1;
                resources[2] = R.drawable.tank_other_down_fire1;
                resources[3] = R.drawable.tank_other_left_fire1;
            }
            else
            {
                resources[0] = R.drawable.tank_other_up_fire2;
                resources[1] = R.drawable.tank_other_right_fire2;
                resources[2] = R.drawable.tank_other_down_fire2;
                resources[3] = R.drawable.tank_other_left_fire2;
            }
        }
    }

    /**
     * Set resource images for Burning Tunneler
     *
     * @param id ID from server
     */
    private void setBurningTunneler(int id)
    {
        if(playerID == tID)
        {
            if(id % 2 == 0)
            {
                resources[0] = R.drawable.tunneler_player_up_fire1;
                resources[1] = R.drawable.tunneler_player_right_fire1;
                resources[2] = R.drawable.tunneler_player_down_fire1;
                resources[3] = R.drawable.tunneler_player_left_fire1;
            }
            else
            {
                resources[0] = R.drawable.tunneler_player_up_fire2;
                resources[1] = R.drawable.tunneler_player_right_fire2;
                resources[2] = R.drawable.tunneler_player_down_fire2;
                resources[3] = R.drawable.tunneler_player_left_fire2;
            }
        }
        else
        {
            if(id % 2 == 0)
            {
                resources[0] = R.drawable.tunneler_other_up_fire1;
                resources[1] = R.drawable.tunneler_other_right_fire1;
                resources[2] = R.drawable.tunneler_other_down_fire1;
                resources[3] = R.drawable.tunneler_other_left_fire1;
            }
            else
            {
                resources[0] = R.drawable.tunneler_other_up_fire2;
                resources[1] = R.drawable.tunneler_other_right_fire2;
                resources[2] = R.drawable.tunneler_other_down_fire2;
                resources[3] = R.drawable.tunneler_other_left_fire2;
            }
        }
    }

    /**
     * Set resource images for Burning Ship
     *
     * @param id ID from server
     */
    private void setBurningShip(int id)
    {
        if(playerID == tID)
        {
            if(id % 2 == 0)
            {
                resources[0] = R.drawable.ship_player_up_fire1;
                resources[1] = R.drawable.ship_player_right_fire1;
                resources[2] = R.drawable.ship_player_down_fire1;
                resources[3] = R.drawable.ship_player_left_fire1;
            }
            else
            {
                resources[0] = R.drawable.ship_player_up_fire2;
                resources[1] = R.drawable.ship_player_right_fire2;
                resources[2] = R.drawable.ship_player_down_fire2;
                resources[3] = R.drawable.ship_player_left_fire2;
            }
        }
        else
        {
            if(id % 2 == 0)
            {
                resources[0] = R.drawable.ship_other_up_fire1;
                resources[1] = R.drawable.ship_other_right_fire1;
                resources[2] = R.drawable.ship_other_down_fire1;
                resources[3] = R.drawable.ship_other_left_fire1;
            }
            else
            {
                resources[0] = R.drawable.ship_other_up_fire2;
                resources[1] = R.drawable.ship_other_right_fire2;
                resources[2] = R.drawable.ship_other_down_fire2;
                resources[3] = R.drawable.ship_other_left_fire2;
            }
        }
    }

    /**
     * Set resource images for Soldier
     */
    private void setSoldier()
    {
        if(playerID == tID)
        {
            resources[0] = R.drawable.soldier_player_up;
            resources[1] = R.drawable.soldier_player_right;
            resources[2] = R.drawable.soldier_player_down;
            resources[3] = R.drawable.soldier_player_left;
        }
        else
        {
            resources[0] = R.drawable.soldier_up;
            resources[1] = R.drawable.soldier_right;
            resources[2] = R.drawable.soldier_down;
            resources[3] = R.drawable.soldier_left;
        }
    }

    /**
     * Set resource image for Dead Soldier
     *
     * @param id ID from server
     */
    private void setDeadSoldier(int id)
    {
        if(id > 220)
            resources[0] = R.drawable.soldier_dead;
        else if(id > 218)
            resources[0] = R.drawable.soldier_dead_fade1;
        else if(id > 216)
            resources[0] = R.drawable.soldier_dead_fade2;
        else if(id > 214)
            resources[0] = R.drawable.soldier_dead_fade3;
        else if(id > 212)
            resources[0] = R.drawable.soldier_dead_fade4;
        else if(id > 210)
            resources[0] = R.drawable.soldier_dead_fade5;
        else if(id > 208)
            resources[0] = R.drawable.soldier_dead_fade6;
        else if(id > 206)
            resources[0] = R.drawable.soldier_dead_fade7;
        else if(id > 204)
            resources[0] = R.drawable.soldier_dead_fade8;
        else if(id > 202)
            resources[0] = R.drawable.soldier_dead_fade9;
        else if(id > 199)
            resources[0] = R.drawable.soldier_dead_fade10;
        else
            resources[0] = R.drawable.error;
    }

    /**
     * Set a new direction for the TankItem.
     *
     * @param d  The new direction code.
     */
    private void setDirection(int d)
    {
        direction = d;
    }
    //End Private Methods

}
