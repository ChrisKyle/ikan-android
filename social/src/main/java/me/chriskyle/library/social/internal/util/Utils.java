package me.chriskyle.library.social.internal.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Description :
 * <p>
 * Created by Chris Kyle on 2017/11/21.
 */
public final class Utils {

    public static boolean isInstall(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packageInfoList.size(); i++) {
            if (packageInfoList.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }
}
