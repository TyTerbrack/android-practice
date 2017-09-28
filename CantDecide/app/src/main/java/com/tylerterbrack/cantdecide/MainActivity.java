package com.tylerterbrack.cantdecide;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.tylerterbrack.cantdecide.data.MovieContract.MovieEntry;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MOVIE_LOADER = 0;

    MovieCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find ListView, setup adapter, and attach adapter to ListView
        ListView list = (ListView) findViewById(R.id.list);
        mCursorAdapter = new MovieCursorAdapter(this, null);
        list.setAdapter(mCursorAdapter);

        // Prepare the loader
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Specify which columns will be use after the query
        String[] projection = {
                MovieEntry._ID,
                MovieEntry.COLUMN_TITLE,
                MovieEntry.COLUMN_YEAR,
                MovieEntry.COLUMN_GENRE
        };

        return new CursorLoader(
                this,                       // Parent activity context
                MovieEntry.CONTENT_URI,     // Provider content URI to query
                projection,                 // Columns to use
                null,                       // No selection clause
                null,                       // No selection arguments
                null                        // Default sort order
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
