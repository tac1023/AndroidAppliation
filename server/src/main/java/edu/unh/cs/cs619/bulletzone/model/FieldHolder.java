package edu.unh.cs.cs619.bulletzone.model;

import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The Holder of the field.
 * @author Simon, Tyler Currier
 * @version 2.0
 * @since 4/17/18
 */
public class FieldHolder {

    private final Map<Direction, FieldHolder> neighbors = new HashMap<Direction, FieldHolder>();
    private Optional<FieldEntity> entityHolder = Optional.absent();
    private Optional<FieldEntity> tankHolder = Optional.absent();

    /**
     * Adds neighbor.
     *
     * @param direction direction in which to add neighbor
     * @param fieldHolder field holder that is new neighbor
     */
    public void addNeighbor(Direction direction, FieldHolder fieldHolder) {
        neighbors.put(checkNotNull(direction), checkNotNull(fieldHolder));
    }

    /**
     * Gets the desired neighbor.
     *
     * @param direction direction in which to get neighbor
     * @return field holder
     */
    public FieldHolder getNeighbor(Direction direction) {
        return neighbors.get(checkNotNull(direction,
                "Direction cannot be null."));
    }

    /**
     * Checks to see if a map item is there.
     *
     * @return boolean
     */
    public boolean isPresent() {
        return entityHolder.isPresent();
    }

    /**
     * checks to see if a player item is there.
     *
     * @return boolean
     */
    public boolean isTankPresent()
    {
        return tankHolder.isPresent();
    }

    /**
     * Gets the desired map item field entity
     *
     * @return Field entity
     */
    public FieldEntity getEntity() {
        return entityHolder.get();
    }

    /**
     * get the desired tank item field entity
     *
     * @return FieldEntity
     */
    public FieldEntity getTankItem()
    {
        return tankHolder.get();
    }

    /**
     * Sets the map item field entity.
     *
     * @param entity map item
     */
    public void setFieldEntity(FieldEntity entity) {
        entityHolder = Optional.of(checkNotNull(entity,
                "FieldEntity cannot be null."));
    }

    /**
     * Sets the player item field entity
     *
     * @param entity player item
     */
    public void setTankEntity(FieldEntity entity)
    {
        tankHolder = Optional.of(checkNotNull(entity, "TankEntity cannot be null."));
    }

    /**
     * Clears the map item field.
     */
    public void clearField() {
        if (entityHolder.isPresent()) {
            entityHolder = Optional.absent();
        }

    }

    /**
     * Clears the player item field
     */
    public void clearTankItem()
    {
        if(tankHolder.isPresent())
        {
            tankHolder = Optional.absent();
        }
    }

}
