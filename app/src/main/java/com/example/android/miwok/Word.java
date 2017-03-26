package com.example.android.miwok;

import android.content.Context;

/**
 * Created by Sanjeeb on 12/28/2016.
 * This class represents a word used in the numbers list in our Miwok app.
 */

public class Word {

    /* mMiwokTranslation stores the number in miwok
     *  mDefaultTranslation stores the number in english
     * contains the resource ID of the icon pictures
     */
    private String mMiwokTranslation;
    private String mDefaultTranslation;
    private int mIconId;
    private int mMusicId;
    private final static int NO_IMAGE_PROVIDED = -1;
    private final int NO_MUSIC_PROVIDED = -1;


    /*
    Four argument constructor that take in all the fields in the Word class.
    It initializes:
    private String mMiwokTranslation;
    private String mDefaultTranslation;
    private int mIconId;
    private int mMusicId;

     */
    public Word(String defaultTranslation, String miwokTranslation, int iconId, int musicId) {
        mMiwokTranslation = miwokTranslation;
        mDefaultTranslation = defaultTranslation;
        mIconId = iconId;
        mMusicId = musicId;
    }

    /*
    No argument constructor
    */
    public Word() {
        mMiwokTranslation = "Miwok Number";
        mDefaultTranslation = "English Number";
        mIconId = NO_IMAGE_PROVIDED;
        mMusicId = NO_MUSIC_PROVIDED;
    }

    /*
    Two argument constructor. It takes in
    */
    public Word(String defaultTranslation, String miwokTranslation) {
        mMiwokTranslation = miwokTranslation;
        mDefaultTranslation = defaultTranslation;
        mIconId = NO_IMAGE_PROVIDED;
        mMusicId = NO_MUSIC_PROVIDED;
    }

    /*
    Three argument constructor. It takes in defaultTranslation, miwokTranslation and then the musicResId.
    It is used in PhrasesActivity where no image is used but a music is used.
    */
    public Word(String defaultTranslation, String miwokTranslation, int musicResId) {
        mMiwokTranslation = miwokTranslation;
        mDefaultTranslation = defaultTranslation;
        mIconId = NO_IMAGE_PROVIDED;
        mMusicId = musicResId;
    }


    /*setter for mMiwokTranslation*/
    public void setMiwokTranslation(String miwokTranslation) {
        mMiwokTranslation = miwokTranslation;
    }

    /*
    getter for mMiwokTranslation
    */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    /*
     getter for mDefaultTranslation
    */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /*
    setter for mDefaultTranslation
    */
    public void setDefaultTranslation(String defaultTranslation) {
        mDefaultTranslation = defaultTranslation;
    }

    /*
    setter for mIconId
    */
    public void setIconPictureResId(int iconId) {
        mIconId = iconId;
    }


    /*
        getter for mIconId
         */
    public int getIconId() {
        return mIconId;
    }

    public boolean hasImage() {
        if (getIconId() != NO_IMAGE_PROVIDED) {
            return true;
        } else {
            return false;
        }
    }

    /*
        setter for mMusicResId.
     */
    public void setMusicResId(int musicResId) {
        mMusicId = musicResId;
    }

    /*
        getter for mMusicResId.
     */
    public int getMusicResId() {
        return mMusicId;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mIconId=" + mIconId +
                ", mMusicId=" + mMusicId +
                ", NO_MUSIC_PROVIDED=" + NO_MUSIC_PROVIDED +
                '}';
    }
}
