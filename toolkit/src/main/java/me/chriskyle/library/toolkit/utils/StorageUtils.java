package me.chriskyle.library.toolkit.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import me.chriskyle.library.toolkit.log.Logger;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * provides application storage paths
 */
public final class StorageUtils {

    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final String INDIVIDUAL_DIR_NAME = "cache";

    private StorageUtils() {
    }

    /**
     * Returns application cache directory. Cache directory will be created on SD card
     * <i>("/Android/data/[app_package_name]/cache")</i> if card is mounted and app has appropriate permission. Else -
     * Android defines cache directory on device's file system.
     *
     * @param context Application context
     * @return Cache {@link File directory}.<br />
     * <b>NOTE:</b> Can be null in some unpredictable cases (if SD card is unmounted and
     * {@link Context#getCacheDir() Context.getCacheDir()} returns null).
     */
    public static File getCacheDirectory(Context context) {
        return getCacheDirectory(context, true);
    }

    /**
     * Returns application cache directory. Cache directory will be created on SD card
     * <i>("/Android/data/[app_package_name]/cache")</i> (if card is mounted and app has appropriate permission) or
     * on device's file system depending incoming parameters.
     *
     * @param context        Application context
     * @param preferExternal Whether prefer external location for cache
     * @return Cache {@link File directory}.<br />
     * <b>NOTE:</b> Can be null in some unpredictable cases (if SD card is unmounted and
     * {@link Context#getCacheDir() Context.getCacheDir()} returns null).
     */
    public static File getCacheDirectory(Context context, boolean preferExternal) {
        File appCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) { // (sh)it happens (Issue #660)
            externalStorageState = "";
        } catch (IncompatibleClassChangeError e) { // (sh)it happens too (Issue #989)
            externalStorageState = "";
        }
        if (preferExternal && MEDIA_MOUNTED.equals(externalStorageState) && hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            Logger.w("Can't define system cache directory! " + cacheDirPath + " will be used.");
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }

    /**
     * Returns individual application cache directory (for only image caching from ImageLoader). Cache directory will be
     * created on SD card <i>("/Android/data/[app_package_name]/cache/uil-images")</i> if card is mounted and app has
     * appropriate permission. Else - Android defines cache directory on device's file system.
     *
     * @param context Application context
     * @return Cache {@link File directory}
     */
    public static File getIndividualCacheDirectory(Context context) {
        return getIndividualCacheDirectory(context, INDIVIDUAL_DIR_NAME);
    }

