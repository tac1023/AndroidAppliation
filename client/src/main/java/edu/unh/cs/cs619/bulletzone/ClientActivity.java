package edu.unh.cs.cs619.bulletzone;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Path;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.annotations.RestService;
import org.androidannotations.rest.spring.api.RestClientHeaders;
import org.androidannotations.api.BackgroundExecutor;

import edu.unh.cs.cs619.bulletzone.control.ButtonHandler;
import edu.unh.cs.cs619.bulletzone.control.RecreateActivityEvent;
import edu.unh.cs.cs619.bulletzone.control.StartReplayActivityEvent;
import edu.unh.cs.cs619.bulletzone.events.BusProvider;
import edu.unh.cs.cs619.bulletzone.replay.ReplayController;
import edu.unh.cs.cs619.bulletzone.rest.BZRestErrorhandler;
import edu.unh.cs.cs619.bulletzone.rest.BulletZoneRestClient;
import edu.unh.cs.cs619.bulletzone.rest.GridPollerTask;
import edu.unh.cs.cs619.bulletzone.rest.GridUpdateEvent;
import edu.unh.cs.cs619.bulletzone.ui.GridAdapter;
import edu.unh.cs.cs619.bulletzone.util.GridWrapper;

/**
 * Main Activity for the Client Application. Responsible for starting
 * up tasks but not for knowing about their data. This class creates
 * some objects that it doesn't need itself but passes on to other
 * classes because those objects had to be created here. This class
 * also deals with orientation.
 *
 * @author ???
 * @author Tyler Currier
 * @version 4.0
 * @since 4/17/2018
 */
@EActivity(R.layout.activity_client)
public class ClientActivity extends Activity {

    //Class Variables:

    @Bean
    BusProvider busProvider;

    @Bean
    BattlefieldFacade battlefieldFacade;

    @Bean
    JoinGameFacade joinGameFacade;

    protected GridView gridView;
    protected TextView textView;
    protected TextView textBox;
    protected TextView textBox2;
    protected TextView healthText;

    private int view = 0; //0 = home screen; 1 = game screen
    private int serverState = 0;
    //0 for our server, 1 for 619
    private boolean wasError;
    private boolean startUp = true; //NEW
    private String errorMessage = "";
    private Resources resources;
    private Activity activity;
    //private boolean isDead = false; //NEW

    /**
     * Remote tank identifier
     */
    long tankId = -1;

    long lastTankId = -1;
    //End Class Variables

    //Final Variables:
    private static final String TAG = "ClientActivity";
    private static final String VIEW_STATE = "viewState";
    private static final String SERVER_STATE = "serverState";
    private static final String TANK_ID = "tankId";
    private static final String START_UP = "startUp";
    private static final String WAS_ERROR = "wasError";
    private static final String ERROR_MESSAGE = "errorMessage";
    private Button button;
    //private static final String IS_DEAD = "isDead";
    //End Final Variables

