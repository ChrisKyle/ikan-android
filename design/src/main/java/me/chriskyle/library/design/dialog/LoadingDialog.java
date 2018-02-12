package me.chriskyle.library.design.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.chriskyle.library.design.R;

/**
 * Description :
 * <p/>
 * Created by Chris Kyle on 2017/3/20.
 */
public class LoadingDialog {

    private ProgressDialog progressDialog;
    private boolean cancellable;

    private String defaultLabel = "";

    private String label;

    private int maxProgress;
    private boolean isAutoDismiss;

    private LoadingDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        isAutoDismiss = true;

        defaultLabel = context.getString(R.string.lce_loading_des);
    }

    public static LoadingDialog create(Context context) {
        return new LoadingDialog(context);
    }

    public LoadingDialog setLabel(String label) {
        this.label = label;
        return this;
    }

    public LoadingDialog setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        return this;
    }

    public void setProgress(int progress) {
        progressDialog.setProgress(progress);
    }

    public LoadingDialog setCancellable(boolean isCancellable) {
        cancellable = isCancellable;
        return this;
    }

    public LoadingDialog setAutoDismiss(boolean isAutoDismiss) {
        this.isAutoDismiss = isAutoDismiss;
        return this;
    }

    public LoadingDialog show() {
        if (!isShowing()) {
            if (TextUtils.isEmpty(label)) {
                setLabel(defaultLabel);
            }
            progressDialog.show();
        }
        return this;
    }

    private boolean isShowing() {
        return progressDialog != null && progressDialog.isShowing();
    }

    public void dismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private class ProgressDialog extends Dialog {

        private Context context;

        private TextView progress;

        private ProgressDialog(Context context) {
            super(context, R.style.V4_Dialog);

            this.context = context;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.loading_dialog, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            setContentView(layout, layoutParams);
            setCanceledOnTouchOutside(false);
            setCancelable(cancellable);

            initViews();
        }

        private void initViews() {
            progress = (TextView) findViewById(R.id.progress);

            if (label != null) {
                TextView labelText = (TextView) findViewById(R.id.label);
                labelText.setText(label);
                labelText.setVisibility(View.VISIBLE);
            }
        }

        public void setProgress(int progress) {
            if (this.progress != null) {
                this.progress.setText(progress);
                if (isAutoDismiss && progress >= maxProgress) {
                    dismiss();
                }
            }
        }
    }
}
