package com.example.android.googlebookslister;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Tyler on 6/23/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(@NonNull Context context, List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Create new list item if none are available
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,
                    false);
        }

        // Get the current book
        Book currentBook = getItem(position);

        // Set title
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.book_title);
        titleTextView.setText(currentBook.getTitle());

        // Set authors
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author);
        List authors = currentBook.getAuthors();
        String authorsString = "";
        if (authors.size() > 1) {
            for (int i = 0; i < authors.size() - 1; i++) {
                authorsString += authors.get(i) + ", ";
            }

        }
        authorsString += authors.get(authors.size() - 1);
        authorTextView.setText(authorsString);

        // Set publisher
        TextView publisherTextView = (TextView) listItemView.findViewById(R.id.publisher);
        publisherTextView.setText(currentBook.getPublisher());

        // Set published date
        TextView publishedDateTextView = (TextView) listItemView.findViewById(R.id.published_date);
        publishedDateTextView.setText(currentBook.getPublishedDate());

        // Set description
        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.description);
        descriptionTextView.setText(currentBook.getDescription());

        // Set page count
        TextView pageCountTextView = (TextView) listItemView.findViewById(R.id.page_count);
        pageCountTextView.setText(Integer.toString(currentBook.getPageCount()));

        // Set image
        ImageView thumbnailImageView = (ImageView) listItemView.findViewById(R.id.thumbnail);
        Bitmap bm =
        thumbnailImageView.setImageBitmap();

        return listItemView;
    }
}