    /**
     * When this app is first created, restore the view variable if
     * there is a savedInstance State, set the content view, and
     * set whether the app can rotate or not based on which screen it
     * is on (Home screen or Game screen).
     *
     * @param savedInstanceState Saved state from previous instance if applicable
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = this;
        Log.d("ClientActivity", "onCreate was called here");
        if(savedInstanceState != null)
        {
            view = savedInstanceState.getInt(VIEW_STATE);
            serverState = savedInstanceState.getInt(SERVER_STATE);
            wasError = savedInstanceState.getBoolean(WAS_ERROR);
            errorMessage = savedInstanceState.getString(ERROR_MESSAGE);
            lastTankId = savedInstanceState.getLong(TANK_ID);
            startUp = savedInstanceState.getBoolean(START_UP);
            //isDead = savedInstanceState.getBoolean(IS_DEAD);

        }
        ReplayController r = ReplayController.getInstance(this);
        setContentView(R.layout.activity_client);

        //Disable rotation on Home Screen
        if(view == 0)
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        }
        else
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
            //^UNCOMMENT ABOVE TO DISABLE ALL ROTATION!!!
        }

        SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        joinGameFacade.setShaking(mSensorManager);
        resources = getResources();
        battlefieldFacade.setResources(resources);
        battlefieldFacade.setReplayController(ClientActivity.this);



    }


    /**
     * If the Activity is restarting due to a state change, save the
     * view variable in the Bundle so it can be restored on recreate.
     *
     * @param savedInstanceState Bundle in which to store current state variables
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putInt(VIEW_STATE, view);
        //serverState = battlefieldFacade.getServerState();
        serverState = joinGameFacade.getServerState();
        savedInstanceState.putInt(SERVER_STATE, serverState);
        savedInstanceState.putBoolean(WAS_ERROR, wasError);
        savedInstanceState.putString(ERROR_MESSAGE, errorMessage);
        savedInstanceState.putLong(TANK_ID, lastTankId);
        savedInstanceState.putBoolean(START_UP, startUp);
        //savedInstanceState.
        //isDead = battlefieldFacade.isDead();
        //if(startUp)
            //isDead = false;
        //savedInstanceState.putBoolean(IS_DEAD, isDead);
        //System.out.println("2: IsDead = " + isDead);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Call super's on Destroy and Unregister ClientActivity from
     * the EventBus.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        busProvider.getEventBus().unregister(restartActivityHandler);
        busProvider.getEventBus().unregister(startReplayActivityHandler);
        battlefieldFacade.unregister();

        //System.out.println("IsDead = " + isDead);
        try {
            joinGameFacade.stopPoll();
        }
        catch (NullPointerException e)
        {
            Log.d(TAG, "Nullpointer on stopPoll() in onDestroy");
        }
        //Log.d("Destroy", "Main Activity destroyed");
    }

    /**
     * Register Shakelistener onResume
     */
    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        joinGameFacade.shakeRegister();
    }

    /**
     * Unregister Shakelistener onPause
     */
    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        joinGameFacade.shakeUnregister();
        super.onPause();
    }

    /**
     * Otto has a limitation (as per design) that it will only find
     * methods on the immediate class type. As a result, if at runtime this instance
     * actually points to a subclass implementation, the methods registered in this class will
     * not be found. This immediately becomes a problem when using the AndroidAnnotations
     * framework as it always produces a subclass of annotated classes.
     * <p>
     * To get around the class hierarchy limitation, one can use a separate anonymous class to
     * handle the events.
     */
    private Object restartActivityHandler = new Object()
    {
        @Subscribe
        public void onRestartActivity(RecreateActivityEvent event) {
            wasError = event.isError;
            errorMessage = event.message;
            restart(event.view);
        }
    };

    /**
     * Otto has a limitation (as per design) that it will only find
     * methods on the immediate class type. As a result, if at runtime this instance
     * actually points to a subclass implementation, the methods registered in this class will
     * not be found. This immediately becomes a problem when using the AndroidAnnotations
     * framework as it always produces a subclass of annotated classes.
     * <p>
     * To get around the class hierarchy limitation, one can use a separate anonymous class to
     * handle the events.
     */
    private Object startReplayActivityHandler = new Object()
    {
        @Subscribe
        public void onStartReplayActivity(StartReplayActivityEvent event)
        {
            startReplay();
        }
    };

    //Timed Methods:

    /**
     * After Views have been created, set the LinearLayout format
     * and, if on GameScreen, Join and set up the game.
     */
    @AfterViews
    protected void afterViewInjection() {
        //Set up which buttons/views are visible based on view state
        //0 equals home screen views, 1 equals game screen
        setGameViewsVisibility();

        LinearLayout layout = (LinearLayout) this.findViewById(R.id.LinearLayout1);
        setLinearLayout(layout);

        //Join and set up game if currently setting up game screen
        joinGameFacade.setView(view);
        if(view != 0) {
            joinAsync();
            SystemClock.sleep(500);

            //gridView.setAdapter(mGridAdapter); //TOOK OUT
            gridView = (GridView) findViewById(R.id.gridView);
            battlefieldFacade.setGridViewAdapter(gridView);

            ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();

            //Format layout/GridView based on Orientation of the device
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            {
                layoutParams.width = convertDpToPixels(400, this);
            }
            else
            {
                layoutParams.width = convertDpToPixels(600, this);
            }

        }
        else
        {
            textView = (TextView) findViewById(R.id.curServerView);
            joinGameFacade.setTextView(textView);
        }

        if(wasError)
        {
            wasError = false;
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(this, errorMessage, duration);
            toast.show();
        }

        textBox = (TextView) findViewById(R.id.textBox);
        textBox2 = (TextView) findViewById(R.id.textBox2);
        healthText = (TextView) findViewById(R.id.healthText);
        battlefieldFacade.setTextBox(textBox, textBox2, healthText);

    }

    /**
     * Set up the RestClient, register EventBuses, and start up shake detection
     */
    @AfterInject
    void afterInject() {

        joinGameFacade.setBzRestErrorhandler();
        busProvider.getEventBus().register(restartActivityHandler);
        busProvider.getEventBus().register(startReplayActivityHandler);
        battlefieldFacade.register();
    }

    //End Timed Methods

    //Methods on Threads:

    /**
     * Join the game by connecting to the server.
     */
    void joinAsync() {
        new Thread(new Runnable() {
            public void run(){
                //joinGameFacade.setGridPollTask(gridPollTask);
                joinGameFacade.setServerState(serverState);
                //System.out.println("1");
                try {
                    tankId = joinGameFacade.join();
                    lastTankId = tankId;
                    //System.out.println("Tank ID at join = " + tankId);
                    System.out.println("JOINED SERVER!!!!!!!!!");
                    startUp = false;

                    joinGameFacade.getGridPollTask().setTankId(tankId);
                    battlefieldFacade.setPlayerID(tankId);
                    battlefieldFacade.setActivity(activity); //pass a reference to this activity
                    battlefieldFacade.setReplayController(ReplayController.getInstance(activity));
                    joinGameFacade.setUpButtonHandler(battlefieldFacade);

                    //joinGameFacade.setGridPollTask(gridPollTask);
                    joinGameFacade.doPoll();

                } catch (Exception e) { //Couldn't connect to server
                    Log.d(TAG, "Exception in joinAsync");
                    wasError = true;
                    errorMessage = "Couldn't Connect to Server";
                    restart(0);
                }
            }
        }).start();
    }

    //End Methods on Threads

    //Public Methods:
    /**
     * Recreate this activity
     *
     * @param v The view to set in onCreate
     */
    public void restart(int v)
    {
        view = v;
        startUp = true;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recreate();
            }
        });
    }

    /**
     * Upon the Replay Button being clicked, recreate the Activity,
     * start up a new connection to the server, and display the
     * Game Screen.
     */
    public void startReplay() {
        Intent intent = new Intent(ClientActivity.this, ReplayActivity_.class);
        //System.out.println("Before Tank ID " + lastTankId);
        intent.putExtra(TANK_ID, lastTankId);
        startActivity(intent);
    }

    /**
     * Converts a value in dp to the equivalent value in pixels for the
     * specific device. Credit goes to:
     * https://stackoverflow.com/questions/5263563/linearlayout-findviewbyid-problem
     *
     * @param dp  The desired value in Density-Independent Pixels
     * @param context  This device
     * @return  The desired value in normal Pixels
     */
    public int convertDpToPixels(float dp, Context context){
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
    }

    /**
     * Called for testing purposes.
     * @return context
     */
    public Context getContext(){
        return ClientActivity.this;
    }
    //End Public Methods

    //Private Methods:

    /**
     * Show the appropriate views based on what state the game is in, either
     * home screen or game screen. The view IDs are hard coded, so they need to
     * be edited if views are added or removed from activity_client.xml.
     */
    private void setGameViewsVisibility()
    {
        //Hard-code Integer arrays of relevant view IDs. EDIT IF IDS ARE CHANGED!!!
        Integer[] gameViews = {R.id.gridView, R.id.textBox, R.id.buttonDown, R.id.buttonUp, R.id.buttonLeft,
            R.id.buttonRight, R.id.buttonFire, R.id.buttonLeave, R.id.buttonEject, R.id.health,
            R.id.health2, R.id.textBox2, R.id.buttonTunneler, R.id.buttonShip, R.id.buttonDrill,
            R.id.buttonFireDown, R.id.buttonFireLeft, R.id.buttonFireRight, R.id.buttonMissile,
                R.id.healthText, R.id.relativeView};

        Integer[] homeViews = {R.id.title_picture, R.id.buttonJoin, R.id.curServerView,
            R.id.buttonSwitchServer, R.id.buttonReplay};

        View b;
        if(view == 0)
        {
            //Hide in-game views
            for(int i = 0; i < gameViews.length; i++)
            {
                b = findViewById(gameViews[i]);
                b.setVisibility(View.GONE);
            }

            //Show home-screen views
            for(int i = 0; i < homeViews.length; i++)
            {
                b = findViewById(homeViews[i]);
                b.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            //Show in-game views
            for(int i = 0; i < gameViews.length; i++)
            {
                b = findViewById(gameViews[i]);
                b.setVisibility(View.VISIBLE);
            }

            //Hide home-screen views
            for(int i = 0; i < homeViews.length; i++)
            {
                b = findViewById(homeViews[i]);
                b.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Set whether the Linear Layout uses a vertical or horizontal
     * layout depending on the current screen orientation
     *
     * @param layout The LinearLayout whose format to set
     */
    private void setLinearLayout(LinearLayout layout)
    {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            layout.setOrientation(LinearLayout.VERTICAL);
        }
        else
        {
            layout.setOrientation(LinearLayout.HORIZONTAL);
        }
    }

    //End Private Methods
}
