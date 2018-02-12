package me.chriskyle.library.design.tip;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import me.chriskyle.library.design.R;

/**
 * Description :
 * <p/>
 * Created by Chris Kyle on 2017/1/6.
 */
public final class Tip {

    private static Toast tip;
    private static final String DEFAULT_STR = "--";

    public static void init(Context context) {
        tip = new Toast(context.getApplicationContext());
        tip.setDuration(Toast.LENGTH_SHORT);
        tip.setGravity(Gravity.CENTER, 0, 0);
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.tip, null);
        tip.setView(v);
    }

    public static void showTip(Context context, String message) {
        if (message == null) {
            message = DEFAULT_STR;
        }

        if (tip == null) {
            init(context);
        }

        TextView tv = (TextView) tip.getView().findViewById(R.id.global_tip_tv);
        if (tv != null) {
            tv.setText(message);
            tip.show();
        } else {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