    /**
     * Returns individual application cache directory (for only image caching from ImageLoader). Cache directory will be
     * created on SD card <i>("/Android/data/[app_package_name]/cache/uil-images")</i> if card is mounted and app has
     * appropriate permission. Else - Android defines cache directory on device's file system.
     *
     * @param context  Application context
     * @param cacheDir Cache directory path (e.g.: "AppCacheDir", "AppDir/cache/images")
     * @return Cache {@link File directory}
     */
    public static File getIndividualCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = getCacheDirectory(context);
        File individualCacheDir = new File(appCacheDir, cacheDir);
        if (!individualCacheDir.exists()) {
            if (!individualCacheDir.mkdir()) {
                individualCacheDir = appCacheDir;
            }
        }
        return individualCacheDir;
    }

    /**
     * Returns specified application cache directory. Cache directory will be created on SD card by defined path if card
     * is mounted and app has appropriate permission. Else - Android defines cache directory on device's file system.
     *
     * @param context  Application context
     * @param cacheDir Cache directory path (e.g.: "AppCacheDir", "AppDir/cache/images")
     * @return Cache {@link File directory}
     */
    public static File getOwnCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
        }
        if (appCacheDir == null || (!appCacheDir.exists() && !appCacheDir.mkdirs())) {
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir;
    }

    /**
     * Returns specified application cache directory. Cache directory will be created on SD card by defined path if card
     * is mounted and app has appropriate permission. Else - Android defines cache directory on device's file system.
     *
     * @param context  Application context
     * @param cacheDir Cache directory path (e.g.: "AppCacheDir", "AppDir/cache/images")
     * @return Cache {@link File directory}
     */
    public static File getOwnCacheDirectory(Context context, String cacheDir, boolean preferExternal) {
        File appCacheDir = null;
        if (preferExternal && MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
        }
        if (appCacheDir == null || (!appCacheDir.exists() && !appCacheDir.mkdirs())) {
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                Logger.w("Unable to create external cache directory");
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                Logger.i("Can't create \".nomedia\" file in application external cache directory");
            }
        }
        return appCacheDir;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    private static final String PREF_FILE = "pseudo.persist";
    private static final String SDCARD_FILE = "beat.persist";
    private static final String PSEUDO_PERSIST_SEP = ",";

    private static File getOrCreatePseudoPersistFile() {
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Documents");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir.getAbsolutePath() + File.separator + SDCARD_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private static SharedPreferences getPseudoPersistPref(Context context) {
        return context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
    }

    /**
     * 获取伪持久value
     */
    public static String getPseudoPersistValue(Context context, String key) {
        SharedPreferences sharedPref = getPseudoPersistPref(context);
        File persistFile = getOrCreatePseudoPersistFile();

        String prefValue = getPseudoPersistValueFromPref(sharedPref, key);
        Map<String, String> map = getPseudoPersistMapFromFile(persistFile);
        String fileValue = map.get(key);

        if (TextUtils.isEmpty(prefValue)) {
            if (TextUtils.isEmpty(fileValue)) {
                return null;
            } else {
                saveToPreference(sharedPref, key, fileValue);
                return fileValue;
            }
        } else {
            if (!prefValue.equals(fileValue)) {
                map.put(key, prefValue);
                saveToSdCard(persistFile, map);
            }

            return prefValue;
        }
    }

    /**
     * 保存伪持久key-value
     */
    public static boolean setPseudoPersistValue(Context context, String key, String value) {
        SharedPreferences sharedPref = getPseudoPersistPref(context);
        File persistFile = getOrCreatePseudoPersistFile();
        Map<String, String> map = getPseudoPersistMapFromFile(persistFile);
        map.put(key, value);
        return saveToPreference(sharedPref, key, value) && saveToSdCard(persistFile, map);
    }

    /**
     * 保存在preference
     */
    private static boolean saveToPreference(SharedPreferences sharedPref, String key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        boolean b = editor.commit();
        return b;
    }

    /**
     * 保存到sd卡
     */
    private static boolean saveToSdCard(File file, Map<String, String> map) {
        if (!file.exists()) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            sb.append(entry.getKey() + PSEUDO_PERSIST_SEP + entry.getValue()).append("\n");
        }
        return FileUtils.writeToFile(file, sb.toString());
    }

    /**
     * 从preference中获取value
     *
     * @param sharedPref
     * @return
     */
    private static String getPseudoPersistValueFromPref(SharedPreferences sharedPref, String key) {
        return sharedPref.getString(key, null);
    }

    /**
     * 从内置文件中读取key-value数据
     *
     * @param file
     * @return
     */
    private static Map<String, String> getPseudoPersistMapFromFile(File file) {
        Map<String, String> map = new HashMap<>();
        if (!file.exists()) {
            return map;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String array[] = line.split(PSEUDO_PERSIST_SEP);
                map.put(array[0], array[1]);
            }
            br.close();
        } catch (IOException e) {
            return null;
        }
        return map;
    }

    /**
     * 判断外置存储是否可写
     *
     * @return
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * 判断外置存储是否可读
     *
     * @return
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    /**
     * 在外置存储中创建公共目录（卸载应用不被删除）
     *
     * @param albumName
     * @return
     */
    public static File createAlbumStorageDir(String albumName, String directoryType) {
        File dir = new File(Environment.getExternalStoragePublicDirectory(directoryType), albumName);
        if (!dir.mkdirs()) {
            return null;
        }
        return dir;
    }

    /**
     * 在外置存储中创建私有目录（卸载应用被删除）
     *
     * @param context
     * @param albumName
     * @return
     */
    public static File createAlbumStorageDir(Context context, String albumName, String directoryType) {
        // Get the directory for the app's private pictures directory.
        File dir = new File(context.getExternalFilesDir(directoryType), albumName);
        if (!dir.mkdirs()) {
            return null;
        }
        return dir;
    }
}
