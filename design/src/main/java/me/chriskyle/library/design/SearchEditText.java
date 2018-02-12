package me.chriskyle.library.design;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Description :
 * <p/>
 * Created by Chris Kyle on 2017/4/2.
 */
public final class SearchEditText extends AppCompatEditText {

    private static final int ILLEGAL_RES_ID = -1;

    private boolean focused = false;
    private int iconResId;
    private int clearIconResId;

    public SearchEditText(Context context) {
        super(context);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.search_edit_text);
        iconResId = typedArray.getResourceId(R.styleable.search_edit_text_icon, ILLEGAL_RES_ID);
        clearIconResId = typedArray.getResourceId(R.styleable.search_edit_text_clear_icon, ILLEGAL_RES_ID);
        typedArray.recycle();

        if (iconResId != ILLEGAL_RES_ID) {
            setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), iconResId),
                    null, null, null);
        }

        addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                reDrawRightIcon();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        this.focused = focused;
        reDrawRightIcon();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    getText().clear();
                } else {
                    setFocusableInTouchMode(true);
                    setFocusable(true);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void reDrawRightIcon() {
        if (focused && !TextUtils.isEmpty(getText().toString())) {
            if (iconResId != ILLEGAL_RES_ID) {
                setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), iconResId),
                        null, ContextCompat.getDrawable(getContext(), clearIconResId), null);
            } else {
                setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getContext(), clearIconResId), null);
            }
        } else {
            if (iconResId != ILLEGAL_RES_ID) {
                setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), iconResId), null, null, null);
            } else {
                setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        }
    }
}
