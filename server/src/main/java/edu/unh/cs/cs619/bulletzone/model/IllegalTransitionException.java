package edu.unh.cs.cs619.bulletzone.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a transition is invalid.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public final class IllegalTransitionException extends Exception {

    /**
     * The exception itself. Prints error msg.
     *
     * @param gameId game throwing exception
     * @param tankId tank committing illegal transition
     * @param from tank's current direction
     * @param to tank's desired direction
     */
    IllegalTransitionException(Long gameId, Long tankId, Direction from, Direction to) {
        super(String.format("It is illegal to turn tank '%d' in game '%d' from '%s' to '%s'",
                tankId, gameId, from, to));
    }

}
