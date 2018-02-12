package me.chriskyle.library.design.table;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.chriskyle.library.design.R;

/**
 * Description :
 * <p/>
 * Created by Chris Kyle on 2017/2/14.
 */
public final class TableRow extends RelativeLayout {

    private static final int IN_VALID_RES_ID = 0;

    private String primaryTitleString;
    private String secondaryTitleString;
    private int leftIconRes;
    private int rightIconRes;
    private float primaryTitleSize;
    private float secondaryTitleSize;
    private int primaryTitleColor;
    private int secondaryTitleColor;

    private TextView primaryTitle;
    private TextView secondaryTitle;
    private ImageView leftIcon;
    private ImageView rightIcon;

    public TableRow(Context context) {
        super(context);
    }

    public TableRow(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs);
    }

    public TableRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.table_row, this);
        primaryTitle = (TextView) rootView.findViewById(R.id.primary_title);
        secondaryTitle = (TextView) rootView.findViewById(R.id.secondary_title);
        leftIcon = (ImageView) rootView.findViewById(R.id.left_icon);
        rightIcon = (ImageView) rootView.findViewById(R.id.right_icon);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.table_row);
        bindViewAttrs(typedArray);
    }

    private void bindViewAttrs(TypedArray typedArray) {
        primaryTitleString = typedArray.getString(R.styleable.table_row_primary_title);
        secondaryTitleString = typedArray.getString(R.styleable.table_row_secondary_title);
        leftIconRes = typedArray.getResourceId(R.styleable.table_row_left_icon, IN_VALID_RES_ID);
        rightIconRes = typedArray.getResourceId(R.styleable.table_row_right_icon, IN_VALID_RES_ID);
        primaryTitleSize = typedArray.getDimension(R.styleable.table_row_primary_title_size, IN_VALID_RES_ID);
        secondaryTitleSize = typedArray.getDimension(R.styleable.table_row_secondary_title_size, IN_VALID_RES_ID);
        primaryTitleColor = typedArray.getColor(R.styleable.table_row_primary_title_color, IN_VALID_RES_ID);
        secondaryTitleColor = typedArray.getColor(R.styleable.table_row_secondary_title_color, IN_VALID_RES_ID);

        typedArray.recycle();
        bindViewAttrsValue();
    }

    private void bindViewAttrsValue() {
        if (!TextUtils.isEmpty(primaryTitleString)) {
            primaryTitle.setText(primaryTitleString);
        }

        if (!TextUtils.isEmpty(secondaryTitleString)) {
            secondaryTitle.setText(secondaryTitleString);
        }

        if (leftIconRes != IN_VALID_RES_ID) {
            leftIcon.setVisibility(VISIBLE);
            leftIcon.setImageResource(leftIconRes);
        } else {
            leftIcon.setVisibility(GONE);
        }

        if (rightIconRes != IN_VALID_RES_ID) {
            rightIcon.setVisibility(VISIBLE);
            rightIcon.setImageResource(rightIconRes);
        } else {
            rightIcon.setVisibility(GONE);
        }

        if (primaryTitleSize != IN_VALID_RES_ID) {
            primaryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, primaryTitleSize);
        }

        if (secondaryTitleSize != IN_VALID_RES_ID) {
            secondaryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, secondaryTitleSize);
        }

        if (primaryTitleColor != IN_VALID_RES_ID) {
            primaryTitle.setTextColor(primaryTitleColor);
        }

        if (secondaryTitleColor != IN_VALID_RES_ID) {
            secondaryTitle.setTextColor(secondaryTitleColor);
        }
    }

    public void setPrimaryTitle(String primaryTitleString) {
        primaryTitle.setText(primaryTitleString);
    }

    public void setSecondaryTitle(String secondaryTitleString) {
        secondaryTitle.setText(secondaryTitleString);
    }
}
