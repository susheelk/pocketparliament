package tech.susheelkona.pocketparliament.model;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * @author Susheel
 */

public class Party implements Parcelable {

    public static Party fromName(String name) {
        return new Party(name, 0); // TODO implement getting this from an external source
    }

    private String name;
    private boolean government;

    private int color;

    public Party(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public Party(String name){
        this.name = name;
    }

    public Party(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @JsonGetter("color")
    public void getColorString() {

    }

    @JsonSetter("color")
    public void setColorString(String color) {
        this.color = Color.parseColor(color);
    }

//    @JsonIgnore
//    public boolean isGovernment() {
//        // NOT GOOD PRACTICE DO NOT DO THIS ACTUALLY MAKE SOMETHING PROPER
//        return name.matches("Liberal");
//    }

    public boolean isGovernment(){
        return this.government;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeByte(this.government ? (byte) 1 : (byte) 0);
        dest.writeInt(this.color);
    }

    protected Party(Parcel in) {
        this.name = in.readString();
        this.government = in.readByte() != 0;
        this.color = in.readInt();
    }

    public static final Parcelable.Creator<Party> CREATOR = new Parcelable.Creator<Party>() {
        @Override
        public Party createFromParcel(Parcel source) {
            return new Party(source);
        }

        @Override
        public Party[] newArray(int size) {
            return new Party[size];
        }
    };
}
