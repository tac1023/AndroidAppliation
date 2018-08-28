package edu.unh.cs.cs619.bulletzone.replay;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import edu.unh.cs.cs619.bulletzone.Replay.ReplayContract;

/**
 * Helper that creates a SQLite database.
 *
 * @author Jason Vettese
 * @since 4/15/2018.
 * @version 1.0
 */
public class ReplayDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ReplayEntry.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ReplayContract.ReplayEntry.TABLE_NAME + " (" +
                    ReplayContract.ReplayEntry._ID + " INTEGER PRIMARY KEY," +
                    ReplayContract.ReplayEntry.COLUMN_TIME + " TIME," +
                    ReplayContract.ReplayEntry.COLUMN_GRID + " TEXT," +
                    ReplayContract.ReplayEntry.COLUMN_TANK + " TEXT," +
                    ReplayContract.ReplayEntry.COLUMN_LIVE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ReplayContract.ReplayEntry.TABLE_NAME;

    /**
     * Calls super contructor to create the database.
     *
     * @param context
     */
    public ReplayDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Makes SQL call to initialize the database.
     * @param db
     */
    public void onCreate(SQLiteDatabase db ){
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * Refreshes the database to handle newer version.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Reverts the upgrade to the previous version.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Deletes all entries in the current database.
     * @param db
     */
    public void onDelete(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }

}
