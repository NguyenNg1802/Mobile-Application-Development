package com.example.android.miwok;

public class Word {
    private String english;
    private String miwok;
    private int image;
    private int pronouce;

    public Word(String english, String miwok, int image, int pronouce) {
        this.english = english;
        this.miwok = miwok;
        this.image = image;
        this.pronouce = pronouce;
    }

    public Word(String english, String miwok, int pronouce) {
        this.english = english;
        this.miwok = miwok;
        this.pronouce = pronouce;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getMiwok() {
        return miwok;
    }

    public void setMiwok(String miwok) {
        this.miwok = miwok;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage() {
        return this.image;
    }

    public int getPronouce() {
        return pronouce;
    }

    public void setPronouce(int pronouce) {
        this.pronouce = pronouce;
    }
}
