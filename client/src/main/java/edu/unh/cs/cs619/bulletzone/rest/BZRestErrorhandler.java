package edu.unh.cs.cs619.bulletzone.rest;

import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;
import org.androidannotations.rest.spring.api.RestClientHeaders;
import org.androidannotations.rest.spring.api.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;

/**
 * Handles Errors from the RestClient...I think
 *
 * @author Simon
 * @version 1.0
 * @since 10/3/14
 */
@EBean
public class BZRestErrorhandler implements RestErrorHandler {

    /**
     * Prints out a stackTrace when the RestClient throws a
     * NestedRuntimeException.
     *
     * @param e A NestedRuntimeException from the RestClient
     */
    @Override
    public void onRestClientExceptionThrown(NestedRuntimeException e) {
        e.printStackTrace();
    }
}
