package me.chriskyle.ikan.presentation.module.account.login;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.chriskyle.ikan.R;
import me.chriskyle.ikan.presentation.module.share.ShareAnimator;
import me.chriskyle.library.toolkit.utils.DeviceUtils;
import me.chriskyle.library.toolkit.utils.DisplayUtil;

/**
 * Description :
 * <p>
 * Created by Chris Kyle on 2017/9/25.
 */
public class LoginPopWindow extends PopupWindow implements View.OnClickListener {

    private RelativeLayout contentView;
    private Context context;

    public LoginPopWindow(Context context) {
        this.context = context;
    }

    public void showMoreWindow(View anchor) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.dialog_share, null);
        int h = DisplayUtil.getScreenHeight(context);
        int w = DisplayUtil.getScreenWidth(context);
        setContentView(rootView);
        this.setWidth(w);
        this.setHeight(h - DeviceUtils.getStatusBarHeight(context));

        contentView = rootView.findViewById(R.id.contentView);

        View close = rootView.findViewById(R.id.close);
        close.setOnClickListener(this);
        showAnimation(contentView);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setOutsideTouchable(true);
        setFocusable(true);
        showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
    }

    private void showAnimation(ViewGroup layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);

            child.setOnClickListener(this);
            child.setVisibility(View.INVISIBLE);
            Observable.timer(i * 50, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) {
                            child.setVisibility(View.VISIBLE);
                            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                            fadeAnim.setDuration(300);
                            ShareAnimator animator = new ShareAnimator();
                            animator.setDuration(150);
                            fadeAnim.setEvaluator(animator);
                            fadeAnim.start();
                        }
                    });
        }
    }

    private void closeAnimation(ViewGroup layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);

            Observable.timer((layout.getChildCount() - i - 1) * 30, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) {
                            child.setVisibility(View.VISIBLE);
                            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 0, 600);
                            fadeAnim.setDuration(200);
                            ShareAnimator animator = new ShareAnimator();
                            animator.setDuration(100);
                            fadeAnim.setEvaluator(animator);
                            fadeAnim.start();
                            fadeAnim.addListener(new Animator.AnimatorListener() {

                                @Override
                                public void onAnimationStart(Animator animation) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    child.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                }
                            });
                        }
                    });


            if (child.getId() == R.id.close) {
                Observable.timer((layout.getChildCount() - i) * 30 + 60, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                dismiss();
                            }
                        });
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wx_timeline:
            case R.id.wx_friends:
            case R.id.wb:
            case R.id.qq_friends:
            case R.id.qq_zone:
            case R.id.close:
                if (isShowing()) {
                    closeAnimation(contentView);
                }
                break;
            default:
                break;
        }
    }
}
