package me.chriskyle.ikan.presentation.module.update;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import me.chriskyle.ikan.data.entity.VersionEntity;

/**
 * Description :
 * <p>
 * Created by Chris Kyle on 2017/9/26.
 */
public final class UpdateAppManager {

    final static String EXTRA_KEY_VERSION = "version";

    public void showUpdateDialog(Activity activity, VersionEntity versionEntity) {
        Bundle bundle = new Bundle();

        UpdateDialogFragment updateDialogFragment = new UpdateDialogFragment();
        bundle.putParcelable(EXTRA_KEY_VERSION, versionEntity);
        updateDialogFragment.setArguments(bundle);
        updateDialogFragment.show(((FragmentActivity) activity).getSupportFragmentManager(), "dialog");
    }
}
