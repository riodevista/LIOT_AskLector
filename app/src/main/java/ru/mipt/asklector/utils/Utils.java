package ru.mipt.asklector.utils;

import android.util.DisplayMetrics;

import ru.mipt.asklector.AskLector;

/**
 * Created by Dmitry Bochkov on 10.12.2015.
 */
public class Utils {

    public static int pxToDp(int px) {
        DisplayMetrics displayMetrics = AskLector.provideContext().getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = AskLector.provideContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static String improveText(String text){
        text = text.replaceAll("\n", " ");
        text = text.replaceAll(" +", " ").trim();
        return text;
    }
}
