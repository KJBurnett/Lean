package com.burntech.kyler.lean;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Kyler on 5/18/2015.
 */
public class Util {

    public static String getCurrentTime(){
        String format = "MM/dd/yyyy hh:mm a";
        SimpleDateFormat df = new SimpleDateFormat(format);

        Calendar c = Calendar.getInstance();
        String time = df.format(c.getTime());

        return time;
    }
}
