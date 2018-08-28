package edu.unh.cs.cs619.bulletzone.replay;

import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Scanner;

import edu.unh.cs.cs619.bulletzone.events.BusProvider;
import edu.unh.cs.cs619.bulletzone.rest.GridUpdateEvent;
import edu.unh.cs.cs619.bulletzone.util.GridWrapper;

/**
 * Poller object used to feed the replay frames.
 * @author Jason Vettese
 * @since 4/18/2018
 * @version 1.0
 */
@EBean(scope = EBean.Scope.Singleton)
public class ReplayPoller {

    @Bean
    BusProvider busProvider;

    private Cursor cursor = ReplayController.getInstance(null).getCursor();

    private boolean poll = true;
    private int millis = 100;
    private int cursorPosition = 0;

    /**
     * Starts a poll by feeding the eventbus frames.
     */
    public void doPoll() {
        poll = true;
        cursor = ReplayController.getInstance(null).getCursor();
        cursor.moveToPosition(cursorPosition);
        new Thread(new Runnable() {
            public void run(){
                //Log.d("InPoll", "Poll started");
                while (poll) {
                    //onGridUpdate(restClient.grid(tankId));
                    try {
                        GridWrapper gw = null;
                        if (cursor.moveToNext()) {
                            int[][] grid = to2DArray(cursor.getString(2));
                            int[][] tank = to2DArray(cursor.getString(3));
                            //Boolean isAlive = cursor.getInt(4) == 1;
                            gw = new GridWrapper(grid, tank, true, 0, 0);
                            //Log.d("ReplayPoller", "Successfully made it this far-" + cursor.getInt(0));

                        }
                        if (gw != null)
                            onGridUpdate(gw);
                        // poll server every 100ms
                    } catch (Exception e) {
                        //Log.d("ReplayPoller", "Out of bounds exception");
                        poll = false;
                    }
                    SystemClock.sleep(millis);
                }
            }
        }).start();
    }

    /**
     * Stops poll and
     */
    public void stopPoll()
    {
        poll = false;
        cursor.moveToPosition(0);
    }

    /**
     * Sets the replay speed by changig the poll time.
     * @param i
     */
    public void setReplaySpeed( int i ) {
        millis = i;
    }

    /**
     * Gets the current Replay speed
     * @return replay speed
     */
    public int getSpeed() {
        return millis;
    }

    /**
     * Sets the cursor position to the specified i.
     * @param i
     */
    public void setCursorPosition( int i ) {
        cursor.moveToPosition(i);
        cursorPosition = i;
    }

    /**
     * Stops poll but maintians the current cursor position.
     */
    public void pausePoll() {
        poll = false;
        cursorPosition = cursor.getPosition();
    }

    /**
     * Declares if the object is polling.
     * @return boolean
     */
    public boolean isPolling() {
        return poll;
    }

    /**
     * Sends message to the eventbus.
     * @param gw
     */
    private void onGridUpdate(final GridWrapper gw) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                //Log.d("BusProvider", "Given Grid");
                busProvider.getEventBus().post(new GridUpdateEvent(gw, true));
                //System.out.println(gw.toString());
            }
        });
    }

    /**
     * Converts a string to a 2D array.
     * @param string
     * @return 2D array
     */
    private int[][] to2DArray(String string){
        Scanner scanner = new Scanner(string);
        int[][] arr = new int[16][16];
        for( int i = 0; i < 16; i++ ){
           for( int j = 0; j < 16; j++ ) {
               arr[i][j] = scanner.nextInt();
           }
        }
        return arr;
    }
}
