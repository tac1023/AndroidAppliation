package edu.unh.cs.cs619.bulletzone;
import android.app.Activity;
import android.content.Context;

import edu.unh.cs.cs619.bulletzone.events.BusProvider;
import edu.unh.cs.cs619.bulletzone.replay.ReplayController;
import edu.unh.cs.cs619.bulletzone.rest.GridUpdateEvent;
import edu.unh.cs.cs619.bulletzone.ui.Battlefield;
import edu.unh.cs.cs619.bulletzone.ui.FieldEntity;
import edu.unh.cs.cs619.bulletzone.ui.FieldEntityFactory;
import edu.unh.cs.cs619.bulletzone.ui.GridAdapter;
import edu.unh.cs.cs619.bulletzone.util.GridWrapper;

import android.content.res.Resources;
import android.database.Cursor;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * This Facade Class provides a buffer between the user interaction and the
 * underlying logic. The Client Activity is responsible for handling user
 * interaction. This class will handle putting objects such as Tanks and
 * Walls in the correct location and displaying them correctly on the screen.
 *
 * @author Tyler Currier
 * @version 3.0
 * @since 4/17/2018
 */
@EBean
public class BattlefieldFacade {

    //Class Variables:
    @Bean
    protected BusProvider busProvider;

    @Bean
    protected GridAdapter gridAdapter;



    private GridView gridView;
    private TextView textBox;
    private TextView textBox2;
    private TextView healthText;
    private Battlefield battlefield;
    private FieldEntityFactory factory;
    private long playerID = -1; //TankID of the Player
    private byte currentTankDirection;
    private byte currentSoldierDirection;
    private Activity activity;
    private ReplayController replayController;
    private boolean isSoldier = false;
    private boolean isReplay;
    //End Class Variables

    //Final Variables:

    //End Final Variables

    /**
     * This is the constructor for Battlefield Facade. It is private because it
     * is being implemented as a Singleton Class.
     */
    BattlefieldFacade()
    {
        battlefield = new Battlefield();
        factory = FieldEntityFactory.getInstance(playerID);
    }


    /**
     * Otto has a limitation (as per design) that it will only find
     * methods on the immediate class type. As a result, if at runtime this instance
     * actually points to a subclass implementation, the methods registered in this class will
     * not be found. This immediately becomes a problem when using the AndroidAnnotations
     * framework as it always produces a subclass of annotated classes.
     *
     * To get around the class hierarchy limitation, one can use a separate anonymous class to
     * handle the events.
     */
    private Object facadeGridEventHandler = new Object()
    {
        @Subscribe
        public void onUpdateGrid(final GridUpdateEvent event) {
            //Log.d("FACADE_POLLER", "Event Received");
            isReplay = event.isReplay;
            if(!event.isReplay) {
                replayController.putEntry(event.gw.getGrid(), event.gw.getTankGrid(), event.gw.isAlive(),
                        System.currentTimeMillis());
                setText(event.gw.getTankHealth(), event.gw.getSoldierHealth());
                setTankHealthBar(event.gw.getTankHealth());
                setSoldierHealthBar(event.gw.getSoldierHealth());
            }
            updateBattlefield(event.gw);
            updateGridAdapter();
        }
    };

    //Public Methods:

    /**
     * Get the TankID associated with this player.
     *
     * @return The Player's TankID
     */
    public long getPlayerID()
    {
        return playerID;
    }

    /**
     * This method is responsible for taking a GridView from the
     * ClientActivity and setting it's adapter to be the gridAdapter
     * generated here. Initializing a GridView in BattlefieldFacade
     * proved to be troublesome.
     *
     * @param gv The GridView from ClientActivity
     */
    void setGridViewAdapter(GridView gv)
    {
        gridView = gv;
        gridView.setAdapter(gridAdapter);
    }

    /**
     * Set the player ID
     *
     * @param id player ID
     */
    void setPlayerID(long id)
    {
        playerID = id;
        factory.setPlayerID(id);
    }

    /**
     * Update the battlefield with the new information from the
     * server. Update health values for Soldier and Tank
     *
     * @param gw  The new grid data
     */
    private void updateBattlefield(final GridWrapper gw) //UPDATE FOR LAYER
    {

        //updateBattlefield(gw, false);
        String healthString = "Soldier/Tank Health\n";
        //this.isReplay = isReplay;
        boolean dead = true;
        isSoldier = false;
        int[][] newGrid = gw.getGrid();
        int[][] newTankGrid = gw.getTankGrid();
        for(int i = 0; i < 16; i++)
        {
            for(int j = 0; j < 16; j++)
            {
                //Log.d("Loop amount", "" + (i+j));
                FieldEntity temp = factory.makeEntity(newGrid[j][i], j, i);
                FieldEntity temp2 = factory.makeEntity(newTankGrid[j][i], j, i);
                battlefield.setFieldEntity(j, i, temp);
                battlefield.setTankEntity(j, i, temp2);

                if(newTankGrid[j][i]/10000000 != 0 ) {
                        healthString += (newTankGrid[j][i]/10000)%1000 + " tank: " +
                                battlefield.getTankEntity(j, i).getLife() + "\n";
                        if ((newTankGrid[j][i]/10000)%1000 == playerID)
                            currentTankDirection = (byte)(battlefield.getTankEntity(j, i).getDirection());

                }


                if(newTankGrid[j][i]/1000000 == 1 ) {
                    healthString += (newTankGrid[j][i] / 1000) % 1000 + " soldier: " +
                            battlefield.getTankEntity(j, i).getLife() + "\n";

                    if ((newTankGrid[j][i] / 1000) % 1000 == playerID) {
                        isSoldier = true;
                        currentSoldierDirection = (byte) (battlefield.getTankEntity(j, i).getDirection());
                    }
                }
            }
        }
        if(!isReplay)
            healthText.setText(healthString);
    }

