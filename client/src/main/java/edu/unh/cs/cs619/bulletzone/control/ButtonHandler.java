package edu.unh.cs.cs619.bulletzone.control;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Path;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.api.BackgroundExecutor;
import org.androidannotations.rest.spring.annotations.RestService;

import edu.unh.cs.cs619.bulletzone.BattlefieldFacade;
import edu.unh.cs.cs619.bulletzone.ClientActivity;
import edu.unh.cs.cs619.bulletzone.R;
import edu.unh.cs.cs619.bulletzone.events.BusProvider;
import edu.unh.cs.cs619.bulletzone.replay.ReplayController;
import edu.unh.cs.cs619.bulletzone.rest.BulletZoneRestClient;

/**
 * This class encapsulates all of the tracking of when buttons
 * are pressed and what to do when they are.
 *
 * @author Tyler Currier
 * @version 1.1
 * @since 4/17/2018
 */

@EBean
public class ButtonHandler {

    //Class Variables:
    @Bean
    BusProvider busProvider;

    @Bean
    RestHandler restHandler;

    private TextView textView;
    private long tankId;
    //private byte currentDirection = 0;
    private int view = 0;
    private BattlefieldFacade battlefieldFacade;
    private Context context;
    private int bulletType = 2; //Default to 2
    private int serverState = 0; //0 for Spaghetti Coders, 1 for CS619
    private ReplayController replayController;
    private Cursor replayCursor = null;
    //End Class Variables

    //Final Variables:
    private static final String TAG = "ClientActivity";
    private static final String SC_SERVER = "http://stman1.cs.unh.edu:61908/games";
    private static final String CS619_SERVER = "http://stman1.cs.unh.edu:61918/games";
    private static final String TV_SC_SERVER = "Current Server: Spaghetti Coders";
    private static final String TV_CS619_SERVER = "Current Server: Spaghetti Coders Test";
    //End Final Variables

    //Public Methods:

    /**
     * Constructor, takes the Calling context as a parameter
     *
     * @param c Context of the whole activity
     */
    public ButtonHandler(Context c)
    {
        replayController = ReplayController.getInstance(c);
        context = c;
    }


    /**
     * Gives this class access to the BattlefieldFacade.
     * This class is called from the BattlefieldFacade.
     *
     * @param bf The BattlefieldFacade
     */
    public void setBattlefieldFacade(BattlefieldFacade bf)
    {
        battlefieldFacade = bf;
        //replayController = ReplayController.getInstance(context);

        setTankID(battlefieldFacade.getPlayerID());
    }

    /**
     * Track text view to change String when proper button is
     * pressed
     *
     * @param tv TextView to track
     */
    public void setTextView(TextView tv)
    {
        textView = tv;
    }

    /**
     * Sets the value of the current view state. 0 is Home Screen,
     * 1 is Game Screen
     *
     * @param v Current view
     */
    public void setView(int v)
    {
        view = v;
    }

    /**
     * Return server state to calling function
     *
     * @return server state
     */
    public int getServerState()
    {
        return serverState;
    }

    /**
     * Set the server state variable, set the proper URL for that state,
     * and set the proper TextView text.
     *
     * @param sState New server state
     */
    public void setServerState(int sState)
    {
        serverState = sState;
        if(serverState == 0) {
            restHandler.setUrl(SC_SERVER);
            if(view == 0)
                textView.setText(TV_SC_SERVER);
        }
        else {
            restHandler.setUrl(CS619_SERVER);
            if(view == 0)
                textView.setText(TV_CS619_SERVER);
        }
    }

    //End Public Methods

    //Private Methods:

    /**
     * Sets the value of the tankId.
     *
     * @param id new tankId of player
     */
    private void setTankID(long id)
    {
        tankId = id;
    }
    //End Private Methods

    //Click Methods:
    /**
     * If the direction controls are used, send the correct motion request to
     * the server
     *
     * @param view The Buttons that can call this method
     */
    @Click({R.id.buttonUp, R.id.buttonDown, R.id.buttonLeft, R.id.buttonRight})
    protected void onButtonMove(View view) {
        final int viewId = view.getId();
        byte direction = 0;

        switch (viewId) {
            case R.id.buttonUp:
                Log.d("ButtonUP", "Pressed");
                direction = 0;
                break;
            case R.id.buttonDown:
                direction = 4;
                break;
            case R.id.buttonLeft:
                direction = 6;
                break;
            case R.id.buttonRight:
                direction = 2;
                break;
            default:
                Log.e(TAG, "Unknown movement button id: " + viewId);
                break;
        }

        /*
         * From here down is the work of Aaron (hj1000)
         * When we click Ahead or Reverse the tank moves forward or backward
         * relative to its current direction
         * When we click left or right the tank turns 90 degrees to the
         * left or right
         */

        Byte currentDirection = battlefieldFacade.getCurrentDirection();

        if (direction == 0) {
            restHandler.moveAsync(tankId, currentDirection);
        } else if (direction == 4) {
            byte currentDirection1 = (byte)(currentDirection + 4);
            if(currentDirection1 == 8)
            {
                currentDirection1 = 0;
            }
            else if(currentDirection1 == 10)
            {
                currentDirection1 = 2;
            }
            restHandler.moveAsync(tankId, currentDirection1);
        } else if (direction == 2) {
            byte currentDirection1 = (byte) (currentDirection + 2);

            if (currentDirection1 == 8) {
                currentDirection1 = (byte)(currentDirection1-8);

            }
            restHandler.turnAsync(tankId, (byte) (currentDirection1));
        }
        else if (direction == 6) {
            byte currentDirection1 = (byte) (currentDirection - 2);

            if (currentDirection1 == -2) {
                currentDirection1 = (byte)6;
            }
            restHandler.turnAsync(tankId, (byte) (currentDirection1));
        }
    }

