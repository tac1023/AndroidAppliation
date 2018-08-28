package edu.unh.cs.cs619.bulletzone.replay;

import android.provider.BaseColumns;

/**
 * @author Jason
 * @version 1.0
 * @since ???
 */
public final class ReplayContract {
    private ReplayContract() {}

    /**
     * This holds the informtion for the SQLite database to process.
     */
    public static class ReplayEntry implements BaseColumns {
        public static final String TABLE_NAME = "replay";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_GRID = "grid";
        public static final String COLUMN_TANK = "tank"; //actually tankgrid
        public static final String COLUMN_LIVE = "live";

    }
}
