package com.susheel.pocketparliament.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Susheel
 */

public class Riding implements Parcelable {

    public static Riding forList(String name, String province) {
        Riding riding = new Riding();
        riding.setName(name);
        riding.setProvince(province);
        return riding;
    }

    private int id;
    private String province;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.province);
        dest.writeString(this.name);
    }

    public Riding() {
    }

    protected Riding(Parcel in) {
        this.id = in.readInt();
        this.province = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Riding> CREATOR = new Parcelable.Creator<Riding>() {
        @Override
        public Riding createFromParcel(Parcel source) {
            return new Riding(source);
        }

        @Override
        public Riding[] newArray(int size) {
            return new Riding[size];
        }
    };
}
