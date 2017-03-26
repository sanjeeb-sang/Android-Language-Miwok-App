package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.content.Context.AUDIO_SERVICE;
import static com.example.android.miwok.R.mipmap.ic_launcher;

public class NumbersFragment extends Fragment {

    private MediaPlayer mMediaPlayer;

    private int mAudioFocus;
    private AudioManager mAudioManager;
    /*
            Creating an instance of a AudioManager class named audioManager
            and requesting for AudioFocus and storing it in audioFocus
    */
    private final AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if (i == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            } else if (i == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                mMediaPlayer.pause();
            } else if (i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.setVolume(AudioManager.ADJUST_LOWER, AudioManager.ADJUST_LOWER);
            }
        }
    };


    public NumbersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.activity_numbers, container, false);

        // icon picture
        int icon = ic_launcher;
        // Creating an array words that contains 10 Word objects
        final ArrayList<Word> words = new ArrayList<Word>();

        // Adding 10 new Word objects to the words ArrayList
        words.add(new Word("one", "lotti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));

        /*
            itemsList is the adapter for our @resource listView
         */
        WordsArrayAdapter itemsList = new WordsArrayAdapter(getActivity(), R.layout.numbers_list_text_view, words);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);


        // Creating a ListView object listView that corresponds to the xml resource file @resource list
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(itemsList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word currentWord = words.get(i);
                mMediaPlayer = MediaPlayer.create(getActivity(), currentWord.getMusicResId());
                mAudioFocus = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                if (mAudioFocus != AudioManager.AUDIOFOCUS_LOSS) {
                    // If we have the AudioFocus then start the MediaPlayer
                    mMediaPlayer.start();
                }
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                        /*
                        since the MediaPlayer has completed so now we release mMediaPlayer so that it does not take
                        much battery and storage and abandoning AudioFocus. This method will release mMediaplayer and then set its value to
                        null
                         */
                        mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
                        releaseMediaPlayer();
                    }
                });
            }
        });
        return rootView;

    }



    /*
       Function that will relese the MediaPlayer
    */
    protected void releaseMediaPlayer() {
       /*  if the mediaPlayer is not null then release the MediaPlayer*/
        if (mMediaPlayer != null) {
            /*
            releasing the MediaPlayer to be null as our mMediaPlayer has stopped playing.
             */
            mMediaPlayer.release();
            /*
             After the MediaPlayer has been released setting its value
             to be null
              */
            mMediaPlayer = null;
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

}
