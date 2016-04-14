package com.burntech.kyler.lean;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Kyler on 5/18/2015.
 */
public class Util {

    public static String getCurrentTime(){
        String format = "MM/dd/yyyy HH:MM:ss";
        SimpleDateFormat df = new SimpleDateFormat(format);

        Calendar c = Calendar.getInstance();
        String time = df.format(c.getTime());

        return time;
    }
}
