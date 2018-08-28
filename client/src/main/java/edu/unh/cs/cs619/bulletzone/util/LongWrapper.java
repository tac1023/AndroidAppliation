package edu.unh.cs.cs619.bulletzone.util;

/**
 * Encapsulates data inside of this wrapper class so multiple variables
 * of different types can be returned as a "long".
 *
 * @author Simon
 * @version 1.0
 * @since 10/1/14
 */
public class LongWrapper {
    private long result;

    /**
     * Default Constructor
     */
    public LongWrapper() {

    }

    /**
     * Constructor that sets the result field of this wrapper
     *
     * @param result The long result to give this wrapper
     */
    public LongWrapper(long result) {
        this.result = result;
    }

    /**
     * Getter for the result field of this wrapper
     *
     * @return The result field of this wrapper
     */
    public long getResult() {
        return result;
    }

    /**
     * Setter for the result field of this wrapper
     *
     * @param result The result to give to this wrapper
     */
    public void setResult(long result) {
        this.result = result;
    }
}
