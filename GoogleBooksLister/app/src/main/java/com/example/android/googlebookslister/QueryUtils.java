package com.example.android.googlebookslister;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyler on 6/23/2017.
 */

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getName();

    // Private constructor because there is no need to instantiate QueryUtils
    private QueryUtils() {
    }


    /* Public methods */

    public static List<Book> fetchBookData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        List<Book> books = null;
        try {
            jsonResponse = makeHttpRequest(url);
            books = extractBooksFromJson(jsonResponse);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making HTTP request", e);
        }

        return books;
    }

    public static String buildSearchUrl(String baseUrl, String searchTerms, int maxResults) {
        String url = baseUrl;

        if (searchTerms.contains(" ")) {
            String parts[] = searchTerms.split(" ");

            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append("?q=");
            for (int i = 0; i < parts.length - 1; i++) {
                urlBuilder.append(parts[i] + "+");
            }
            urlBuilder.append(parts[parts.length - 1]);
            urlBuilder.append("&maxresults=" + maxResults);

            url += urlBuilder.toString();
        } else {
            url += "?q=" + searchTerms + "&maxresults=" + maxResults;
        }

        return url;
    }


    /* Private helper methods */

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url != null) {
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } else {
                    Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem retrieving JSON results", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset
                    .forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    private static List<Book> extractBooksFromJson(String jsonResponse) throws IOException{
        List<Book> books = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray booksArray = root.getJSONArray("items");

            for (int i = 0; i < booksArray.length(); i++) {
                JSONObject currentBook = booksArray.getJSONObject(i);
                String url = currentBook.getString("selfLink");

                String bookJsonResponse = makeHttpRequest(createUrl(url));
                books.add(new Book(bookJsonResponse));
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON results", e);
        }

        return books;
    }
}
