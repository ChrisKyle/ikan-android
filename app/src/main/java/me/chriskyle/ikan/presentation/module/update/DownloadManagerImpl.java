package me.chriskyle.ikan.presentation.module.update;

import android.support.annotation.NonNull;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.DownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

import me.chriskyle.library.toolkit.log.Logger;

/**
 * Description :
 * <p>
 * Created by Chris Kyle on 2017/9/26.
 */
public class DownloadManagerImpl implements DownloadManager {

    @Override
    public void download(@NonNull String url, @NonNull String path, @NonNull String fileName,
                         @NonNull final Callback callback) {
        BaseDownloadTask downloadTask = FileDownloader.getImpl().create(url)
                .setPath(path)
                .setListener(new FileDownloadListener() {

                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        callback.onBefore();
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        callback.onProgress((float) soFarBytes/totalBytes, totalBytes);
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        callback.onResponse(new File(task.getTargetFilePath()));
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                });
        ((DownloadTask) downloadTask).setFileName(fileName);
        downloadTask.start();
    }
}
