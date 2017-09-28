package com.example.android.googlebookslister;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Tyler on 6/23/2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        List<Book> books = null;
        if (mUrl.length() > 0 && mUrl != null) {
            books = QueryUtils.fetchBookData(mUrl);
        }
        return books;
    }
}
