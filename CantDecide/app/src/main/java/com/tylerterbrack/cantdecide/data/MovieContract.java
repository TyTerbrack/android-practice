package com.tylerterbrack.cantdecide.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Tyler on 7/20/2017.
 */

public final class MovieContract {

    // Base URI constants
    public static final String CONTENT_AUTHORITY = "com.tylerterbrack.cantdecide";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    // Private constructor to prevent accidental instantiation
    private MovieContract() {}

    /* Inner class that defines table contents */
    public static class MovieEntry implements BaseColumns {

        // Content URI
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);

        // MIME types
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        // Table name
        public static final String TABLE_NAME = "movies";

        // Column names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_GENRE = "genre";
        public static final String COLUMN_WATCHED = "watched";

        // Possible watched values
        public static final int WATCHED_TRUE = 1;
        public static final int WATCHED_FALSE = 0;

        public static boolean isValidWatchedValue(int watched) {
            switch (watched) {
                case WATCHED_FALSE:
                case WATCHED_TRUE:
                    return true;
                default:
                    return false;
            }
        }
    }
}
