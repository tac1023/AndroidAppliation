package edu.unh.cs.cs619.bulletzone.model;

/**
 * The wall.
 * @author ???
 * @since ???
 * @version ???
 */
public class Wall extends FieldEntity {
    private int destructValue;
    private boolean destructible = false;
    private boolean canEnter = false;

    /**
     * Wall Constructor.
     */
    public Wall(){
        this.destructValue = 1000;
    }

    /**
     * Wall Constructor.
     *
     * @param destructValue value of object
     * @param pos position of object
     */
    public Wall(int destructValue, int pos){
        this.destructValue = destructValue;
        this.pos = pos;
        destructible = !(destructValue == 1000);
    }

    /**
     * Creates new Wall
     *
     * @return new wall
     */
    @Override
    public FieldEntity copy() {
        return new Wall();
    }

    /**
     * Gets the life of wall.
     *
     * @return wall life
     */
    @Override
    public int getIntValue() {
        return destructValue;
    }

    /**
     * Returns the string value.
     *
     * @return string
     */
    @Override
    public String toString() {
        if(destructible)
            return "DW";
        return "IW";
    }

    /**
     * A Bullet hits this wall
     *
     * @param damage damage to deal
     */
    @Override
    public void hit(int damage)
    {
        if(destructible)
        {
            destructValue = destructValue - damage;
            if(destructValue < 1001) {
                destructValue = 2001;
                destructible = false;
                canEnter = true;
            }
        }
    }

    /**
     * The current position in grid.
     *
     * @return position
     */
    public int getPos(){
        return pos;
    }

    /**
     * Is this wall destructible?
     *
     * @return true or false
     */
    public boolean isDestructible()
    {
        return destructible;
    }

    /**
     * Return whether a tankItem can enter this space
     *
     * @return boolean
     */
    public boolean canEnter()
    {
        return canEnter;
    }
}
