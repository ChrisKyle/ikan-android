package me.chriskyle.ikan.presentation.module.update.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import java.io.File;

import me.chriskyle.ikan.R;
import me.chriskyle.ikan.data.entity.VersionEntity;
import me.chriskyle.ikan.presentation.module.update.DownloadManager;
import me.chriskyle.ikan.presentation.module.update.DownloadManagerImpl;
import me.chriskyle.ikan.presentation.module.update.utils.AppUpdateUtils;
import me.chriskyle.library.design.tip.Tip;

/**
 * Description :
 * <p>
 * Created by Chris Kyle on 2017/9/26.
 */
public class DownloadService extends Service {

    private static final int NOTIFY_ID = 0;
    public static boolean isRunning = false;
    private NotificationManager notificationManager;
    private DownloadBinder binder = new DownloadBinder();
    private NotificationCompat.Builder builder;

    public static void bindService(Context context, ServiceConnection connection) {
        Intent intent = new Intent(context, DownloadService.class);
        context.startService(intent);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        isRunning = true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        notificationManager = null;
        super.onDestroy();
    }

    private void setupNotification() {
        builder = new NotificationCompat.Builder(this, "ikan_notification_channel");
        builder.setContentTitle("开始下载")
                .setContentText("正在连接服务器")
                .setSmallIcon(R.drawable.ic_update_app)
                .setLargeIcon(AppUpdateUtils.drawableToBitmap(AppUpdateUtils.getAppIcon(DownloadService.this)))
                .setOngoing(true)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis());
        notificationManager.notify(NOTIFY_ID, builder.build());
    }

    private void startDownload(VersionEntity versionEntity, final BinderDownloadCallback callback) {
        String apkUrl = versionEntity.getUrl();
        if (TextUtils.isEmpty(apkUrl)) {
            String errorContentText = "新版本下载路径错误";
            stop(errorContentText);
            return;
        }

        String appName = AppUpdateUtils.getApkName(versionEntity);
        File appDir = new File(AppUpdateUtils.getTargetPath(DownloadService.this));
        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        String target = appDir + File.separator + versionEntity.getName();

        new DownloadManagerImpl().download(apkUrl, target, appName, new DownloadCallBack(callback));
    }

    private void stop(String contentText) {
        if (builder != null) {
            builder.setContentTitle(AppUpdateUtils.getAppName(DownloadService.this)).setContentText(contentText);
            Notification notification = builder.build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(NOTIFY_ID, notification);
        }
        close();
    }

    private void close() {
        stopSelf();
        isRunning = false;
    }

    public interface BinderDownloadCallback {

        void onStart();

        void onProgress(float progress, long totalSize);

        void setMax(long totalSize);

        boolean onFinish(File file);

        void onError(String msg);
    }

    public class DownloadBinder extends Binder {

        public void start(VersionEntity versionEntity, BinderDownloadCallback callback) {
            startDownload(versionEntity, callback);
        }
    }

    public class DownloadCallBack implements DownloadManager.Callback {

        private final BinderDownloadCallback callBack;

        private int oldRate = 0;

        DownloadCallBack(@Nullable BinderDownloadCallback callback) {
            super();
            this.callBack = callback;
        }

        @Override
        public void onBefore() {
            setupNotification();
            if (callBack != null) {
                callBack.onStart();
            }
        }

        @Override
        public void onProgress(float progress, long total) {
            int rate = Math.round(progress * 100);
            if (oldRate != rate) {
                if (callBack != null) {
                    callBack.setMax(total);
                    callBack.onProgress(progress, total);
                }

                if (builder != null) {
                    builder.setContentTitle("正在下载：" + AppUpdateUtils.getAppName(DownloadService.this))
                            .setContentText(rate + "%")
                            .setProgress(100, rate, false)
                            .setWhen(System.currentTimeMillis());
                    Notification notification = builder.build();
                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                    notificationManager.notify(NOTIFY_ID, notification);
                }

                oldRate = rate;
            }
        }

        @Override
        public void onError(String error) {
            Tip.showTip(DownloadService.this, "更新新版本出错，" + error);

            if (callBack != null) {
                callBack.onError(error);
            }
            try {
                notificationManager.cancel(NOTIFY_ID);
                close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        @Override
        public void onResponse(File file) {
            if (callBack != null) {
                if (!callBack.onFinish(file)) {
                    close();
                    return;
                }
            }

            try {
                if (AppUpdateUtils.isAppOnForeground(DownloadService.this) || builder == null) {
                    notificationManager.cancel(NOTIFY_ID);
                    AppUpdateUtils.installApp(DownloadService.this, file);
                } else {
                    Intent installAppIntent = AppUpdateUtils.getInstallAppIntent(DownloadService.this, file);
                    PendingIntent contentIntent = PendingIntent.getActivity(DownloadService.this, 0, installAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(contentIntent)
                            .setContentTitle(AppUpdateUtils.getAppName(DownloadService.this))
                            .setContentText("下载完成，请点击安装")
                            .setProgress(0, 0, false)
                            .setDefaults((Notification.DEFAULT_ALL));
                    Notification notification = builder.build();
                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                    notificationManager.notify(NOTIFY_ID, notification);
                }
                close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }
}