    /**
     * Upon the Fire Button being cliched, send a request to the server
     * for the indicated tank to fire a bullet.
     */
    @Click(R.id.buttonFire)
    protected void onButtonFire()
    {
        restHandler.fireAsync(tankId, bulletType, 0);
    }

    /**
     * Upon the Fire Button being cliched, send a request to the server
     * for the indicated tank to fire a bullet left.
     */
    @Click(R.id.buttonFireLeft)
    protected void onButtonFireLeft()
    {
        restHandler.fireAsync(tankId, bulletType, 6);
    }

    /**
     * Upon the Fire Button being cliched, send a request to the server
     * for the indicated tank to fire a bullet right.
     */
    @Click(R.id.buttonFireRight)
    protected void onButtonFireRight()
    {
        restHandler.fireAsync(tankId, bulletType, 2);
    }

    /**
     * Upon the Fire Button being cliched, send a request to the server
     * for the indicated tank to fire a bullet down.
     */
    @Click(R.id.buttonFireDown)
    protected void onButtonFireDown()
    {
        restHandler.fireAsync(tankId, bulletType, 4);
    }

    /**
     * This method is responsible for dealing with the ejection of a soldier.
     */
    @Click(R.id.buttonEject)
    void ejectSoldier() {
      //  Log.d("Soldier", "I am too lazy to implement this" );
        restHandler.ejectAsync(tankId);
    }

    /**
     * Transforms the tank into a tunneler
     */
    @Click(R.id.buttonTunneler)
    void tunnel() {
        restHandler.transformIntoTunneler(tankId);
        Log.d("ButtonHandler", "tunnel button pressed");
    }

    /**
     * Attempts to use Tunneler to drill.
     */
    @Click(R.id.buttonDrill)
    void drill() {
        restHandler.drill(tankId);
        Log.d("ButtonHandler", "drill button pressed");
    }

    /**
     * Transforms the tank into a ship.
     */
    @Click(R.id.buttonShip)
    void transformIntoShip() {
        restHandler.transformIntoShip(tankId);
        Log.d("ButtonHandler", "transform into ship called");
    }

    /**
     * Upon the Leave Button being clicked, start a pop up menu to prompt
     * the user if the action want to leave the game. Clicking yes will
     * result in the LeaveGame method being called. No will close the
     * menu to allow the player to keep playing the game.
     */
    @Click(R.id.buttonLeave)
    void leaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to leave?")
                .setTitle(R.string.leave)
                .setPositiveButton(R.string.leave, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        replayCursor = replayController.getCursor();
                        if( replayCursor == null)
                            Log.d("replay", "cursor is null");
                        restHandler.leaveGame(tankId);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Upon the Join Button being clicked, recreate the Activity,
     * start up a new connection to the server, and display the
     * Game Screen.
     */
    @Click(R.id.buttonJoin)
    //@Background
    void join()
    {
        view = 1;
        //replayController.getInstance(context);
        if(battlefieldFacade != null)
            battlefieldFacade.resetReplay();
        replayController.clearEntries();
        //Recreate the Application
        busProvider.getEventBus().post(new RecreateActivityEvent(false, view, ""));
    }

    /**
     * Upon the Replay Button being clicked, recreate the Activity,
     * start up a new connection to the server, and display the
     * Game Screen.
     */
    @Click(R.id.buttonReplay)
    void startReplay()
    {
        //replayCursor = replayController.getCursor();
        //replayCursor.moveToPosition(0);


        busProvider.getEventBus().post(new StartReplayActivityEvent());
    }

    /**
     * Upon the Switch Server Button being clicked, toggle the server
     * currently selected for join.
     */
    @Click(R.id.buttonSwitchServer)
    void switchServer()
    {
        serverState = ((serverState -1) * -1);

        if(serverState == 0) {
            restHandler.setUrl(SC_SERVER);
            textView.setText(TV_SC_SERVER);
        }
        else {
            restHandler.setUrl(CS619_SERVER);
            textView.setText(TV_CS619_SERVER);
        }
    }

    /**
     * Upon the fireMissile button being clicked, tell the server
     * to fire a guided missile
     */
    @Click(R.id.buttonMissile)
    void fireMissile()
    {
        restHandler.fireMissileAsync(tankId);
    }

    //}
    //End Click Methods
}
