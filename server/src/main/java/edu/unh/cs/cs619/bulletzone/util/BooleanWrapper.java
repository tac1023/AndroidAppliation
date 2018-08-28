package edu.unh.cs.cs619.bulletzone.util;

/**
 * Encapsulates data inside of this wrapper class so multiple variables
 * of different types can be returned as a "boolean".
 *
 * @author Simon
 * @version 1.0
 * @since 10/1/14
 */
public class BooleanWrapper {
    private boolean result;

    /**
     * Constructor that sets the result field
     *
     * @param result True of False result
     */
    public BooleanWrapper(boolean result) {
        this.result = result;
    }

    /**
     * Returns the result of this wrapper
     *
     * @return True or False
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Sets the result of this wrapper.
     *
     * @param result True or False
     */
    public void setResult(boolean result) {
        this.result = result;
    }
}
