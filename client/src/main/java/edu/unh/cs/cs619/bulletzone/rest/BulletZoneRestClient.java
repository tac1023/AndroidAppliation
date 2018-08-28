package edu.unh.cs.cs619.bulletzone.rest;

import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.annotations.RestService.*;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.androidannotations.rest.spring.api.RestClientHeaders.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;

import edu.unh.cs.cs619.bulletzone.util.BooleanWrapper;
import edu.unh.cs.cs619.bulletzone.util.GridWrapper;
import edu.unh.cs.cs619.bulletzone.util.LongWrapper;

/** "http://stman1.cs.unh.edu:6191/games"
 * "http://10.0.0.145:6191/games"
 * http://10.0.2.2:8080/
 * Created by simon on 10/1/14.
 *
 * our port = 61908
 * cur other port = 6192
 */

@Rest(rootUrl = "http://stman1.cs.unh.edu:61908/games",
        converters = {StringHttpMessageConverter.class, MappingJackson2HttpMessageConverter.class}
        // TODO: disable intercepting and logging
        // , interceptors = { HttpLoggerInterceptor.class }
)

/**
 * Interface for the RestClient in the context of BulletZone
 *
 * @author Tyler Currier, ???(Other Person)
 * @version 3.0
 * @since 4/17/2018
 */
public interface BulletZoneRestClient extends RestClientErrorHandling {
    void setRootUrl(String rootUrl);

    /**
     * Used to get the tankID from the server
     *
     * @return the tankID in a LongWrapper
     * @throws RestClientException throws exception upon failure
     */
    @Post("")
    LongWrapper

    /**
     * Join the server, throws exception upon failure
     */
    join() throws RestClientException;

    /**
     * Get the latest grid from the server
     *
     * @return the latest grid in a GridWrapper
     */
    @Get("/{tankId}/grid")
    GridWrapper grid(@Path long tankId);

    /**
     * Tells the server to move the tank to a new location. Server returns
     * true if the requested move is allowed or false if it is in violation
     * of the server constraints
     *
     * @param tankId The ID of the tank to move
     * @param direction The direction to move the tank
     * @return true if success, false if failure
     */
    @Put("/{tankId}/move/{direction}")
    BooleanWrapper move(@Path long tankId, @Path byte direction);

    /**
     * Tells the server to turn the tank to a new direction. Server returns
     * true if the requested turn is allowed or false if it is in violation
     * of the server constraints
     *
     * @param tankId The ID of the tank to move
     * @param direction The direction to which to turn the tank
     * @return true if success, false if failure
     */
    @Put("/{tankId}/turn/{direction}")
    BooleanWrapper turn(@Path long tankId, @Path byte direction);



    /**
     * Tells the server to fire a bullet from the tank. Server returns true if
     * the requested fire is allowed or false if it is in violation of the
     * server constraints.
     *
     * DEPRECATED
     *
     * @param tankId The ID of the tank from which to fire a bullet
     * @return true if success, false if failure
     */
    //@Put("/{tankId}/fire/")
    //BooleanWrapper fire(@Path long tankId);

    /**
     * Tells the server to fire a bullet  of the given type from the tank.
     * Server returns true if the requested fire is allowed or false if it
     * is in violation of the server constraints.
     *
     * @param tankId The ID of the tank from which to fire a bullet
     * @param bulletType The type of bullet to fire
     * @return true if success, false if failure
     */
    @Put("/{tankId}/fire/{bulletType}/{direction}/")
    BooleanWrapper fire(@Path long tankId, @Path int bulletType, @Path int direction);

    /**
     * Tells the server to fire a guidedMissile
     *
     * @param tankId ID of tank from which to fire
     * @return true if success, false if failure
     */
    @Put("/{tankId}/fireMissile/")
    BooleanWrapper fireMissile(@Path long tankId);

    /**
     * Tells the server to eject the soldier from the tank. Server returns true if the ejection is
     * within the requirements listed in specifications. False if the ejection was invalid or
     * ejected too quickly.
     *
     * @param tankId
     * @return true if ejection was successful
     */
    @Put("/{tankId}/eject/")
    BooleanWrapper eject( @Path long tankId );

    /**
     * Tells the server to transform the tank into a Tunneler. Server returns true if the tank is
     * within the specified bounds that can be found in spec.
     *
     * @param tankId
     * @return true of transformation was successful
     */
    @Put("/{tankId}/tunneler/")
    BooleanWrapper toTunneler( @Path long tankId );

    /**
     * Sends rest request for the tunneler to drill.
     * @param tankId
     * @return true if command was sent successfully
     */
    @Put("/{tankId}/drill/")
    BooleanWrapper drill( @Path long tankId );

    /**
     * Tells the server to transform the tank into a Ship. Server returns true if the tank is
     * within the specified bounds that can be found in spec.
     *
     * @param tankId
     * @return true of transformation was successful
     */
    @Put("/{tankId}/ship/")
    BooleanWrapper toShip( @Path long tankId );

    /**
     * Tells the server to delete the tank from the game. Server returns true if
     * the requested leave succeeds or false if it fails.
     *
     * @param tankId The ID of the tank to remove from the game
     * @return true if success, false if failure
     */
    @Delete("/{tankId}/leave")
    BooleanWrapper leave(@Path long tankId);
}
