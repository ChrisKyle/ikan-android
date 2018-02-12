package me.chriskyle.library.design.fragmentanim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.chriskyle.library.design.R;

public class FragmentTransactionWrapper implements FragmentManager.OnBackStackChangedListener {

    private boolean didSlideOut = false;
    private boolean isAnimating = false;
    private FragmentTransaction fragmentTransaction;
    private Context context;
    private Fragment firstFragment, secondFragment;
    private int containerID;
    private int transitionType;

    public static final int SCALEX = 0;
    public static final int SCALEY = 1;
    public static final int SCALEXY = 2;
    public static final int FADE = 3;
    public static final int FLIP_HORIZONTAL = 4;
    public static final int FLIP_VERTICAL = 5;
    public static final int SLIDE_VERTICAL = 6;
    public static final int SLIDE_HORIZONTAL = 7;
    public static final int SLIDE_HORIZONTAL_PUSH_TOP = 8;
    public static final int SLIDE_VERTICAL_PUSH_LEFT = 9;
    public static final int GLIDE = 10;
    public static final int SLIDING = 11;
    public static final int STACK = 12;
    public static final int CUBE = 13;
    public static final int ROTATE_DOWN = 14;
    public static final int ROTATE_UP = 15;
    public static final int ACCORDION = 16;
    public static final int TABLE_HORIZONTAL = 17;
    public static final int TABLE_VERTICAL = 18;
    public static final int ZOOM_FROM_LEFT_CORNER = 19;
    public static final int ZOOM_FROM_RIGHT_CORNER = 20;

    public FragmentTransactionWrapper(Context context, FragmentTransaction fragmentTransaction, Fragment firstFragment, Fragment secondFragment, int containerID) {
        this.fragmentTransaction = fragmentTransaction;
        this.context = context;
        this.firstFragment = firstFragment;
        this.secondFragment = secondFragment;
        this.containerID = containerID;
    }

    public void addTransition(int transitionType) {
        this.transitionType = transitionType;
        switch (transitionType) {
            case SCALEX:
                transitionScaleX();
                break;
            case SCALEY:
                transitionScaleY();
                break;
            case SCALEXY:
                transitionScaleXY();
                break;
            case FADE:
                transitionFade();
                break;
            case FLIP_HORIZONTAL:
                transitionFlipHorizontal();
                break;
            case FLIP_VERTICAL:
                transitionFlipVertical();
                break;
            case SLIDE_VERTICAL:
                transitionSlideVertical();
                break;
            case SLIDE_HORIZONTAL:
                transitionSlideHorizontal();
                break;
            case SLIDE_HORIZONTAL_PUSH_TOP:
                transitionSlideHorizontalPushTop();
                break;
            case SLIDE_VERTICAL_PUSH_LEFT:
                transitionSlideVerticalPushLeft();
                break;
            case GLIDE:
                transitionGlide();
                break;
            case SLIDING:
                return;
            case STACK:
                transitionStack();
                break;
            case CUBE:
                transitionCube();
                break;
            case ROTATE_DOWN:
                transitionRotateDown();
                break;
            case ROTATE_UP:
                transitionRotateUp();
                break;
            case ACCORDION:
                transitionAccordion();
                break;
            case TABLE_HORIZONTAL:
                transitionTableHorizontal();
                break;
            case TABLE_VERTICAL:
                transitionTableVertical();
                break;
            case ZOOM_FROM_LEFT_CORNER:
                transitionZoomFromLeftCorner();
                break;
            case ZOOM_FROM_RIGHT_CORNER:
                transitionZoomFromRightCorner();
                break;
        }
        fragmentTransaction.replace(containerID, secondFragment);
    }

    private void transitionFade() {
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
    }

    private void transitionScaleX() {
        fragmentTransaction.setCustomAnimations(R.animator.scalex_enter, R.animator.scalex_exit, R.animator.scalex_enter, R.animator.scalex_exit);
    }

