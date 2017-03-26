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


public class PhrasesFragment extends Fragment {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private int mAudioFocus = -1;

    public PhrasesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.activity_phrases, container, false);

        // Creating an array phrases that contains 10 Word objects
        final ArrayList<Word> phrases = new ArrayList<Word>();

        // Adding 10 new Word objects to the phrases ArrayList
        phrases.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        phrases.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        phrases.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        phrases.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        phrases.add(new Word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        phrases.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        phrases.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        phrases.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        phrases.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));
        phrases.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));

        // itemsList is the adapter for our @resource listView
        WordsArrayAdapter itemsList = new WordsArrayAdapter(getActivity(), R.layout.numbers_list_text_view, phrases);


        /*
            Creating an instance of a AudioManager class named audioManager
            and requesting for AudioFocus and storing it in audioFocus
         */

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        /*
            Creating an instance of a onAudioFocusListener class onAudioFocusChangeListener
         */

        final AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
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

        // Creating a ListView object listView that ccorresponds to the xml resource file @resource list
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(itemsList);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word currentWord = phrases.get(i);
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
        Function that will release the MediaPlayer
     */
    protected void releaseMediaPlayer() {
        // if the mediaPlayer is not null then release the MediaPlayer
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