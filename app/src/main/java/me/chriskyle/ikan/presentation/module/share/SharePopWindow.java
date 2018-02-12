package me.chriskyle.ikan.presentation.module.share;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import me.chriskyle.library.design.tip.Tip;
import me.chriskyle.library.social.internal.PlatformType;
import me.chriskyle.library.social.SocialApi;
import me.chriskyle.library.social.listener.ShareListener;
import me.chriskyle.library.social.media.BaseShareMedia;
import me.chriskyle.library.toolkit.utils.DeviceUtils;
import me.chriskyle.library.toolkit.utils.DisplayUtil;

/**
 * Description :
 * <p>
 * Created by Chris Kyle on 2017/9/25.
 */
public class SharePopWindow extends PopupWindow implements View.OnClickListener {

    private RelativeLayout contentView;
    private Activity activity;

    private SocialApi socialApi;
    private ShareListener shareListener;
    private BaseShareMedia baseShareMedia;

    public SharePopWindow(Activity activity, BaseShareMedia baseShareMedia, ShareListener shareListener) {
        this.activity = activity;
        this.baseShareMedia = baseShareMedia;
        this.shareListener = shareListener;
        this.socialApi = SocialApi.Companion.getInstance();
    }

    public void showMoreWindow(View anchor) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.dialog_share, null);
        int h = DisplayUtil.getScreenHeight(activity);
        int w = DisplayUtil.getScreenWidth(activity);
        setContentView(rootView);
        this.setWidth(w);
        this.setHeight(h - DeviceUtils.getStatusBarHeight(activity));

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

            if (child.getId() == R.id.close || child.getId() == R.id.title) {
                continue;
            }

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

            if (child.getId() == R.id.close) {
                continue;
            }

            Observable.timer((layout.getChildCount() - i - 1) * 50, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) {
                            child.setVisibility(View.VISIBLE);
                            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 0, 600);
                            fadeAnim.setDuration(300);
                            ShareAnimator animator = new ShareAnimator();
                            animator.setDuration(150);
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

            if (child.getId() == R.id.title) {
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
        if (isShowing()) {
            closeAnimation(contentView);
        }

        if (v.getId() == R.id.close) {
            return;
        }

        switch (v.getId()) {
            case R.id.wx_friends:
                share(PlatformType.WX);
                break;
            case R.id.wx_timeline:
                share(PlatformType.WX_TIMELINE);
                break;
            case R.id.wb:
                share(PlatformType.WB);
                break;
            case R.id.qq_friends:
                share(PlatformType.QQ);
                break;
            case R.id.qq_zone:
                share(PlatformType.QQ_ZONE);
                break;
            case R.id.paste_link:
                pasteLink();
            default:
        }
    }

    private void share(PlatformType platformType) {
        socialApi.doShare(activity, platformType, baseShareMedia, shareListener);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        socialApi.onActivityResult(requestCode, resultCode, data);
    }

    public void release() {
    }

    private void pasteLink() {
        Tip.showTip(activity, activity.getString(R.string.paste_link_success));
    }
}
