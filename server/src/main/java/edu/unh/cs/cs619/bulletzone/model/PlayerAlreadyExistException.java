package edu.unh.cs.cs619.bulletzone.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when duplicate player exists.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public final class PlayerAlreadyExistException extends Exception {

    /**
     * The exception itself.
     *
     * @param name name of player
     */
    PlayerAlreadyExistException(String name) {
        super(String.format("Player with name '%s' already exist", name));
    }

}
