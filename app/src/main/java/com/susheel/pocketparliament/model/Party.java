package com.susheel.pocketparliament.model;

import android.graphics.Color;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * @author Susheel
 */

public class Party {

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
}
