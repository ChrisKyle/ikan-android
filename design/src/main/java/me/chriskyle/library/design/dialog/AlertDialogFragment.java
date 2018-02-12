package me.chriskyle.library.design.dialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import me.chriskyle.library.design.R;

/**
 * Description :
 * <p/>
 * Created by Chris Kyle on 2017/2/14.
 */
public final class AlertDialogFragment extends DialogFragment {

    protected static final String KEY_ID = "DIALOG_ID";
    private static final String KEY_MESSAGE = "MESSAGE";
    private static final String KEY_POSITIVE = "POSITIVE";
    private static final String KEY_NEGATIVE = "NEGATIVE";
    private static final String KEY_TITLE = "TITLE";
    private static final String KEY_POSITIVE_INTENT = "POSITIVE_INTENT";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_AppCompat_Light_Dialog);
    }

    public static class Builder extends DialogBuilder {

        public Builder id(int id) {
            data.putInt(KEY_ID, id);
            return this;
        }

        public Builder setTitle(String title) {
            this.data.putString(KEY_TITLE, title);
            return this;
        }

        public static String getTitle(Bundle args) {
            return args.getString(KEY_TITLE);
        }

        public Builder setNegative(int id) {
            this.data.putInt(KEY_NEGATIVE, id);
            return this;
        }

        public static int getNegative(Bundle args) {
            return args.getInt(KEY_NEGATIVE, 0);
        }

        public Builder setPositive(int id) {
            this.data.putInt(KEY_POSITIVE, id);
            return this;
        }

        public static int getPositive(Bundle args) {
            return args.getInt(KEY_POSITIVE, 0);
        }

        public Builder setMessage(String message) {
            this.data.putString(KEY_MESSAGE, message);
            return this;
        }

        public static String getMessage(Bundle args) {
            return args.getString(KEY_MESSAGE);
        }

        public Builder setPositiveIntent(Intent intent) {
            this.data.putParcelable(KEY_POSITIVE_INTENT, intent);
            return this;
        }

        public static Intent getIntent(Bundle args, int which) {
            switch (which) {
                case -1:
                    return (Intent) args.getParcelable(KEY_POSITIVE_INTENT);
            }
            return null;
        }

        protected AlertDialogFragment newInstance() {
            return new AlertDialogFragment();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return LayoutInflater.from(getActivity()).inflate(R.layout.common_dialog, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        this.setStyle(DialogFragment.STYLE_NO_FRAME, 0);

        TextView vTitle = (TextView) view.findViewById(R.id.title);
        vTitle.setText(Builder.getTitle(args));

        TextView vMessage = (TextView) view.findViewById(R.id.message);
        vMessage.setText(Builder.getMessage(args));
        
        TextView vAction0 = (TextView) view.findViewById(R.id.action0);
        int positive = Builder.getPositive(args);
        if (positive > 0) {
            vAction0.setText(getString(positive));
        }
        bindActionListener(vAction0);

        TextView vAction1 = (TextView) view.findViewById(R.id.action1);
        int negative = Builder.getNegative(args);
        if (negative > 0) {
            vAction1.setText(getString(negative));
        }
        bindActionListener(vAction1);
    }

    protected void bindActionListener(View v) {
        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null == onDialogActionClickListener) {
                    dismiss();
                } else {
                    if (onDialogActionClickListener.onDialogActionClick(getArguments().getInt(KEY_ID), v)) {
                        dismiss();
                    }
                }
            }
        });
    }

    public AlertDialogFragment setOnDialogActionClickListener(OnDialogActionClickListener onDialogActionClickListener) {
        this.onDialogActionClickListener = onDialogActionClickListener;
        return this;
    }

    private OnDialogActionClickListener onDialogActionClickListener;

    public interface OnDialogActionClickListener {

        boolean onDialogActionClick(int whichDialog, View view);
    }
}
