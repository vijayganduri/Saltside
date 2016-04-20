package com.vijayganduri.saltside.ui.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by vganduri on 8/3/2015.
 */
public class DisplayUtils{

    public static int convertDptoPixels(Context context, int dip) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }

}