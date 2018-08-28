package edu.unh.cs.cs619.bulletzone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.w3c.dom.Text;

import edu.unh.cs.cs619.bulletzone.control.ReplayButtonHandler;
import edu.unh.cs.cs619.bulletzone.replay.ReplayController;
import edu.unh.cs.cs619.bulletzone.replay.ReplayPoller;

/**
 * @author luxingzeng
 * @since 2018/4/16
 * @version 2.0
 */
@EActivity(R.layout.activity_replay)
public class ReplayActivity extends Activity {

    private ReplayController controller;
    private Cursor cursor;

    @Bean
    ReplayButtonHandler buttonHandler;

    @Bean
    BattlefieldFacade battlefieldFacade;

    @NonConfigurationInstance
    @Bean
    ReplayPoller replayPoller;

    protected GridView gridView;
    protected long tankId = -1;
    private ReplayActivity activity;

    private static final String TANK_ID = "tankId";

    /**
     * Called when the activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = this;
        setContentView(R.layout.activity_replay);

        try {
            tankId = getIntent().getExtras().getLong(TANK_ID);
        }
        catch(NullPointerException e)
        {
            System.err.println("Failed to retrieve Player's tankId");
        }


        controller = ReplayController.getInstance(null);
        cursor = controller.getCursor();
        cursor.moveToPosition(0);
        Log.d("ReplayController", "I didn't break anything yet... " + cursor.getCount());

        Resources resources = getResources();
        battlefieldFacade.setResources(resources);

        //add Bundle stuff from second activity in Second Activity


    }

    /**
     * Called when the activity is destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        battlefieldFacade.unregister();
        replayPoller.stopPoll();
    }

    /**
     * Called after view is injected. Handles layouts and setting the grid view.
     */
    @AfterViews
    protected void afterViewInjection() {
        LinearLayout layout = (LinearLayout) this.findViewById(R.id.LinearLayout2);
        setLinearLayout(layout);

        startUp();
        SystemClock.sleep(500);

        gridView = (GridView) findViewById(R.id.gridViewReplay);
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

    /**
     * Called after the injection. Registers the battlefield facade.
     */
    @AfterInject
    void afterInject() {
        battlefieldFacade.register();
    }

    /**
     * Similar to join for ClientActivity. Called to initialize vital parts of program.
     */
    void startUp()
    {
        //textBox = findViewById(R.id.replayText);
        buttonHandler.setTextBox((TextView)findViewById(R.id.replayText));
        battlefieldFacade.setPlayerID(tankId);
        battlefieldFacade.setActivity(activity);
        replayPoller.doPoll();

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
}

