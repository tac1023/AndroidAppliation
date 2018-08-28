package edu.unh.cs.cs619.bulletzone.model;

/**
 * This class represents Rocks, Dirt blocks, open sub-terrain spaces, and open
 * sub-terrain spaces under Tunnels
 *
 * @author Tyler Currier
 * @version 2.0
 * @since 5/2/2018
 */
public class RockAndDirt extends FieldEntity {
    private boolean isOpen = false;
    private boolean destructible;
    private int destructValue;

    /**
     * Default Constructor
     */
    public RockAndDirt()
    {
        this.destructValue = 4000;
    }

    /**
     * This Constructor makes a Rock, Dirt block, or open space
     * depending upon the destructValue
     *
     * @param destructValue value of object ot make
     */
    public RockAndDirt(int destructValue)
    {
        this.destructValue = destructValue;
        destructible = (destructValue > 4000);
        isOpen = ((destructValue == 3) || (destructValue == 4));
    }

    /**
     * Return the current Integer value of this object
     *
     * @return int
     */
    @Override
    public int getIntValue()
    {
        if(isOpen && (parent != null))
        {
            if(parent.getNeighbor(Direction.Above).isPresent()
                    && parent.getNeighbor(Direction.Above).getEntity() instanceof Tunnel)
                destructValue = 4;
            else
                destructValue = 3;
        }
        return destructValue;
    }

    /**
     * Return a rock
     *
     * @return FieldEntity
     */
    @Override
    public FieldEntity copy()
    {
        return new RockAndDirt();
    }

    /**
     * Returns the String representation of this Object
     *
     * @return String
     */
    @Override
    public String toString()
    {
        if(isOpen())
            return "OS";
        if(destructible)
            return "D";
        return "R";
    }

    /**
     * Return whether this object is an open space
     *
     * @return boolean
     */
    public boolean isOpen()
    {
        return isOpen;
    }

    /**
     * Turn this object into an open space
     */
    public void clearDirt()
    {
        isOpen = true;
        destructible = false;
    }

    /**
     * Is this object destructible?
     *
     * @return true or false
     */
    public boolean isDestructible()
    {
        return destructible;
    }
}
