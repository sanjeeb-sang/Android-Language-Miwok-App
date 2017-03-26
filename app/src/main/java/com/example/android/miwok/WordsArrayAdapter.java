package com.example.android.miwok;

import android.app.LauncherActivity;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Sanjeeb on 12/28/2016.
 */

public class WordsArrayAdapter extends ArrayAdapter<Word> {

    /*
       2 argument constructor for WordsArrayAdapter
    */
    public WordsArrayAdapter(Context context, int resource, String nameOfXmlListResource) {
        super(context, resource);
        String xmlResource = nameOfXmlListResource;
    }

    /*
       3 argument constructor for WordsArrayAdapter
     */
    public WordsArrayAdapter(Context context, int resource, Word[] objects) {
        super(context, resource, objects);
    }

    /*
        3 argument constructor for WordsArrayAdapter
     */
    public WordsArrayAdapter(Context context, int resource, List<Word> objects) {
        super(context, resource, objects);
    }

    /*
        4 argument constructor for WordsArrayAdapter
     */
    public WordsArrayAdapter(Context context, int resource, int textViewResourceId, List<Word> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    /*
        @override the getView() method
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            listItemView = layoutInflater.inflate(R.layout.numbers_list_text_view, parent, false);
        }

        /*
            Getting the word corresponding to position which is a parameter of the
         getView() method using the getItem() method.
         */
        final Word currentWord = getItem(position);


        /*
            Finding the TextView in the numbers_list_text_view that is supposed to display the
         default translation of the language
         */
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_language_text_view);
        /*
            Setting the Text in the defaultTextView to be the defaultTranslation which we get from the
         currentWord Word object, using the getDefaultTranslation() method.
         */
        defaultTextView.setText(currentWord.getDefaultTranslation());

        /*
            Finding the TextView in the numbers_list_text_view that is supposed to display the
         default translation of the language
         */
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_language_text_view);
        /*
        Setting the Text in the defaultTextView to be the defaultTranslation which we get from the
         currentWord Word object, using the getDefaultTranslation() method.
         */
        miwokTextView.setText(currentWord.getMiwokTranslation());


        ImageView iconImageView = (ImageView) listItemView.findViewById(R.id.icon_image_view);

        if (currentWord.hasImage()) {
            iconImageView.setImageResource(currentWord.getIconId());
        } else {
            iconImageView.setVisibility(View.GONE);
        }
        return listItemView;
    }
}
