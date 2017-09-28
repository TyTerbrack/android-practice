package com.example.android.miwok;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tyler on 6/10/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceId;

    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourceId) {
        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being used, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the word object located at this position in the list
        Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml file with the ID miwok_text_view
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        // Get the miwok translation from the current word object and set this text on the miwok TextView
        miwokTextView.setText(currentWord.getMiwokTranslation());

        // Find the TextView in the list_item.xml file with the ID default_text_view
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        // Get the default translation from the current word object and set this text on the default TextView
        defaultTextView.setText(currentWord.getDefaultTranslation());

        // Find the ImageView in the list_item.xml file with the ID image
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);

        if (currentWord.hasImage()) {
            // Get the image resource id from the current word object and set this resource id on the ImageView
            imageView.setImageResource(currentWord.getImageResourceId());
            // Make ImageView visible
            imageView.setVisibility(View.VISIBLE);
        } else {
            // Make ImageView not visible
            imageView.setVisibility(View.GONE);
        }

        // Get theme color
        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find color that resource id maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set background color
        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
