package edu.unh.cs.cs619.bulletzone.rest;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import edu.unh.cs.cs619.bulletzone.util.GridWrapper;

/**
 * Intercepts the response from the server and prints out the grid
 * that was sent.
 *
 * @author Simon
 * @version 1.0
 * @since 10/3/14
 */
public class HttpLoggerInterceptor implements ClientHttpRequestInterceptor {

    private static final String TAG = "HttpLoggerInterceptor";

    /**
     * Take the newest grid gathered from the server and print it to
     * System.out
     *
     * @param req request from the server
     * @param data an array of bytes
     * @param execution executes the server request
     * @return Returns the client response, probably to forward to the rest of the program
     * @throws IOException
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest req, byte[] data, ClientHttpRequestExecution execution) throws IOException {

        // TODO: do improved logging/authentication can be done here as well
        Log.d(TAG, "Http Request: " + req.getMethod() + " " + req.getURI());
        ClientHttpResponse res = execution.execute(req, data);

        if (req.getMethod().equals("GET")) {
            ObjectMapper mapper = new ObjectMapper();
            GridWrapper gw = mapper.readValue(res.getBody(), GridWrapper.class);
            int[][] grid = gw.getGrid();
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    System.out.print(grid[i][j]);
                }
                System.out.println("");
            }
        }

        Log.d(TAG, "Http Response: " + res.getStatusCode() + " " + res.getStatusText());
        return res;
    }
}