    /**
     * Accept a TextView from ClientActivity and use it to display
     * the player's tank and soldier's health.
     *
     * @param t TextView from ClientActivity
     */
    void setTextBox( TextView t , TextView t2, TextView healthText) {
        textBox = t;
        textBox2 = t2;
        this.healthText = healthText;
    }

    /**
     * Set the contents of the text box to display the current healths of
     * the player's tank and soldier.
     *
     * @param tank health of tank
     * @param sold health of soldier
     */
    private void setText( long tank, long sold ) {
        textBox.setText("Tank Health: " + tank);
        textBox2.setText("Soldier Health: " + sold);
        //Log.d("SetText method", "" + tankHealth);
        //.setText("Tank Health: " + tank + "\n" +
            //    "Soldier Health: " + sold);
    }
    /**
     * Get the current direction of the player tank.
     *
     * @return direction
     */
    public byte getCurrentDirection()
    {
        if(isSoldier) {
            Log.d("SOLDIER DIRECTION", "Used soldier Direction");
            return currentSoldierDirection;
        }
        return currentTankDirection;
    }

    /**
     * Update the GridAdapter with the latest Battlefield.
     */
    private void updateGridAdapter()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                gridAdapter.updateList(battlefield);
            }
        }).start();
        if(gridView != null)
            gridView.invalidateViews();
    }

    /**
     * Register the event handler with the EventBus to receive updates
     * from the Poller.
     */
    void register()
    {
        busProvider.getEventBus().register(facadeGridEventHandler);
    }

    /**
     * Unregister the event handler from the EventBus
     */
    void unregister()
    {
        busProvider.getEventBus().unregister(facadeGridEventHandler);
    }

    /**
     * Accept a Resources object form ClientActivity because that is the only place
     * it can be instantiated and it is needed in GridAdapter
     *
     * @param resources Resources object from ClientActivity
     */
    public void setResources(Resources resources)
    {
        gridAdapter.setResources(resources);
    }

    /**
     * gets the cursor of the replay controller.
     *
     * @return Cursor
     */
    public Cursor getReplayCursor() {
        return replayController.getCursor();
    }

    /**
     * Sets the replay controller given a context.
     *
     * @param cont context
     */
    void setReplayController( Context cont ){
        replayController = ReplayController.getInstance(cont);
        //replayController.clearEntries();
    }

    /**
     * Sets the replay controller given a replay controller.
     *
     * @param rc replay controller
     */
    void setReplayController( ReplayController rc ) {
        replayController = rc;
        replayController.clearEntries();
    }

    /**
     * Resets the cursor associated with the replay controller.
     */
    public void resetReplay() {
        if( replayController != null)
            replayController.clearEntries();
    }

    /**
     * Give BattlefieldFacade a reference to the MainActivity
     *
     * @param activity reverence to activity
     */
    public void setActivity(Activity activity)
    {
        this.activity = activity;
    }


    //End Public Methods

    //Private Methods:

    /**
     * Set the resource image for the tank health bar the corresponds to the
     * current tank health
     *
     * @param tankHealth health of the tank
     */
    private void setTankHealthBar(int tankHealth) {
        if (activity == null)
            return;

        if(tankHealth > 80)
        {
            activity.findViewById(R.id.health).setBackgroundResource(R.drawable.healthblood);
        }
        else if(tankHealth > 60)
        {
            activity.findViewById(R.id.health).setBackgroundResource(R.drawable.blood4);
        }
        else if(tankHealth > 40)
        {
            activity.findViewById(R.id.health).setBackgroundResource(R.drawable.blood3);
        }
        else if(tankHealth > 20)
        {
            activity.findViewById(R.id.health).setBackgroundResource(R.drawable.blood2);
        }
        else if(tankHealth > 0)
        {
            activity.findViewById(R.id.health).setBackgroundResource(R.drawable.blood1);
        }
        else if(tankHealth == 0)
        {
            activity.findViewById(R.id.health).setBackgroundResource(R.drawable.died);
        }
    }

    /**
     * Set the resource image for the soldier health bar that corresponds to the
     * current soldier heath
     *
     * @param soldHealth health of soldier
     */
    private void setSoldierHealthBar(long soldHealth)
    {
        if(activity == null)
            return;

        if(soldHealth > 20)
        {
            activity.findViewById(R.id.health2).setBackgroundResource(R.drawable.healthblood);
        }
        else if(soldHealth > 15)
        {
            activity.findViewById(R.id.health2).setBackgroundResource(R.drawable.blood4);
        }
        else if(soldHealth > 10)
        {
            activity.findViewById(R.id.health2).setBackgroundResource(R.drawable.blood3);
        }
        else if(soldHealth > 5)
        {
            activity.findViewById(R.id.health2).setBackgroundResource(R.drawable.blood2);
        }
        else if(soldHealth > 0)
        {
            activity.findViewById(R.id.health2).setBackgroundResource(R.drawable.blood1);
        }
        else if(soldHealth == 0)
        {
            activity.findViewById(R.id.health2).setBackgroundResource(R.drawable.died);
        }
    }
    //End Private Methods


}
