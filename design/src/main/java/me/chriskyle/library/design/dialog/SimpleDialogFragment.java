package me.chriskyle.library.design.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import me.chriskyle.library.design.R;

/**
 * Description :
 * <p/>
 * Created by Chris Kyle on 2017/2/14.
 */
public abstract class SimpleDialogFragment extends DialogFragment {

    public static abstract class SimpleDialogBuilder<T extends SimpleDialogFragment> {

        protected Bundle data;
        protected OnDialogActionClickListener actionClickListener;

        public SimpleDialogBuilder() {
            data = new Bundle();
        }

        public SimpleDialogBuilder<T> id(int id) {
            data.putInt(K_ID, id);
            return this;
        }

        public SimpleDialogBuilder<T> title(CharSequence title) {
            data.putString(K_TITLE, title.toString());
            return this;
        }

        public SimpleDialogBuilder<T> message(CharSequence message) {
            data.putString(K_MESSAGE, message.toString());
            return this;
        }

        public SimpleDialogBuilder<T> hint(CharSequence hint) {
            data.putString(K_HINT, hint.toString());
            return this;
        }

        public SimpleDialogBuilder<T> positive(CharSequence positive) {
            data.putString(K_POSITIVE, positive.toString());
            return this;
        }

        public SimpleDialogBuilder<T> negative(CharSequence negative) {
            data.putString(K_NEGATIVE, negative.toString());
            return this;
        }

        public SimpleDialogBuilder<T> actionAbove(CharSequence actionAbove) {
            data.putString(K_ACTION_ABOVE, actionAbove.toString());
            return this;
        }

        public SimpleDialogBuilder<T> actionMiddle(CharSequence actionMiddle) {
            data.putString(K_ACTION_MIDDLE, actionMiddle.toString());
            return this;
        }

        public SimpleDialogBuilder<T> actionBelow(CharSequence actionBelow) {
            data.putString(K_ACTION_BELOW, actionBelow.toString());
            return this;
        }

        public SimpleDialogBuilder<T> dialogActionClickListener(OnDialogActionClickListener listener) {
            this.actionClickListener = listener;
            return this;
        }

        public abstract T create();
    }

    public interface OnDialogActionClickListener {

        boolean onDialogActionClick(int whichDialog, View view, String message);
    }

    protected static final String K_ID = "ck.dialog.id";
    protected static final String K_TITLE = "ck.dialog.title";
    protected static final String K_MESSAGE = "ck.dialog.message";
    protected static final String K_HINT = "ck.dialog.hint";
    protected static final String K_POSITIVE = "ck.dialog.positive";
    protected static final String K_NEGATIVE = "ck.dialog.negative";
    protected static final String K_ACTION_ABOVE = "ck.dialog.action.above";
    protected static final String K_ACTION_MIDDLE = "ck.dialog.action.middle";
    protected static final String K_ACTION_BELOW = "ck.dialog.action.below";

    private OnDialogActionClickListener onDialogActionClickListener;
    private TextView message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_AppCompat_Light_Dialog);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_FRAME, 0);

        TextView vTitle = (TextView) view.findViewById(R.id.v4_dialog_title);
        String title = getArguments().getString(K_TITLE);
        if (!TextUtils.isEmpty(title)) {
            vTitle.setVisibility(View.VISIBLE);
            vTitle.setText(title);
        } else {
            vTitle.setVisibility(View.GONE);
        }

        message = (TextView) view.findViewById(R.id.v4_dialog_message);
        String message = getArguments().getString(K_MESSAGE);
        if (!TextUtils.isEmpty(message)) {
            this.message.setVisibility(View.VISIBLE);
            this.message.setText(message);
        } else {
            this.message.setVisibility(View.GONE);
        }

        String hint = getArguments().getString(K_HINT);
        if (!TextUtils.isEmpty(hint)) {
            this.message.setHint(hint);
        }

        TextView vPositive = (TextView) view.findViewById(R.id.v4_dialog_action_positive);
        String positive = getArguments().getString(K_POSITIVE);
        if (!TextUtils.isEmpty(positive)) {
            vPositive.setVisibility(View.VISIBLE);
            vPositive.setText(positive);
            bindActionListener(vPositive);
        } else {
            vPositive.setVisibility(View.GONE);
        }

        TextView vNegative = (TextView) view.findViewById(R.id.v4_dialog_action_negative);
        String negative = getArguments().getString(K_NEGATIVE);
        if (!TextUtils.isEmpty(negative)) {
            vNegative.setVisibility(View.VISIBLE);
            vNegative.setText(negative);
            bindActionListener(vNegative);
        } else {
            vNegative.setVisibility(View.GONE);
        }

        TextView vCancel = (TextView) view.findViewById(R.id.v4_dialog_cancel);
        if (null != vCancel) {
            bindActionListener(vCancel);
        }
    }

    protected void bindActionListener(View v) {
        v.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onDialogActionClickListener == null) {
                    dismiss();
                } else {
                    if (onDialogActionClickListener.onDialogActionClick(getArguments().getInt(K_ID), v, message.getText().toString())) {
                        dismiss();
                    }
                }
            }
        });
    }

    protected SimpleDialogFragment setOnDialogActionClickListener(OnDialogActionClickListener l) {
        onDialogActionClickListener = l;
        return this;
    }
}
