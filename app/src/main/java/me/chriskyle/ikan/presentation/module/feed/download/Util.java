package me.chriskyle.ikan.presentation.module.feed.download;

import com.liulishuo.filedownloader.util.FileDownloadUtils;

/**
 * Description :
 * <p>
 * Created by Chris Kyle on 2017/9/25.
 */
public final class Util {

    public static String getSaveFilePath(final String url, String fileName) {
        return FileDownloadUtils.generateFilePath(FileDownloadUtils.getDefaultSaveRootPath(), fileName);
    }
}


