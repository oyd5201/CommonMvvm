package com.kunminx.architecture.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *     author:
 *                                      ___           ___           ___         ___
 *         _____                       /  /\         /__/\         /__/|       /  /\
 *        /  /::\                     /  /::\        \  \:\       |  |:|      /  /:/
 *       /  /:/\:\    ___     ___    /  /:/\:\        \  \:\      |  |:|     /__/::\
 *      /  /:/~/::\  /__/\   /  /\  /  /:/~/::\   _____\__\:\   __|  |:|     \__\/\:\
 *     /__/:/ /:/\:| \  \:\ /  /:/ /__/:/ /:/\:\ /__/::::::::\ /__/\_|:|____    \  \:\
 *     \  \:\/:/~/:/  \  \:\  /:/  \  \:\/:/__\/ \  \:\~~\~~\/ \  \:\/:::::/     \__\:\
 *      \  \::/ /:/    \  \:\/:/    \  \::/       \  \:\  ~~~   \  \::/~~~~      /  /:/
 *       \  \:\/:/      \  \::/      \  \:\        \  \:\        \  \:\         /__/:/
 *        \  \::/        \__\/        \  \:\        \  \:\        \  \:\        \__\/
 *         \__\/                       \__\/         \__\/         \__\/
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : utils about initialization
 * </pre>
 */
public final class Utils {

    private static final String PERMISSION_ACTIVITY_CLASS_NAME =
        "com.blankj.utilcode.util.PermissionUtils$PermissionActivity";

    private static final ActivityLifecycleImpl ACTIVITY_LIFECYCLE = new ActivityLifecycleImpl();

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Init utils.
     * <p>Init it in the class of Application.</p>
     *
     * @param context context
     */
    public static void init(final Context context) {
        if (context == null) {
            init(getApplicationByReflect());
            return;
        }
        init((Application) context.getApplicationContext());
    }

    /**
     * Init utils.
     * <p>Init it in the class of Application.</p>
     *
     * @param app application
     */
    public static void init(final Application app) {
        if (sApplication == null) {
            if (app == null) {
                sApplication = getApplicationByReflect();
            } else {
                sApplication = app;
            }
            sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
        } else {
            if (app != null && app.getClass() != sApplication.getClass()) {
                sApplication.unregisterActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
                ACTIVITY_LIFECYCLE.mActivityList.clear();
                sApplication = app;
                sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
            }
        }
    }

    /**
     * Return the context of Application object.
     *
     * @return the context of Application object
     */
    public static Application getApp() {
        if (sApplication != null) {
            return sApplication;
        }
        Application app = getApplicationByReflect();
        init(app);
        return app;
    }

    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (NoSuchMethodException | IllegalAccessException | ClassNotFoundException | InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }

    public interface OnAppStatusChangedListener {
        void onForeground();

        void onBackground();
    }

    public interface OnActivityDestroyedListener {
        void onActivityDestroyed(Activity activity);
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////

    static class ActivityLifecycleImpl implements ActivityLifecycleCallbacks {

        final LinkedList<Activity> mActivityList = new LinkedList<>();
        final Map<Object, OnAppStatusChangedListener> mStatusListenerMap = new HashMap<>();
        final Map<Activity, Set<OnActivityDestroyedListener>> mDestroyedListenerMap = new HashMap<>();

        private int mForegroundCount = 0;
        private int mConfigCount = 0;
        private boolean mIsBackground = false;

        private static void fixSoftInputLeaks(final Activity activity) {
            if (activity == null) {
                return;
            }
            InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm == null) {
                return;
            }
            String[] leakViews = new String[]{"mLastSrvView", "mCurRootView", "mServedView", "mNextServedView"};
            for (String leakView : leakViews) {
                try {
                    Field leakViewField = InputMethodManager.class.getDeclaredField(leakView);
                    if (leakViewField == null) {
                        continue;
                    }
                    if (!leakViewField.isAccessible()) {
                        leakViewField.setAccessible(true);
                    }
                    Object obj = leakViewField.get(imm);
                    if (!(obj instanceof View)) {
                        continue;
                    }
                    View view = (View) obj;
                    if (view.getRootView() == activity.getWindow().getDecorView().getRootView()) {
                        leakViewField.set(imm, null);
                    }
                } catch (Throwable ignore) { /**/ }
            }
        }