    private void transitionScaleY() {
        fragmentTransaction.setCustomAnimations(R.animator.scaley_enter, R.animator.scaley_exit, R.animator.scaley_enter, R.animator.scaley_exit);
    }

    private void transitionScaleXY() {
        fragmentTransaction.setCustomAnimations(R.animator.scalexy_enter, R.animator.scalexy_exit, R.animator.scalexy_enter, R.animator.scalexy_exit);
    }

    private void transitionSlideVertical() {
        fragmentTransaction.setCustomAnimations(R.animator.slide_fragment_vertical_right_in, R.animator.slide_fragment_vertical_left_out, R.animator.slide_fragment_vertical_left_in, R.animator.slide_fragment_vertical_right_out);
    }

    private void transitionSlideHorizontal() {
        fragmentTransaction.setCustomAnimations(R.animator.slide_fragment_horizontal_right_in, R.animator.slide_fragment_horizontal_left_out, R.animator.slide_fragment_horizontal_left_in, R.animator.slide_fragment_horizontal_right_out);
    }

    private void transitionSlideHorizontalPushTop() {
        fragmentTransaction.setCustomAnimations(R.animator.slide_fragment_horizontal_right_in, R.animator.slide_fragment_vertical_left_out, R.animator.slide_fragment_vertical_left_in, R.animator.slide_fragment_horizontal_right_out);
    }

    private void transitionSlideVerticalPushLeft() {
        fragmentTransaction.setCustomAnimations(R.animator.slide_fragment_vertical_right_in, R.animator.slide_fragment_horizontal_left_out, R.animator.slide_fragment_horizontal_left_in, R.animator.slide_fragment_vertical_right_out);
    }

    private void transitionGlide() {
        fragmentTransaction.setCustomAnimations(R.animator.glide_fragment_horizontal_in, R.animator.glide_fragment_horizontal_out, R.animator.glide_fragment_horizontal_in, R.animator.glide_fragment_horizontal_out);
    }

    private void transitionStack() {
        fragmentTransaction.setCustomAnimations(R.animator.stack_right_in, R.animator.stack_left_out, R.animator.stack_left_in, R.animator.stack_right_out);
    }

    private void transitionCube() {
        fragmentTransaction.setCustomAnimations(R.animator.cube_right_in, R.animator.cube_left_out, R.animator.cube_left_in, R.animator.cube_right_out);
    }

    private void transitionRotateDown() {
        fragmentTransaction.setCustomAnimations(R.animator.rotatedown_right_in, R.animator.rotatedown_left_out, R.animator.rotatedown_left_in, R.animator.rotatedown_right_out);
    }

    private void transitionRotateUp() {
        fragmentTransaction.setCustomAnimations(R.animator.rotateup_right_in, R.animator.rotateup_left_out, R.animator.rotateup_left_in, R.animator.rotateup_right_out);
    }

    private void transitionAccordion() {
        fragmentTransaction.setCustomAnimations(R.animator.accordion_right_in, R.animator.accordion_left_out, R.animator.accordion_left_in, R.animator.accordion_right_out);
    }

    private void transitionTableHorizontal() {
        fragmentTransaction.setCustomAnimations(R.animator.table_horizontal_right_in, R.animator.table_horizontal_left_out, R.animator.table_horizontal_left_int, R.animator.table_horizontal_right_out);
    }

    private void transitionTableVertical() {
        fragmentTransaction.setCustomAnimations(R.animator.table_vertical_right_in, R.animator.table_vertical_left_out, R.animator.table_vertical_left_int, R.animator.table_vertical_right_out);
    }

    private void transitionFlipHorizontal() {
        fragmentTransaction.setCustomAnimations(R.animator.card_flip_horizontal_right_in, R.animator.card_flip_horizontal_left_out, R.animator.card_flip_horizontal_left_in, R.animator.card_flip_horizontal_right_out);
    }

