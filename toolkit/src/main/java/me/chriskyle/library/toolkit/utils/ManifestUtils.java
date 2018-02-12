package me.chriskyle.library.toolkit.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

public final class ManifestUtils {

    public static String getMetaData(Context context, String metaKey) {
        String name = context.getPackageName();
        ApplicationInfo appInfo;
        String mataValue = "";
        try {
            appInfo = context.getPackageManager().getApplicationInfo(name,
                    PackageManager.GET_META_DATA);
            mataValue = appInfo.metaData.getString(metaKey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(mataValue)) {
            return "";
        }

        return mataValue;
    }

    public static String getChannelValue(Context context, String channelKey) {
        return getMetaData(context, channelKey);
    }

    public static String getVersionName(Context context) {
        String version = "";
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(version)) {
            version = "";
        }

        return version;
    }

    public static int getVersionCode(Context context) {
        int versionCode = 0;
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }
}
