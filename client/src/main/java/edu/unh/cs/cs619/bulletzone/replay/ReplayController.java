package edu.unh.cs.cs619.bulletzone.replay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

/**
 *  Handles most of the processing of the SQLite database used.
 * @author Jason Vettese
 * @since 4/15/2018
 * @version 1.0
 */
public class ReplayController {

    private volatile static ReplayController instance = null;
    Context context;
    ReplayDbHelper rDbHelper;
    SQLiteDatabase writeDatabase;
    SQLiteDatabase readDatabase;
    //LinkedList<Long> list;
    Thread charpentier;

    /**
     * Private constructor for singleton purposes.
     * @param cont Context
     */
    private ReplayController(Context cont) {
        context = cont;
        rDbHelper = new ReplayDbHelper(context);
        writeDatabase = rDbHelper.getWritableDatabase();
        readDatabase = rDbHelper.getReadableDatabase();
        //list = new LinkedList();
    }

    /**
     * Used to get instance of controller since the controller is a singleton.
     * @param cont
     * @return a replay controller
     */
    public static ReplayController getInstance(Context cont) {
        if(instance == null) {
            synchronized (ReplayController.class){
                if(instance == null)
                    //Log.d("ReplayController", "Replay Controller was created here");
                    instance = new ReplayController(cont);
            }
        }
        return instance;
    }

    /**
     * Method used to insert a move into a database.
     * @param grid
     * @param tankGrid
     * @param time
     */
   public void putEntry( final int[][] grid, final int[][] tankGrid, final boolean isAlive, final long time ){
       synchronized (ReplayController.this ) {
           final String sGrid = toString(grid);
           final String sTankGrid = toString(tankGrid);
           charpentier = new Thread(new Runnable() {
               public void run() {

                   ContentValues vals = new ContentValues();
                   vals.put(ReplayContract.ReplayEntry.COLUMN_TIME, time);
                   vals.put(ReplayContract.ReplayEntry.COLUMN_GRID, ReplayController.toString(grid));
                   vals.put(ReplayContract.ReplayEntry.COLUMN_TANK, ReplayController.toString(tankGrid));
                   vals.put(ReplayContract.ReplayEntry.COLUMN_LIVE, isAlive);

                   long newRowId = writeDatabase.insert(ReplayContract.ReplayEntry.TABLE_NAME, null, vals);

               //Log.d("ReplayController", "record stored in database at " + newRowId);
               //list.add(newRowId);
               }
           });
           charpentier.start();
       }
   }

    /**
     * Gets a Cursor object containing the full database sorted with time ascending LIFO.
     *
     * @return a cursor object
     */
    public Cursor getCursor() {
        //Log.d("ReplayController", "getCursor was called here");
        synchronized (ReplayController.this) {
            if (charpentier != null) {
                try {
                    charpentier.join();
                } catch (InterruptedException e) {
                    return null;
                }
            }
            return readDatabase.query(
                    ReplayContract.ReplayEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    (ReplayContract.ReplayEntry.COLUMN_TIME + " ASC") //ascending time order
            );
        }
    }


    /**
     * Clears the entries in the current database. Called when joining a new game.
     */
    public void clearEntries() {
        synchronized (ReplayController.this) {
            SQLiteDatabase db = rDbHelper.getWritableDatabase();
            if (db == null) return;
            rDbHelper.onDelete(db);
            rDbHelper.onCreate(db);
        }
    }

    /**
     * Gets the context associated with object.
     * @return context
     */
    public Context getContext() {
        return context;
    }
    //End Public Methods

    //Private Methods


    /**
     * Conerts a 2D array into a String
     * @param arr
     * @return string
     */
    private static String toString( int[][] arr ){
        String str = "";
        for( int i = 0; i < 16; i++ ) {
            for( int j = 0; j < 16; j++ ){
                str += arr[i][j] + " ";
            }
        }
        return str.trim();
    }

    //End of private methods
}
