package me.chriskyle.ikan.presentation.module.update;

import android.support.annotation.NonNull;

import java.io.File;

/**
 * Description :
 * <p>
 * Created by Chris Kyle on 2017/9/26.
 */
public interface DownloadManager {

    void download(@NonNull String url, @NonNull String path, @NonNull String fileName, @NonNull Callback callback);

    interface Callback {

        void onBefore();

        void onProgress(float progress, long total);

        void onResponse(File file);

        void onError(String error);
    }
}
