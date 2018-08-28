package edu.unh.cs.cs619.bulletzone.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a given limit is exceeded.
 * @author ???
 * @version 1.0
 * @since ???
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public final class LimitExceededException extends Exception {
    /**
     * The exception itself.
     *
     * @param msg message to pring
     */
    LimitExceededException(String msg) {
        super(msg);
    }
}
