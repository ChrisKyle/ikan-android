package me.chriskyle.library.toolkit.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

public final class ClientUtil {

    private static final String FIRST_SRC = "first_src";
    private static final String SRC_UNKNOWN = "src_unknown";

    public static String getFirstSrc(Context context, String channelName) {
        String firstSrc = StorageUtils.getPseudoPersistValue(context, FIRST_SRC);
        if (TextUtils.isEmpty(firstSrc)) {
            firstSrc = getLastSrc(context, channelName);
            StorageUtils.setPseudoPersistValue(context, FIRST_SRC, firstSrc);
        }

        return firstSrc;
    }

    public static String getLastSrc(Context context, String channelName) {
        return getFromApplication(context, channelName);
    }

    private static String getFromApplication(Context context, String channelName) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName()
                    , PackageManager.GET_META_DATA);
            return info.metaData.getString(channelName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return SRC_UNKNOWN;
        }
    }

    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);

        return applicationName;
    }
}
