package me.chriskyle.library.design.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import me.chriskyle.library.design.R;

/**
 * Description :
 * <p/>
 * Created by Chris Kyle on 2017/2/14.
 */
public final class MessageDialogFragment extends SimpleDialogFragment {

    public static final class MessageDialogBuilder extends SimpleDialogBuilder<MessageDialogFragment> {

        public MessageDialogBuilder() {
            super();
        }

        public MessageDialogFragment create() {
            MessageDialogFragment fragment = new MessageDialogFragment();
            fragment.setArguments(data);
            fragment.setOnDialogActionClickListener(actionClickListener);
            return fragment;
        }
    }

    public MessageDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return inflater.inflate(R.layout.fragment_dialog_message, container, false);
    }
}