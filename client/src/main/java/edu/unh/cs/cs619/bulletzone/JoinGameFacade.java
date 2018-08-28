package edu.unh.cs.cs619.bulletzone;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.HttpClientErrorException;

import edu.unh.cs.cs619.bulletzone.control.ButtonHandler;
import edu.unh.cs.cs619.bulletzone.rest.BZRestErrorhandler;
import edu.unh.cs.cs619.bulletzone.rest.BulletZoneRestClient;
import edu.unh.cs.cs619.bulletzone.rest.GridPollerTask;

/**
 * This facade class hides much of the start game RestClient communication.
 * It is also responsible for setting up the Shakelistener class since
 * RestClient communication is necessary but it didn't fit in to the
 * RestHandler/BattlefieldFacade classes.
 *
 * @author Tyler Currier
 * @version 1.0
 * @since 4/2/2018
 */

@EBean
public class JoinGameFacade {

    //Class Variables
    @RestService
    BulletZoneRestClient restClient;

    @Bean
    BZRestErrorhandler bzRestErrorhandler;

    @Bean
    ButtonHandler buttonHandler;

    private long tankId;

    @Bean
    protected GridPollerTask gridPollTask;

    private static final String SC_SERVER = "http://stman1.cs.unh.edu:61908/games";
    private static final String CS619_SERVER = "http://stman1.cs.unh.edu:61918/games";
    private static final String NO_TANK = "NO TANK ON SERVER";

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Shakelistener mShakeDetector;
    private int bulletType = 2; //Default to 2
    private int serverState = 0;
    //End Class Variables

    //Public Methods:

    /**
     * Set the GridPollerTask for this Class. This is called
     * by ClientActivity because GridPollerTask uses the
     * NonInstanceConfiguration annotation which can only be called
     * from a class using the EActivity annotation.
     *
     * @param poll GridPollerTask from ClientActivity
     */
    public void setGridPollTask(GridPollerTask poll)
    {
        gridPollTask = poll;
    }

    /**
     * Set the buttonHandler's BattlefieldFacade to this
     * BattlefieldFacade.
     *
     * @param battlefieldFacade Reference to battleField Facade that button handler needs
     */
    public void setUpButtonHandler(BattlefieldFacade battlefieldFacade)
    {
        buttonHandler.setBattlefieldFacade(battlefieldFacade);
    }

    /**
     * Calls join through the RestClient and returns a tankId if the
     * join was successful.
     *
     * @return tankID
     */
    public long join()
    {
        tankId = restClient.join().getResult();
        return tankId;
    }


    /**
     * Start the GridPollerTask
     */
    public void doPoll()
    {
        gridPollTask.doPoll();
    }

    /**
     * Kill the polling sequence to ensure that that thread dies
     * on recreate
     */
    public void stopPoll()
    {
        gridPollTask.stopPoll();
    }

    /**
     * Set the ErrorHandler for the RestClient
     */
    public void setBzRestErrorhandler()
    {
        restClient.setRestErrorHandler(bzRestErrorhandler);
    }

    /**
     * Set up the Shakelistener class and establish a RestClient
     * connection for it. SensorManager has to be called in ClientActivity,
     * so it is passed in as a parameter here.
     *
     * @param sm SensorManager from ClientActivity
     */
    public void setShaking(SensorManager sm)
    {
        //Log.d("Shake", "Set Shaking");
        mSensorManager = sm;
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new Shakelistener();
        mShakeDetector.setOnShakelistener(new Shakelistener.OnShakeListener() {

            @Override
            public void onShake(int count) {
                //Log.d("Shake", "Entered onShake");
                //restClient.fire(tankId);
                new Thread(new Runnable() {
                    public void run(){
                        try {
                            //if (serverState == 0)
                                restClient.fire(tankId, bulletType, 0);
                            //else
                                //restClient.fire(tankId);
                        }
                        catch (HttpClientErrorException e)
                        {
                            Log.d(NO_TANK, "Unable to execute leave");
                        }
                    }
                }).start();
            }
        });
    }

    /**
     * Register the Shakelistener
     */
    public void shakeRegister()
    {
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    /**
     * Unregister the Shakelistener
     */
    public void shakeUnregister()
    {
        mSensorManager.unregisterListener(mShakeDetector);
    }

    /**
     * Set serverState variable and tell the RestClient to use the
     * proper URL based on the server state.
     *
     * @param sState
     */
    public void setServerState(int sState)
    {
        serverState = sState;

        if(sState == 0)
        {
            restClient.setRootUrl(SC_SERVER);
        }
        else
        {
            restClient.setRootUrl(CS619_SERVER);
        }
        buttonHandler.setServerState(sState);
        gridPollTask.setServerState(sState);

    }

    /**
     * Return the server state returned by ButtonHandler to the
     * calling function
     *
     * @return New state of server
     */
    public int getServerState()
    {
        return buttonHandler.getServerState();
    }


    /**
     * Pass along a TextView to ButtonHandler
     *
     * @param textView TextView from ClientActivity
     */
    public void setTextView(TextView textView)
    {
        buttonHandler.setTextView(textView);
    }

    /**
     * Pass along the current view state to ButtonHandler
     *
     * @param view current view state.
     */
    public void setView(int view)
    {
        buttonHandler.setView(view);
    }

    public GridPollerTask getGridPollTask()
    {
        return gridPollTask;
    }
    //End Public Methods
}
