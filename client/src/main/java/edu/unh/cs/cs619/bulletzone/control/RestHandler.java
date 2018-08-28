package edu.unh.cs.cs619.bulletzone.control;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.api.BackgroundExecutor;
import org.androidannotations.rest.spring.annotations.RestService;
//import org.apache.http.client.HttpClient;
import org.springframework.web.client.HttpClientErrorException;

import edu.unh.cs.cs619.bulletzone.ClientActivity;
import edu.unh.cs.cs619.bulletzone.events.BusProvider;
import edu.unh.cs.cs619.bulletzone.rest.BulletZoneRestClient;

/**
 * This Class encapsulates most of the requests to the RestClient from the
 * server. Also alerts ClientActivity when the game is ending.
 *
 * @author Tyler Currier
 * @version 1.1
 * @since 4/17/2018
 */

@EBean
public class RestHandler {

    //Class Variable:
    @RestService
    BulletZoneRestClient restClient;

    @Bean
    BusProvider busProvider;

    private int view;
    private static final String NO_TANK = "NO TANK ON SERVER";
    private Context context;
    //End Class Variables

    /**
     * Rest handler constructor.
     * @param c
     */
    public RestHandler(Context c)
    {
        context = c;
    }

    //Public Methods

    /**
     * Set the view state, 0 for Home Screen 1 for Game Screen
     *
     * @param v value of current view.
     */
    public void setView(int v)
    {
        view = v;
    }

    /**
     * Send a request to the server for the indicated tank to move
     * the indicated direction
     *
     * @param tankId ID of tank to move
     * @param direction Direction to move tank
     */
    void moveAsync(final long tankId, final byte direction) {
        new Thread(new Runnable() {
            public void run(){
                try {
                    restClient.move(tankId, direction);
                }
                catch(HttpClientErrorException e)
                {
                    Log.d(NO_TANK, "Unable to execute move");
                }
            }
        }).start();
    }

    /**
     * Send a request to the server for the indicated tank to
     * turn to the indicated direction
     *
     * @param tankId ID of tank to turn
     * @param direction Direction to which to turn tank
     */
    void turnAsync(final long tankId, final byte direction) {
        new Thread(new Runnable() {
            public void run(){
                try {
                    restClient.turn(tankId, direction);
                }
                catch(HttpClientErrorException e)
                {
                    Log.d(NO_TANK, "Unable to execute turn");
                }
            }
        }).start();
    }

    /**
     * Send a request to the server for the indicated tank to
     * fire a bullet.
     *
     * @param tankId ID of tank from which to fire bullet.
     */
    void fireAsync(final long tankId)
    {
        new Thread(new Runnable() {
            public void run(){
                try {
                    //restClient.fire(tankId);
                }
                catch (HttpClientErrorException e)
                {
                    Log.d(NO_TANK, "Unable to execute fire");
                }
            }
        }).start();
    }

    /**
     * Send a request to the server for the indicated tank to
     * fire a bullet of the indicated type.
     *
     * @param tankId ID of tank from which to fire bullet
     * @param bulletType Type of bullet to fire
     * @param relativeDirection Direction in which to fire
     */
    void fireAsync(final long tankId, final int bulletType, final int relativeDirection)
    {
        new Thread(new Runnable() {
            public void run(){
                try {
                    restClient.fire(tankId, bulletType, relativeDirection);
                }
                catch (HttpClientErrorException e)
                {
                    Log.d(NO_TANK, "Unable to execute fire");
                }
            }
        }).start();
    }

    /**
     * Send a request to the server for the indicated tank to fire
     * a guided missile.
     *
     * @param tankId ID of tank from which to fire
     */
    void fireMissileAsync(final long tankId)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    restClient.fireMissile(tankId);
                }
                catch (HttpClientErrorException e)
                {
                    Log.d(NO_TANK, "Unable to execute fire missile");
                }
            }
        }).start();
    }

    /**
     * Send a request to the server for the indicated tank to
     * eject a soldier that the player can then control.
     *
     * @param tankId ID of tank from which to eject soldier
     */
    void ejectAsync(final long tankId ){
        new Thread(new Runnable() {
            public void run() {
                try{
                    restClient.eject(tankId);
                } catch (HttpClientErrorException e) {
                    Log.d("Eject issue", "Unable to eject from HTTP");
                }
            }
        }).start();
    }

    /**
     * Attempts to create a Tunneler out of the Tank.
     * @param tankId
     */
    void transformIntoTunneler( final long tankId ){
        new Thread(new Runnable() {
            public void run() {
                try{
                    restClient.toTunneler(tankId);
                } catch (HttpClientErrorException e) {
                    Log.d("Tunneler issue", "Unable to create Tunneler from HTTP");
                }
            }
        }).start();
    }

    void drill( final long tankId ) {
        new Thread(new Runnable() {
            public void run() {
                try{
                    restClient.drill(tankId);
                } catch (HttpClientErrorException e) {
                    Log.d("Drilling issue", "Unable to create Tunneler from HTTP");
                }
            }
        }).start();
    }

    /**
     * Attempts to create a ship out of the Tank.
     * @param tankId
     */
    void transformIntoShip( final long tankId ){
        new Thread(new Runnable() {
            public void run() {
                try{
                    restClient.toShip(tankId);
                } catch (HttpClientErrorException e) {
                    Log.d("Ship issue", "Unable to create a ship from HTTP");
                }
            }
        }).start();
    }

    /**
     * Send a request to the server for the player's tank to be destroyed,
     * end communication with the server, and return to the Home Screen.
     */
    void leaveGame(final long tankId) {
        new Thread(new Runnable() {
            public void run(){
                try {
                    restClient.leave(tankId);
                }
                catch (HttpClientErrorException e)
                {
                    Log.d(NO_TANK, "Unable to execute leave");
                }

                view = 0;

                //Recreate the application
                busProvider.getEventBus().post(new RecreateActivityEvent(false, view, ""));
            }
        }).start();
    }

    /**
     * Set the URL used by the RestClient
     *
     * @param url New URL to use
     */
    public void setUrl(String url)
    {
        restClient.setRootUrl(url);
    }



    //End Public Methods
}
