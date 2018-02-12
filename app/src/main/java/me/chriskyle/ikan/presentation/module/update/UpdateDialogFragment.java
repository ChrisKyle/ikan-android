package me.chriskyle.ikan.presentation.module.update;

import android.Manifest;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import me.chriskyle.ikan.R;
import me.chriskyle.ikan.data.entity.VersionEntity;
import me.chriskyle.ikan.presentation.module.update.service.DownloadService;
import me.chriskyle.ikan.presentation.module.update.utils.AppUpdateUtils;
import me.chriskyle.library.toolkit.utils.ColorUtil;
import me.chriskyle.library.toolkit.utils.DrawableUtil;
import me.chriskyle.library.design.tip.Tip;
import me.chriskyle.library.support.NumberProgressBar;

/**
 * Description :
 * <p>
 * Created by Chris Kyle on 2017/9/26.
 */
public class UpdateDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final String NO_PERMISSIONS_TIPS = "请授权访问存储空间权限，否则APP无法更新!";
    public static boolean isShow = false;

    private TextView tvContent;
    private Button updateOkButton;
    private VersionEntity versionEntity;
    private NumberProgressBar numberProgressBar;
    private ImageView ivClose;
    private TextView tvTitle;

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            startDownloadApp((DownloadService.DownloadBinder) service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    private int defaultColor = 0xfff1b664;
    private int defaultPicResId = R.drawable.update_app_top_bg;

    private LinearLayout llClose;
    private ImageView ivTop;
    private TextView tvIgnore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isShow = true;
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.UpdateAppDialog);
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (versionEntity != null && versionEntity.isConstraint()) {
                        startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });

        Window dialogWindow = getDialog().getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        lp.height = (int) (displayMetrics.heightPixels * 0.8f);
        dialogWindow.setAttributes(lp);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_update_app, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        tvContent =  view.findViewById(R.id.tv_update_info);
        tvTitle = view.findViewById(R.id.tv_title);
        updateOkButton = view.findViewById(R.id.btn_ok);
        numberProgressBar = view.findViewById(R.id.npb);
        ivClose = view.findViewById(R.id.iv_close);
        llClose = view.findViewById(R.id.ll_close);
        ivTop = view.findViewById(R.id.iv_top);
        tvIgnore = view.findViewById(R.id.tv_ignore);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        initTheme();

        if (getArguments() != null) {
            versionEntity = getArguments().getParcelable(UpdateAppManager.EXTRA_KEY_VERSION);
        }

        if (versionEntity != null) {
            String newVersion = versionEntity.getName();
            String targetSize = versionEntity.getSize();
            String updateLog = versionEntity.getUpdateLog();

            String msg = "";

            if (!TextUtils.isEmpty(targetSize)) {
                msg = "新版本大小：" + targetSize + "\n\n";
            }

            if (!TextUtils.isEmpty(updateLog)) {
                msg += updateLog;
            }

            tvContent.setText(msg);
            tvTitle.setText(String.format("是否升级到%s版本？", newVersion));

            if (versionEntity.isConstraint()) {
                llClose.setVisibility(View.GONE);
            } else {
                tvIgnore.setVisibility(View.VISIBLE);
            }

            initEvents();
        }
    }

    private void initTheme() {
        setDialogTheme(defaultColor, defaultPicResId);
    }

    private void setDialogTheme(int color, int topResId) {
        ivTop.setImageResource(topResId);
        numberProgressBar.setProgressTextColor(color);
        numberProgressBar.setReachedBarColor(color);
    }

    private void initEvents() {
        updateOkButton.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        tvIgnore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_ok) {
            int flag = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (flag != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(getActivity(), NO_PERMISSIONS_TIPS, Toast.LENGTH_LONG).show();
                } else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }

            } else {
                installApp();
                updateOkButton.setVisibility(View.GONE);
            }

        } else if (i == R.id.iv_close) {
            dismiss();
        } else if (i == R.id.tv_ignore) {
            AppUpdateUtils.saveIgnoreVersion(getActivity(), versionEntity.getCode());
            dismiss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                installApp();
                updateOkButton.setVisibility(View.GONE);
            } else {
                Tip.showTip(getActivity(), NO_PERMISSIONS_TIPS);
                dismiss();
            }
        }
    }

    private void installApp() {
        if (AppUpdateUtils.appIsDownloaded(versionEntity, getContext())) {
            AppUpdateUtils.installApp(getActivity(), AppUpdateUtils.getAppFile(versionEntity, getContext()));
            dismiss();
        } else {
            downloadApp();
        }
    }

    private void downloadApp() {
        DownloadService.bindService(getActivity().getApplicationContext(), conn);
    }

    private void startDownloadApp(DownloadService.DownloadBinder binder) {
        if (versionEntity != null) {
            binder.start(versionEntity, new DownloadService.BinderDownloadCallback() {

                @Override
                public void onStart() {
                    if (!UpdateDialogFragment.this.isRemoving()) {
                        numberProgressBar.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onProgress(float progress, long totalSize) {
                    if (!UpdateDialogFragment.this.isRemoving()) {
                        numberProgressBar.setProgress(Math.round(progress * 100));
                        numberProgressBar.setMax(100);
                    }
                }

                @Override
                public void setMax(long total) {
                }

                @Override
                public boolean onFinish(File file) {
                    if (!UpdateDialogFragment.this.isRemoving()) {
                        dismissAllowingStateLoss();
                    }
                    return true;
                }

                @Override
                public void onError(String msg) {
                    if (!UpdateDialogFragment.this.isRemoving()) {
                        dismissAllowingStateLoss();
                    }
                }
            });
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            if (manager.isDestroyed())
                return;
        }

        try {
            super.show(manager, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        isShow = false;
        super.onDestroyView();
    }
}
