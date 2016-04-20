package com.vijayganduri.saltside.ui.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by vganduri on 11/19/2015.
 */
public class ToastManager {

    private static Toast toast;

    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }

    public static void showToast(Context context, String msg) {
        dismissToast();
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void dismissToast(){
        if(toast!=null){
            toast.cancel();
        }
    }

}