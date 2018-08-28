package edu.unh.cs.cs619.bulletzone.control;

/**
 * Event that notifies that the activity needs to be
 * recreated as the result of a join or leave.
 * Sends a true message for whether the recreate is
 * true and the updated view value.
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 4/1/2018
 */

public class RecreateActivityEvent {
    public boolean isError;
    public int view;
    public String message;

    /**
     * Constructor that takes a boolean for whether this recreate
     * is real and an int for the new view value as a result of
     * joining or leaving.
     *
     * @param bool Is this recreate real or not
     * @param v Updated view state value
     */
    public RecreateActivityEvent(boolean bool, int v, String str)
    {
        isError = bool;
        view = v;
        message = str;
    }
}
