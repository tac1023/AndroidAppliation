package edu.unh.cs.cs619.bulletzone.rest;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.rest.spring.annotations.RestService;
import org.androidannotations.rest.spring.api.RestClientHeaders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;

import edu.unh.cs.cs619.bulletzone.ClientActivity;
import edu.unh.cs.cs619.bulletzone.control.RecreateActivityEvent;
import edu.unh.cs.cs619.bulletzone.events.BusProvider;
import edu.unh.cs.cs619.bulletzone.util.GridWrapper;

/**
 * This class is responsible for, when prompted, getting the most
 * recent version of the grid/Battlefield form the RestClient and
 * sending it over the EventBus to all listeners.
 *
 * @author Simon, Tyler Currier
 * @version 1.2
 * @since 10/3/14
 */
@EBean (scope = EBean.Scope.Singleton)
public class GridPollerTask {
    private static final String TAG = "PollServer";

    // Injected object
    @Bean
    BusProvider busProvider;

    @RestService
    BulletZoneRestClient restClient;

    private long tankId;
    private boolean poll = true;
    //private int serverState = 0;
    private static final String SC_SERVER = "http://stman1.cs.unh.edu:61908/games";
    private static final String CS619_SERVER = "http://stman1.cs.unh.edu:61918/games";

    /**
     * This method sends out a message including the latest version of
     * the Battlefield from the server.
     */
    public void doPoll() {
        poll = true;
        new Thread(new Runnable() {
            public void run(){
                //Log.d("InPoll", "Poll started");
                while (poll) {
                    try {
                        onGridUpdate(restClient.grid(tankId));
                    }
                    catch(ResourceAccessException e)
                    {
                        String str = "Connection to Server Lost";
                        busProvider.getEventBus().post(new RecreateActivityEvent(true, 0, str));
                    }
                    // poll server every 100ms
                    SystemClock.sleep(100);
                }
            }
        }).start();
    }

    /**
     * Stop the while loop inside the doPoll Thread so that this
     * thread dies when the app is recreated.
     */
    public void stopPoll()
    {
        poll = false;
    }

    /**
     * Set the URL used by the RestClient
     *
     * @param sState new server state.
     */
    public void setServerState(int sState)
    {
        if(sState == 0)
        {
            restClient.setRootUrl(SC_SERVER);
        }
        else
        {
            restClient.setRootUrl(CS619_SERVER);
        }
    }

    /**
     * Give GridPollerTask the tankId of the player to use
     * when making get requests
     *
     * @param tankId ID of player
     */
    public void setTankId(long tankId)
    {
        this.tankId = tankId;
    }


    /**
     * Helper method that posts a new message to the EventBus
     *
     * @param gw Newest grid from server encased in a GridWrapper object
     */
    private void onGridUpdate(final GridWrapper gw) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                //Log.d("BusProvider", "Given Grid");
                busProvider.getEventBus().post(new GridUpdateEvent(gw));
                //System.out.println(gw.toString());
            }
        });
    }
}
