package edu.unh.cs.cs619.bulletzone.util;

import java.io.Serializable;

/**
 * Encapsulates data inside of this wrapper class so multiple variables
 * of different types can be returned as a "result" of type "T".
 *
 * @author Simon
 * @version 1.0
 * @since 10/1/14
 */
public class ResultWrapper<T> implements Serializable {
    private T result;

    /**
     * Default Constructor
     */
    public ResultWrapper() {
    }

    /**
     * Constructor that sets the result field of this wrapper
     *
     * @param result the result of type "T" to give this wrapper
     */
    public ResultWrapper(T result) {
        this.result = result;
    }

    /**
     * Getter for the result field of this wrapper
     *
     * @return The result field of this wrapper
     */
    public T getResult() {
        return this.result;
    }

    /**
     * Setter for the resulf field of this wrapper
     *
     * @param result The result of type "T" to give to this wrapper
     */
    public void setResult(T result) {
        this.result = result;
    }
}
