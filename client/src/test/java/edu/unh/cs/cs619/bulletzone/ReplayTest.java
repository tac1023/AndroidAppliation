package edu.unh.cs.cs619.bulletzone;


import android.content.Context;
import android.database.Cursor;
import android.widget.GridView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Scanner;

import edu.unh.cs.cs619.bulletzone.replay.ReplayController;
import edu.unh.cs.cs619.bulletzone.util.GridWrapper;

/**
 * Created by Jason on 4/20/2018.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ReplayTest {
    private ReplayController rc;
    private int[][] grid;

    @Before
    public void start() {
        rc = ReplayController.getInstance(RuntimeEnvironment.application);
 //       if( rc.getCursor().getCount() != 0 )
//            rc.clearEntries();
        grid = new int[16][16];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                grid[i][j] = 0;
            }
        }
    }


    @Test
    public void databaseInsertsElements() {

        rc.putEntry(grid, grid, true, System.currentTimeMillis());
        Assert.assertEquals(1, rc.getCursor().getCount());
        rc.putEntry(grid, grid, true, System.currentTimeMillis());
        Assert.assertEquals(2, rc.getCursor().getCount());
        rc.putEntry(grid, grid, true, System.currentTimeMillis());
        Assert.assertEquals(3, rc.getCursor().getCount());
    }


    @Test
    public void databaseCanProduceCorrectOutput() {

        rc.putEntry(grid, grid, true, System.currentTimeMillis());
        Assert.assertEquals(1, rc.getCursor().getCount());
        rc.putEntry(grid, grid, true, System.currentTimeMillis());
        Assert.assertEquals(2, rc.getCursor().getCount());
        rc.putEntry(grid, grid, true, System.currentTimeMillis());
        Assert.assertEquals(3, rc.getCursor().getCount());

        Cursor cur = rc.getCursor();

        while( cur.moveToNext() ){
            Assert.assertEquals(cur.getString(2), toString(grid));
        }

    }
/*
    @Test
    public void databaseCreatesCorrectGridWrapper() {
        int[][] arr = grid;
        grid[0][0] = 1000000;
        grid[1][1] = 1000000;

        GridWrapper gw = new GridWrapper(arr,grid,true);
        rc.putEntry(arr,grid,true,System.currentTimeMillis());
        Cursor cursor = rc.getCursor();
        Assert.assertTrue(cursor.moveToPosition(0));
        Assert.assertNotEquals(cursor.getString(2), toString(grid));
        GridWrapper curGW = new GridWrapper( to2DArray(cursor.getString(2)),
                                                to2DArray(cursor.getString(3)),
                                                    true );
        Assert.assertEquals(gw, curGW);

    }*/


    private String toString( int[][] arr ) {
        String str = "";
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                str += arr[i][j] + " ";
            }
        }
        return str.trim();
    }

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
