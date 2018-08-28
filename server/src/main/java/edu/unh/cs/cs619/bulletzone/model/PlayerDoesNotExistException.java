package edu.unh.cs.cs619.bulletzone.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for invalid player
 * @author ???
 * @version 1.0
 * @since ???
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public final class PlayerDoesNotExistException extends Exception {

    /**
     * The exception itself.
     *
     * @param name name of player
     */
    PlayerDoesNotExistException(String name) {
        super(String.format("Player with name '%s' does not exist", name));
    }

}
