package edu.unh.cs.cs619.bulletzone.repository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception when game is non-existant.
 * @author ???
 * @version 1.0
 * @since ???
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public final class GameDoesNotExistException extends Exception {

    /**
     * Print out the error statement associated with this exception
     *
     * @param gameId Game for which to print error
     */
    GameDoesNotExistException(int gameId) {
        super(String.format("Game with id '%s' does not exist", gameId));
    }

}
