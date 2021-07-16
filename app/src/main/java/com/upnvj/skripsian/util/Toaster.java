package com.upnvj.skripsian.util;

import android.content.Context;
import android.widget.Toast;

public class Toaster {

    public static void shortLength(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void shortLength(Context context, int messageRes) {
        Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show();
    }

    public static void longLength(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void longLength(Context context, int messageRes) {
        Toast.makeText(context, messageRes, Toast.LENGTH_LONG).show();
    }

}
