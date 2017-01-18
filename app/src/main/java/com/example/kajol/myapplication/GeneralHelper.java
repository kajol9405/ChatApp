package com.example.kajol.myapplication;

import android.text.format.DateUtils;

public class GeneralHelper {


    public static String time_ago(long message_timestamp) {

        CharSequence xx = DateUtils.getRelativeTimeSpanString(message_timestamp * 1000L, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        return xx.toString();
    }

}
