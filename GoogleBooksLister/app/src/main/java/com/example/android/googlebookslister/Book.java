package com.example.android.googlebookslister;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyler on 6/23/2017.
 */

public class Book {

    public static final String LOG_TAG = Book.class.getName();

    private String mJsonResponse;
    private String mTitle;
    private List<String> mAuthors;
    private String mPublisher;
    private String mPublishedDate;
    private String mDescription;
    private int mPageCount;
    private String mImageUrl;
    private String mInfoUrl;

    public Book(String jsonResponse) {
        mJsonResponse = jsonResponse;
        extractBookData();
    }

    public String getTitle() {
        return mTitle;
    }

    public List<String> getAuthors() {
        return mAuthors;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getPageCount() {
        return mPageCount;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getInfoUrl() {
        return mInfoUrl;
    }

    private void extractBookData() {
        try {
            JSONObject root = new JSONObject(mJsonResponse);
            JSONObject volumeInfo = root.getJSONObject("volumeInfo");

            // Get title
            mTitle = volumeInfo.getString("title");

            // Get authors
            mAuthors = new ArrayList<>();
            JSONArray authors = volumeInfo.getJSONArray("authors");
            for (int i = 0; i < authors.length(); i++) {
                mAuthors.add(authors.getString(i));
            }

            // Get publisher
            mPublisher = volumeInfo.getString("publisher");

            // Get published date
            mPublishedDate = volumeInfo.getString("publishedDate");

            // Get description
            mDescription = volumeInfo.getString("description");

            // Get page count
            mPageCount = volumeInfo.getInt("pageCount");

            // Get thumbnail image url
            JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
            mImageUrl = imageLinks.getString("smallThumbnail");

            // Get info url
            mInfoUrl = volumeInfo.getString("infoLink");

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem extracting book data", e);
        }
    }
}
