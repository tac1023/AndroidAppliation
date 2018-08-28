package edu.unh.cs.cs619.bulletzone.model;

/**
 * The field entity object as abstract.
 * @author ???
 * @version ???
 * @since ???
 */
public abstract class FieldEntity {

    FieldHolder parent;
    int pos;

    /**
     * Serializes the current {@link edu.unh.cs.cs619.bulletzone.model.FieldEntity} instance.
     *
     * @return Integer representation of the current {@link edu.unh.cs.cs619.bulletzone.model.FieldEntity}
     */
    public abstract int getIntValue();

    /**
     * Gets the parent of holder
     *
     * @return field holder
     */
    public FieldHolder getParent() {
        return parent;
    }

    /**
     * Sets a parent.
     *
     * @param parent Parent of FieldEntity
     */
    public void setParent(FieldHolder parent) {
        this.parent = parent;
    }

    /**
     * Copies the Entity
     *
     * @return new entity
     */
    public abstract FieldEntity copy();

    /**
     * Registers a hit on an object.
     *
     * @param damage damage to deal
     */
    public void hit(int damage) {
    }

    /**
     * Get the position of this field entity
     *
     * @return int
     */
    public int getPos()
    {
        return pos;
    }
}
