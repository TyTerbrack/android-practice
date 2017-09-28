package com.tylerterbrack.cantdecide.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tylerterbrack.cantdecide.data.MovieContract.MovieEntry;

/**
 * Created by Tyler on 7/20/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    // Increment the database version if the database schema is changed
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "catalog.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // SQL statement to create movies table
        String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_YEAR + " INTEGER, " +
                MovieEntry.COLUMN_GENRE + " TEXT, " +
                MovieEntry.COLUMN_WATCHED + " INTEGER NOT NULL DEFAULT 0)";

        // Execute the SQL statement
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // TODO: Add upgrade functionality
    }
}
