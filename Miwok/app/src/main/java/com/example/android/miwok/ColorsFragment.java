package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ColorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ColorsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<Word> words;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    android.media.AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Loss of audio focus for a couple of times.
                        // Pause playback immediately
                        mediaPlayer.pause();
                        // Play again at the beginning
                        mediaPlayer.seekTo(0);
                    }
                    else if (focusChange == android.media.AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                        mediaPlayer.start();
                    }
                    else if (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS) {
                        // release mediaplayer if audio focus loss permanently.
                        releaseMediaPlayer();
                    }
                }
            };

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ColorsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ColorsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ColorsFragment newInstance(String param1, String param2) {
        ColorsFragment fragment = new ColorsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create an array of words
        words = new ArrayList<Word>() {
            {
                add(new Word("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
                add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
                add(new Word("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
                add(new Word("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
                add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
                add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));
                add(new Word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
                add(new Word("mustard yello", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
            }
        };

        WordAdapter wordAdapter = new WordAdapter(getActivity(), R.layout.list_item, words, R.color.category_colors);
        ListView listView = (ListView)rootView.findViewById(R.id.list);
        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                if (word.getPronouce() != 0) {
                    releaseMediaPlayer();

                    // Request audio focus for playback
                    int result = audioManager.requestAudioFocus(afChangeListener,
                            // Use the music stream.
                            AudioManager.STREAM_MUSIC,
                            // Request only couple of seconds.
                            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        // We have audio focus now
                        mediaPlayer = MediaPlayer.create(getActivity(), word.getPronouce());
                        mediaPlayer.start();

                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                releaseMediaPlayer();
                            }
                        });
                    }
                }
            }
        });

        return rootView;
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        mediaPlayer = null;

        audioManager.abandonAudioFocus(afChangeListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }
}