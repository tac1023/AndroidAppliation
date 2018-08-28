package edu.unh.cs.cs619.bulletzone;


import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import edu.unh.cs.cs619.bulletzone.replay.ReplayController;
import edu.unh.cs.cs619.bulletzone.ui.Battlefield;
import edu.unh.cs.cs619.bulletzone.util.GridWrapper;

import static org.junit.Assert.*;
/**
 * Created by Jason Vettese on 4/16/2018.
 */
@RunWith(AndroidJUnit4.class)
public class ReplayInstrumentedTest {

    Context context = null;
    int[][] grid = null;


    @Before
    public void setUp() {
        if( context == null )
            context = InstrumentationRegistry.getTargetContext();
        if( grid == null ) {
            grid = new int[16][16];
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    grid[i][j] = 0;
                }
            }
        }

    }

    @Test
    public void SQLiteDatabaseBuiltCorrectly() throws InterruptedException{
        ReplayController rc = ReplayController.getInstance(context);
        rc.clearEntries();
        final String GRID = toString(grid);

        rc.putEntry(grid,grid,true, System.currentTimeMillis());
        wait(1000);
        Assert.assertEquals(1, rc.getCursor().getCount());
        /*new Thread(new Runnable() {
            public void run() {

            }
        }).start();
        for( int i = 1; i <= 5; i++ ) {
            final int I = i;
            rc.putEntry(grid, grid, true, System.currentTimeMillis());
            new Thread(new Runnable() {
                public void run() {
                    Assert.assertEquals(I, RC.getCursor().getCount());
                }
            }).start();

        }
        new Thread(new Runnable() {
            public void run() {
                Cursor cursor = RC.getCursor();
                cursor.moveToPosition(0);
                //Log.d("DEBUG", cursor.getString(2));
                //Log.d("GRID TO STRING", toString(grid));
                Assert.assertEquals(GRID, cursor.getString(2));
            }}).start();*/
    }

    private String toString( int[][] arr ){
        String str = "";
        for( int i = 0; i < 16; i++ ) {
            for( int j = 0; j < 16; j++ ){
                str += arr[i][j] + " ";
            }
        }
        return str.trim();
    }
}
