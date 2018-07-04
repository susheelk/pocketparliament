package com.susheel.pocketparliament.ui.tasks;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * @author Susheel Kona
 */

public final class ColorUtils {
    public static int darken(@ColorInt int color) {
        int alpha = Color.alpha(color);
        int red = Math.round(Color.red(color) *0.8f);
        int green = Math.round(Color.green(color) *0.8f);
        int blue = Math.round(Color.blue(color) *0.8f);

        return Color.argb(alpha, Math.min(red, 255), Math.min(green, 255), Math.min(blue, 255));
    }
}
