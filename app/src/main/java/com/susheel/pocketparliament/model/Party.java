package com.susheel.pocketparliament.model;

import android.graphics.Color;

/**
 * @author Susheel
 */

public class Party {

    public static Party fromName(String name) {
        return new Party(name, null); // TODO implement getting this from an external source
    }

    private String name;
    private Color color;

    private Party(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
