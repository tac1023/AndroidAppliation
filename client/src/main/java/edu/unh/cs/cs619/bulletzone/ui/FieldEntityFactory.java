package edu.unh.cs.cs619.bulletzone.ui;

import java.util.HashMap;

/**
 * Creates and returns the proper FieldEntity based on the
 * given ID and either index or coordinates.
 *
 * @author Tyler Currier
 * @version 1.1
 * @since 4/17/2018
 */

public class FieldEntityFactory {

    //Class Variables:
    //private HashMap<String, TankItem> tankMap;
    private int colNum = 16;
    private int rowNum = 16;
    private long playerID = -1;

    private static FieldEntityFactory _instance;
    //End Class Variables

    /**
     * Private constructor for this class. It takes the
     * ID of the players tank and stores it to differ between
     * the other tanks and the player's tank.
     *
     * @param pID ID of the player
     */
    private FieldEntityFactory(long pID)
    {
        //tankMap = new HashMap<>(100);
        playerID = pID;
    }

    //Public Methods:

    /**
     * If an instance of this class does not exist, then create one and return it.
     * If an instance does exist, return it.
     *
     * @return an instance of this class
     */
    public static FieldEntityFactory getInstance(long pID)
    {
        if(_instance == null)
            _instance = new FieldEntityFactory(pID);
        return _instance;
    }

    /**
     * Set the playerID variable to the player's tank's ID
     *
     * @param id Player's tanks' id
     */
    public void setPlayerID(long id)
    {
        playerID = id;
    }

    /**
     * Create a new FieldEntity and return it to the calling
     * function based on the ID from the server and the
     * current index
     *
     * @param id ID from the server
     * @param index current linear index
     * @return new FieldEntity
     */
    public FieldEntity makeEntity(Integer id, int index)
    {
        return makeEntity(id, index % colNum, index / rowNum);
    }

    /**
     * Create a new FieldEntity and return it to the calling
     * function based on the ID from the server and the
     * current x and y coordinates.
     *
     * @param id Id from the server
     * @param col current x-location
     * @param row current y-location
     * @return new FieldEntity
     */
    public FieldEntity makeEntity(Integer id, int col, int row)
    {
        FieldEntity ret;
        if(id == 0)
        {
            ret = new FieldEntity(id, playerID, col, row);
        }
        else if(((id >= 1000) && (id <= 2001)) || ((id >= 3000) && (id < 5000)) || (id == 1) || (id == 2) ||
                (id == 3) || (id == 4) || (id == 5) || ((id >= 20) && (id <= 26)))
        {
            ret = new FieldItem(id, playerID, col, row);
        }
        else if(((id >= 2000000) && (id < 3000000)) || ((id <= 100) && (id >= 92)) || ((id >=10000) && id < 20000))
        {
            ret = new BulletItem(id, playerID, col, row);
        }
        else if(((id >= 1000000) && (id < 2000000)) || ((id >= 10000000) && (id < 40000000)) ||
                ((id >= 3000000) && (id < 6000000)) || ((id >= 200) && (id < 300)))
        {
            ret = new TankItem(id, playerID, col, row);
        }
        else
        {
            ret = new FieldEntity(id, playerID, col, row);
        }
        return ret;
    }
    //End Public Methods
}
