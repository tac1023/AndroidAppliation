package edu.unh.cs.cs619.bulletzone.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when invalid tank id is called.
 * @author ???
 * @version 1.0
 * @since ???
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public final class TankDoesNotExistException extends Exception {
    /**
     * The exception itself.
     *
     * @param tankId ID of tank
     */
    public TankDoesNotExistException(Long tankId) {
        super(String.format("Tank '%d' does not exist", tankId));
    }
}
