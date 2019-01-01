package com.susheel.pocketparliament.model.legislation;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

public class Ballot implements Parcelable, Serializable {
    private int id;
    private String name;
    private String vote;

    public Ballot(int id, String name, String vote) {
        this.id = id;
        this.name = name;
        this.vote = vote;
    }

    public Ballot() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    protected Ballot(Parcel in) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

    }

    public static final Parcelable.Creator<Ballot> CREATOR = new Parcelable.Creator<Ballot>() {
        @Override
        public Ballot createFromParcel(Parcel in) {
            return new Ballot(in);
        }

        @Override
        public Ballot[] newArray(int size) {
            return new Ballot[size];
        }
    };
}