        @Override
        public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {
            if (!mIsBackground) {
                setTopActivity(activity);
            }
            if (mConfigCount < 0) {
                ++mConfigCount;
            } else {
                ++mForegroundCount;
            }
        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {
            setTopActivity(activity);
            if (mIsBackground) {
                mIsBackground = false;
                postStatus(true);
            }
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {/**/

        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (activity.isChangingConfigurations()) {
                --mConfigCount;
            } else {
                --mForegroundCount;
                if (mForegroundCount <= 0) {
                    mIsBackground = true;
                    postStatus(false);
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {/**/}

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
            mActivityList.remove(activity);
            consumeOnActivityDestroyedListener(activity);
            fixSoftInputLeaks(activity);
        }

        Activity getTopActivity() {
            if (!mActivityList.isEmpty()) {
                final Activity topActivity = mActivityList.getLast();
                if (topActivity != null) {
                    return topActivity;
                }
            }
            Activity topActivityByReflect = getTopActivityByReflect();
            if (topActivityByReflect != null) {
                setTopActivity(topActivityByReflect);
            }
            return topActivityByReflect;
        }

        private void setTopActivity(final Activity activity) {
            if (PERMISSION_ACTIVITY_CLASS_NAME.equals(activity.getClass().getName())) {
                return;
            }
            if (mActivityList.contains(activity)) {
                if (!mActivityList.getLast().equals(activity)) {
                    mActivityList.remove(activity);
                    mActivityList.addLast(activity);
                }
            } else {
                mActivityList.addLast(activity);
            }
        }

        void addOnAppStatusChangedListener(final Object object,
                                           final OnAppStatusChangedListener listener) {
            mStatusListenerMap.put(object, listener);
        }

        void removeOnAppStatusChangedListener(final Object object) {
            mStatusListenerMap.remove(object);
        }

        void removeOnActivityDestroyedListener(final Activity activity) {
            if (activity == null) {
                return;
            }
            mDestroyedListenerMap.remove(activity);
        }

        void addOnActivityDestroyedListener(final Activity activity,
                                            final OnActivityDestroyedListener listener) {
            if (activity == null || listener == null) {
                return;
            }
            Set<OnActivityDestroyedListener> listeners;
            if (!mDestroyedListenerMap.containsKey(activity)) {
                listeners = new HashSet<>();
                mDestroyedListenerMap.put(activity, listeners);
            } else {
                listeners = mDestroyedListenerMap.get(activity);
                if (listeners.contains(listener)) {
                    return;
                }
            }
            listeners.add(listener);
        }

        private void postStatus(final boolean isForeground) {
            if (mStatusListenerMap.isEmpty()) {
                return;
            }
            for (OnAppStatusChangedListener onAppStatusChangedListener : mStatusListenerMap.values()) {
                if (onAppStatusChangedListener == null) {
                    return;
                }
                if (isForeground) {
                    onAppStatusChangedListener.onForeground();
                } else {
                    onAppStatusChangedListener.onBackground();
                }
            }
        }

        private void consumeOnActivityDestroyedListener(Activity activity) {
            Iterator<Map.Entry<Activity, Set<OnActivityDestroyedListener>>> iterator
                = mDestroyedListenerMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Activity, Set<OnActivityDestroyedListener>> entry = iterator.next();
                if (entry.getKey() == activity) {
                    Set<OnActivityDestroyedListener> value = entry.getValue();
                    for (OnActivityDestroyedListener listener : value) {
                        listener.onActivityDestroyed(activity);
                    }
                    iterator.remove();
                }
            }
        }

        private Activity getTopActivityByReflect() {
            try {
                @SuppressLint("PrivateApi")
                Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
                Object currentActivityThreadMethod = activityThreadClass.getMethod("currentActivityThread").invoke(null);
                Field mActivityListField = activityThreadClass.getDeclaredField("mActivityList");
                mActivityListField.setAccessible(true);
                Map activities = (Map) mActivityListField.get(currentActivityThreadMethod);
                if (activities == null) {
                    return null;
                }
                for (Object activityRecord : activities.values()) {
                    Class activityRecordClass = activityRecord.getClass();
                    Field pausedField = activityRecordClass.getDeclaredField("paused");
                    pausedField.setAccessible(true);
                    if (!pausedField.getBoolean(activityRecord)) {
                        Field activityField = activityRecordClass.getDeclaredField("activity");
                        activityField.setAccessible(true);
                        return (Activity) activityField.get(activityRecord);
                    }
                }
            } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static final class FileProvider4UtilCode extends FileProvider {

        @Override
        public boolean onCreate() {
            Utils.init(getContext());
            return true;
        }
    }
    //判断年月日是星期几 返回1是星期天 以此类推
    public static int getDayofWeek(String dateTime) {
        Calendar cal = Calendar.getInstance();
        if (dateTime.equals("")) {
            cal.setTime(new Date(System.currentTimeMillis()));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date;
            try {
                date = sdf.parse(dateTime);
            } catch (ParseException e) {
                date = null;
                e.printStackTrace();
            }
            if (date != null) {
                cal.setTime(new Date(date.getTime()));
            }
        }
        return cal.get(Calendar.DAY_OF_WEEK);
    }


    //判断某年某月有多少天
    public static int getMonthOfCounts(int year,int month){
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0); //输入类型为int类型

        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        return dayOfMonth;

    }
}
