package com.yqkj.yqframedemo.data.repository;

import android.text.TextUtils;
import android.util.Log;

/**
 * Create by oyd at 2021/11/22
 */

public class Logger {
    private static boolean sEnabled = false;

    public static boolean isEnabled() {
        return sEnabled;
    }

    public static void setEnabled(boolean enabled) {
        sEnabled = enabled;
    }

    public static void d(String msg) {
        if (sEnabled) {
            d("Logger", msg);
        }
    }

    public static void d(String tag, String msg) {
        if (sEnabled) {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable t) {
        if (sEnabled) {
            Log.d(tag, msg, t);
        }
    }

    public static void d(String msg, Throwable t) {
        if (sEnabled) {
            Log.d("", msg, t);
        }
    }

    public static void e(String tag, String msg, Throwable t) {
        if (sEnabled) {
            Log.e(tag, msg, t);
        }
    }

    public static void e(String tag, String msg) {
        if (sEnabled) {
            if (!TextUtils.isEmpty(msg)) {
                Log.e(tag, msg);
            }
        }
    }

    public static void i(String tag, String msg) {
        if (sEnabled) {
            Log.i(tag, msg);
        }
    }
}
