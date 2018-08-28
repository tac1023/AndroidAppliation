package edu.unh.cs.cs619.bulletzone.model;

/**
 * The a child of Field Entity.
 * @author Bence Cserna (bence@cserna.net)
 * @version 1.0
 * @since ???
 */

public class NumberField extends FieldEntity {

    private static final String TAG = "NumberField";
    private final int value;

    /**
     * Constructor.
     *
     * @param value new number for this field
     */
    public NumberField(int value) {
        this.value = value;
    }

    /**
     * Returns 0.
     *
     * @return 0
     */
    @Override
    public int getIntValue() {
        return 0;
    }

    /**
     * Does nothing.
     *
     * @return null
     */
    @Override
    public FieldEntity copy() {
        return null;
    }

    /**
     * Turns integer to string.
     *
     * @return string
     */
    @Override
    public String toString() {
        return Integer.toString(value == 1000 ? 1 : 2);
    }

}
