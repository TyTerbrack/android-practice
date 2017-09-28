package com.tylerterbrack.cantdecide.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tylerterbrack.cantdecide.data.MovieContract.MovieEntry;

/**
 * Created by Tyler on 7/20/2017.
 */

public class MovieProvider extends ContentProvider {

    public static final String LOG_TAG = MovieProvider.class.getSimpleName();

    // Database helper object
    private MovieDbHelper mDbHelper;

    // UriMatcher codes
    private static final int MOVIES = 1;
    private static final int MOVIE_ID = 2;

    // Create a UriMatcher object
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer
    static {
        // Add paths to UriMatcher
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES + "/#",
                MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    /* Retrieve data from provider */
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                         String sortOrder) {
        // Get readable database from helper
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // Create cursor object
        Cursor cursor;

        // Choose type of query based on incoming URI
        switch (sUriMatcher.match(uri)) {
            case MOVIES:        // Query whole movies table
                cursor = database.query(MovieContract.MovieEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case MOVIE_ID:      // Query a specific movie
                // Extract ID from URI
                selection = MovieEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(MovieEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unkown URI " + uri);
        }

        return cursor;
    }

    /* Return MIME type corresponding to content URI */
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return MovieEntry.CONTENT_LIST_TYPE;
            case MOVIE_ID:
                return MovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    /* Insert a new row into provider */
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return insertMovie(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /* Delete rows from provider */
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:        // Delete whole movies table
                rowsDeleted = database.delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_ID:      // Delete a specific movie
                // Extract ID from URI
                selection = MovieEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};

                rowsDeleted = database.delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // TODO: Notify listeners

        return rowsDeleted;
    }

    /* Update existing rows in provider */
    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:        // Update whole movies table
                return updateMovie(uri, contentValues, selection, selectionArgs);
            case MOVIE_ID:      // Update a specific movie
                // Extract ID from URI
                selection = MovieEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};

                return updateMovie(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private Uri insertMovie(Uri uri, ContentValues values) {
        // Check that the title is not null
        String title = values.getAsString(MovieEntry.COLUMN_TITLE);
        if (title == null) {
            throw new IllegalArgumentException("Movie requires a title");
        }

        // Check that watched has a valid value
        Integer watched = values.getAsInteger(MovieEntry.COLUMN_WATCHED);
        if (watched == null || !MovieEntry.isValidWatchedValue(watched)) {
            throw new IllegalArgumentException("Movie requires a valid watched value");
        }

        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert new movie with given values
        long id = database.insert(MovieEntry.TABLE_NAME, null, values);

        // Check if insert failed
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // TODO: Notify listeners

        return ContentUris.withAppendedId(uri, id);
    }

    private int updateMovie(Uri uri, ContentValues values, String selection,
                            String[] selectionArgs) {
        // Check that the title is not null
        String title = values.getAsString(MovieEntry.COLUMN_TITLE);
        if (title == null) {
            throw new IllegalArgumentException("Movie requires a title");
        }

        // Check that watched has a valid value
        Integer watched = values.getAsInteger(MovieEntry.COLUMN_WATCHED);
        if (watched == null || !MovieEntry.isValidWatchedValue(watched)) {
            throw new IllegalArgumentException("Movie requires a valid watched value");
        }

        // Check if there are values to update
        if (values.size() == 0) {
            return 0;
        }

        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform update and get number of rows affected
        int rowsUpdated = database.update(MovieEntry.TABLE_NAME, values, selection, selectionArgs);

        // TODO: Notify listeners

        return rowsUpdated;
    }
}
