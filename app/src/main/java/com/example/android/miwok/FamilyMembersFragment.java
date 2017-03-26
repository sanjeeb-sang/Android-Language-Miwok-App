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

import static com.example.android.miwok.R.mipmap.ic_launcher;

public class FamilyMembersFragment extends Fragment {
    private MediaPlayer mMediaPlayer;
    private int mAudioFocus;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;


    public FamilyMembersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_family_members, container, false);
        final ArrayList<Word> familyMembers = new ArrayList<Word>();
        int icon = ic_launcher;
        // Adding 10 new Word objects to the familyMembers ArrayList
        familyMembers.add(new Word("father", "ape", R.drawable.family_father, R.raw.family_father));
        familyMembers.add(new Word("mother", "ela", R.drawable.family_mother, R.raw.family_mother));
        familyMembers.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        familyMembers.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        familyMembers.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        familyMembers.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        familyMembers.add(new Word("older sister", "tele", R.drawable.family_older_sister, R.raw.family_older_sister));
        familyMembers.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        familyMembers.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        familyMembers.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        // itemsList is the adapter for our @resource listView
        WordsArrayAdapter itemsList = new WordsArrayAdapter(getActivity(), R.layout.numbers_list_text_view, familyMembers);


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
                Word currentWord = familyMembers.get(i);
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

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /*
          Function that will relese the MediaPlayer
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


}