    private void transitionFlipVertical() {
        fragmentTransaction.setCustomAnimations(R.animator.card_flip_vertical_right_in, R.animator.card_flip_vertical_left_out, R.animator.card_flip_vertical_left_in, R.animator.card_flip_vertical_right_out);
    }

    private void transitionZoomFromLeftCorner() {
        fragmentTransaction.setCustomAnimations(R.animator.zoom_from_left_corner_right_in, R.animator.zoom_from_left_corner_left_out, R.animator.zoom_from_left_corner_left_in, R.animator.zoom_from_left_corner_right_out);
    }

    private void transitionZoomFromRightCorner() {
        fragmentTransaction.setCustomAnimations(R.animator.zoom_from_right_corner_right_in, R.animator.zoom_from_right_corner_left_out, R.animator.zoom_from_right_corner_left_in, R.animator.zoom_from_right_corner_right_out);
    }

    private void switchFragments() {
        ((AppCompatActivity) this.context).getSupportFragmentManager().addOnBackStackChangedListener(this);

        if (isAnimating) {
            return;
        }
        isAnimating = true;
        if (didSlideOut) {
            didSlideOut = false;
            ((Activity) context).getFragmentManager().popBackStack();
        } else {
            didSlideOut = true;
            Animator.AnimatorListener listener = new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator arg0) {
                    fragmentTransaction.setCustomAnimations(R.animator.slide_fragment_in, 0, 0, R.animator.slide_fragment_out);
                    fragmentTransaction.add(containerID, secondFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            };
            slideBack(listener);
        }
    }

    public void slideBack(Animator.AnimatorListener listener) {
        View movingFragmentView = firstFragment.getView();
        movingFragmentView.setPivotY(movingFragmentView.getHeight() / 2);
        movingFragmentView.setPivotX(movingFragmentView.getWidth() / 2);

        PropertyValuesHolder rotateX = PropertyValuesHolder.ofFloat("rotationX", 40f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.8f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.8f);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0.5f);
        ObjectAnimator movingFragmentAnimator = ObjectAnimator.ofPropertyValuesHolder(movingFragmentView, rotateX, scaleX, scaleY, alpha);

        ObjectAnimator movingFragmentRotator = ObjectAnimator.ofFloat(movingFragmentView, "rotationX", 0);
        movingFragmentRotator.setStartDelay(context.getResources().getInteger(R.integer.half_slide_up_down_duration));

        AnimatorSet s = new AnimatorSet();
        s.playTogether(movingFragmentAnimator, movingFragmentRotator);
        s.addListener(listener);
        s.start();
    }

    public void slideForward() {
        View movingFragmentView = firstFragment.getView();
        PropertyValuesHolder rotateX = PropertyValuesHolder.ofFloat("rotationX", 40f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f);
        ObjectAnimator movingFragmentAnimator = ObjectAnimator.ofPropertyValuesHolder(movingFragmentView, rotateX, scaleX, scaleY, alpha);

        ObjectAnimator movingFragmentRotator = ObjectAnimator.ofFloat(movingFragmentView, "rotationX", 0);
        movingFragmentRotator.setStartDelay(context.getResources().getInteger(R.integer.half_slide_up_down_duration));

        AnimatorSet s = new AnimatorSet();
        s.playTogether(movingFragmentAnimator, movingFragmentRotator);
        s.setStartDelay(context.getResources().getInteger(R.integer.slide_up_down_duration));
        s.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                didSlideOut = true;
            }
        });
        s.start();
        ((AppCompatActivity) this.context).getSupportFragmentManager().removeOnBackStackChangedListener(this);
    }


    public void commit() {
        switch (transitionType) {
            case SLIDING:
                switchFragments();
                break;
            default:
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public void onBackStackChanged() {
        switch (transitionType) {
            case SLIDING:
                if (!didSlideOut) {
                    slideForward();
                } else {
                    didSlideOut = false;
                }
                break;
        }
    }
}
