package edu.unh.cs.cs619.bulletzone.control;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;
import org.w3c.dom.Text;

import edu.unh.cs.cs619.bulletzone.R;
import edu.unh.cs.cs619.bulletzone.replay.ReplayPoller;

/**
 * Created by Jason Vettese on 4/19/2018.
 */
@EBean
public class ReplayButtonHandler {

    @Bean
    ReplayPoller replayPoller;

    private Context context;
    private TextView textBox;

    /**
     * Constructor that is passed a context.
     * @param context
     */
    public ReplayButtonHandler(Context context) {
        this.context = context;
    }

    /**
     * Sets the textbox at the top of view.
     * @param text
     */
    public void setTextBox(TextView text ) {
        textBox = text;
    }

    /**
     * This method deals with pausing or playing the replay.
     */
    @Click(R.id.buttonPlay)
    protected void playOrPause() {
        if( replayPoller.isPolling() ) {
            textBox.setText("Paused");
        }
        else {
            if( replayPoller.getSpeed() == 100 ) textBox.setText("1x");
            if( replayPoller.getSpeed() == 50 ) textBox.setText("2x");
            if( replayPoller.getSpeed() == 25 ) textBox.setText("3x");
            if( replayPoller.getSpeed() == 12 ) textBox.setText("4x");
        }
        if( replayPoller.isPolling() ) {
            replayPoller.pausePoll();
        }
        else {
            replayPoller.doPoll();
        }

    }

    /**
     * Restarts the replay.
     */
    @Click(R.id.buttonRestart)
    void restart(){
        if(!replayPoller.isPolling())
            textBox.setText("Restarted - Press Play");

        //Log.d("ReplayButtonHandler", "restart pressed");
        replayPoller.setCursorPosition(0);

    }

    /**
     * Replay at normal speed.
     */
    @Click(R.id.button1x)
    void oneTimeSpeed(){
        replayPoller.setReplaySpeed(100);
        if(replayPoller.isPolling())
            textBox.setText("1x");
    }

    /**
     * Replay at tw times speed.
     */
    @Click(R.id.button2x)
    void twoTimeSpeed(){

        replayPoller.setReplaySpeed(50);
        if(replayPoller.isPolling())
            textBox.setText("2x");

    }

    /**
     * Replay at three times speed.
     */
    @Click(R.id.button3x)
    void threeTimeSpeed(){

        replayPoller.setReplaySpeed(25);
        if(replayPoller.isPolling())
            textBox.setText("3x");
    }

    /**
     * Replay at four times speed.
     */
    @Click(R.id.button4x)
    void fourTimeSpeed(){
        replayPoller.setReplaySpeed(12);
        if(replayPoller.isPolling())
            textBox.setText("4x");
    }


}
