package com.tylerterbrack.cantdecide;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.tylerterbrack.cantdecide.data.MovieContract.MovieEntry;

/**
 * Created by Tyler on 7/20/2017.
 */

public class MovieCursorAdapter extends CursorAdapter {

    public MovieCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        // Inflate and return a new view
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        TextView tvYear = (TextView) view.findViewById(R.id.year);
        TextView tvGenre = (TextView) view.findViewById(R.id.genre);

        // Extract properties from cursor
        String title = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_TITLE));
        int year = cursor.getInt(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_YEAR));
        String genre = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_GENRE));

        // Populate fields with extracted properties
        // TODO: Check if there is a year / genre
        tvTitle.setText(title);
        tvYear.setText(String.valueOf(year));
        tvGenre.setText(genre);
    }
}
