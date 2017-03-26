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


public class ColorsFragment extends Fragment {
    private MediaPlayer mMediaPlayer;
    private int mAudioFocus;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;
    /*
            Creating an instance of a onAudioFocusListener class onAudioFocusChangeListener
     */

    public ColorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_colors, container, false);

        // Creating an array colors that contains 10 Word objects
        final ArrayList<Word> colors = new ArrayList<Word>();


        // Adding 10 new Word objects to the colors ArrayList
        colors.add(new Word("red", "wetetti", R.drawable.color_red, R.raw.color_red));
        colors.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        colors.add(new Word("brown", "takaakki", R.drawable.color_brown, R.raw.color_brown));
        colors.add(new Word("gray", "topoppi", R.drawable.color_gray, R.raw.color_gray));
        colors.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        colors.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        colors.add(new Word("dusty yellow", "topiise", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        colors.add(new Word("mustard Yellow", "chiwiite", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        // itemsList is the adapter for our @resource listView
        WordsArrayAdapter itemsList = new WordsArrayAdapter(getActivity(), R.layout.numbers_list_text_view, colors);

        // Creating a ListView object listView that ccorresponds to the xml resource file @resource list
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(itemsList);

        /*
            Creating an instance of a AudioManager class named audioManager
            and requesting for AudioFocus and storing it in audioFocus
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
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word currentWord = colors.get(i);
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


