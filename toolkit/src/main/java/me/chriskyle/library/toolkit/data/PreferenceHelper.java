package me.chriskyle.library.toolkit.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Description :
 * <p/>
 * Created by Chris Kyle on 2017/1/6.
 */
public final class PreferenceHelper {

    public static final String SDK_PREFERENCE_DEFAULT_STRING = null;
    public static final int SDK_PREFERENCE_DEFAULT_INT = -10000;
    public static final long SDK_PREFERENCE_DEFAULT_LONG = 0L;
    public static final boolean SDK_PREFERENCE_DEFAULT_BOOL = false;
    public static final int[] SDK_PREFERENCE_DEFAULT_INT_ARRAY = new int[]{0};

    private static final String DEFAULT_PREFERENCE_NAME = "cn.chriskyle.library.toolkit.data";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private volatile static PreferenceHelper preferenceHelper;

    public static PreferenceHelper getInstance(Context context) {
        return getInstance(context, DEFAULT_PREFERENCE_NAME);
    }

    public static PreferenceHelper getInstance(Context context, String preferenceName) {
        if (preferenceHelper == null) {
            synchronized (PreferenceHelper.class) {
                if (preferenceHelper == null) {
                    preferenceHelper = new PreferenceHelper(context, preferenceName);
                }
            }
        }
        return preferenceHelper;
    }

    private PreferenceHelper(Context context, String preferenceName) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public PreferenceHelper put(String key, Object value) {
        if (value == null) {
            editor.putString(key, null);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer || value instanceof Byte) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else {
            editor.putString(key, value.toString());
        }
        return this;
    }

    public PreferenceHelper remove(String key) {
        editor.remove(key);
        return this;
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, SDK_PREFERENCE_DEFAULT_STRING);
    }

    public boolean commit(String key, Object value) {
        return put(key, value).commit();
    }

    public boolean commit() {
        return editor.commit();
    }
}
