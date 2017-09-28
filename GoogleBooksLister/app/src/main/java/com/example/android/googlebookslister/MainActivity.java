package com.example.android.googlebookslister;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager
        .LoaderCallbacks<List<Book>> {

    private static final String BASE_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final int BOOK_LOADER_ID = 1;
    private static final int MAX_RESULTS = 15;

    private BookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set adapter on list view
        ListView bookListView = (ListView) findViewById(R.id.list);
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);

        // Configure search button
        Button searchButton = (Button) findViewById(R.id.search_button);

        final LoaderManager loaderManager = getLoaderManager();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Check for internet connection
                loaderManager.destroyLoader(BOOK_LOADER_ID);
                loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        EditText searchBar = (EditText) findViewById(R.id.search_bar);
        String searchTerms = searchBar.getText().toString();

        return new BookLoader(this, QueryUtils.buildSearchUrl(BASE_REQUEST_URL, searchTerms,
                MAX_RESULTS));
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // TODO: Loading indicator and empty view
        mAdapter.clear();
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}
