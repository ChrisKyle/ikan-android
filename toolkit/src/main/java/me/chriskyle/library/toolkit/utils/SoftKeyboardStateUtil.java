package me.chriskyle.library.toolkit.utils;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.LinkedList;
import java.util.List;

/**
 * Description :
 * <p/>
 * Created by Chris Kyle on 2016/8/24.
 */
public final class SoftKeyboardStateUtil implements ViewTreeObserver.OnGlobalLayoutListener {

    public interface SoftKeyboardStateListener {

        void onSoftKeyboardOpened(int keyboardHeightInPx);

        void onSoftKeyboardClosed();
    }

    private final List<SoftKeyboardStateListener> mListeners = new LinkedList<>();
    private final View mActivityRootView;

    public SoftKeyboardStateUtil(View activityRootView) {
        this(activityRootView, false);
    }

    public SoftKeyboardStateUtil(View activityRootView, boolean isSoftKeyboardOpened) {
        mActivityRootView = activityRootView;

        if (null != activityRootView)
            activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        final ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final Rect rect = new Rect();
                mActivityRootView.getWindowVisibleDisplayFrame(rect);
                final int screenHeight = mActivityRootView.getRootView().getHeight();
                final int heightDifference = screenHeight - rect.bottom;
                boolean visible = heightDifference > screenHeight / 3;

                if (visible) {
                    notifyOnSoftKeyboardOpened(heightDifference);
                } else {
                    notifyOnSoftKeyboardClosed();
                }
            }
        };
        mActivityRootView.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
    }

    public void addSoftKeyboardStateListener(SoftKeyboardStateListener listener) {
        mListeners.add(listener);
    }

    public void removeSoftKeyboardStateListener(SoftKeyboardStateListener listener) {
        mListeners.remove(listener);
    }

    private void notifyOnSoftKeyboardOpened(int keyboardHeightInPx) {
        for (SoftKeyboardStateListener listener : mListeners) {
            if (null != listener) {
                listener.onSoftKeyboardOpened(keyboardHeightInPx);
            }
        }
    }

    private void notifyOnSoftKeyboardClosed() {
        for (SoftKeyboardStateListener listener : mListeners) {
            if (null != listener) {
                listener.onSoftKeyboardClosed();
            }
        }
    }
}
