package me.chriskyle.library.toolkit.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import java.util.Iterator;
import java.util.Stack;

/**
 * Description :
 * <p/>
 * Created by Chris Kyle on 2017/1/6.
 */
public final class ActivityManager {

    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (activityStack == null) {
            return;
        }
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    public Activity currentActivity() {
        if (activityStack == null) {
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    public void finishActivity() {
        if (activityStack == null) {
            return;
        }
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    public void finishActivity(Activity activity) {
        if (activityStack == null) {
            return;
        }
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public void finishActivity(Class<?> cls) {
        if (activityStack == null) {
            return;
        }
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null && activity.getClass().equals(cls)) {
                activity.finish();
                iterator.remove();
            }
        }
    }

    public void finishAllManagedActivity() {
        if (activityStack == null) {
            return;
        }
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    public Activity getActivity(String activityName) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null && TextUtils.equals(activity.getClass().getName(), activityName)) {
                return activity;
            }
        }
        return null;
    }

    public void appExit(Context context) {
        try {
            finishAllManagedActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
