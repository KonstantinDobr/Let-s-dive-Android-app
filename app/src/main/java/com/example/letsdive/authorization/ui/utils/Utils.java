package com.example.letsdive.authorization.ui.utils;

import android.view.View;

public class Utils {
    public static int visibleOrGone(boolean isVisible) {
        return isVisible ? View.VISIBLE : View.GONE;
    }
}
