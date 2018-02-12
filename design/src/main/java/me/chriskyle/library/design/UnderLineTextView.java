package me.chriskyle.library.design;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.util.AttributeSet;

/**
 * Description :
 * <p>
 * Created by Chris Kyle on 2017/9/25.
 */
public class UnderLineTextView extends AppCompatTextView {

    private Rect rect;
    private Paint linePaint;
    private int lineColor;
    private float density;
    private float lineStrokeWidth;

    public UnderLineTextView(Context context) {
        this(context, null, 0);
    }

    public UnderLineTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnderLineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        density = context.getResources().getDisplayMetrics().density;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.UnderlinedTextView, defStyleAttr, 0);
        lineColor = array.getColor(R.styleable.UnderlinedTextView_underlineColor, 0xFFFF0000);
        lineStrokeWidth = array.getDimension(R.styleable.UnderlinedTextView_underlineWidth, density * 3);
        array.recycle();

        rect = new Rect();
        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(lineStrokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int count = getLineCount();

        final Layout layout = getLayout();

        float x_start, x_stop, x_diff;
        int firstCharInLine, lastCharInLine;

        for (int i = 0; i < count; i++) {
            int baseline = getLineBounds(i, rect);
            firstCharInLine = layout.getLineStart(i);
            lastCharInLine = layout.getLineEnd(i);

            x_start = layout.getPrimaryHorizontal(firstCharInLine);
            x_diff = layout.getPrimaryHorizontal(firstCharInLine) - x_start;
            x_stop = layout.getPrimaryHorizontal(lastCharInLine) + x_diff;

            canvas.drawLine(x_start, baseline + 5 + lineStrokeWidth, x_stop, baseline + 5 + lineStrokeWidth, linePaint);
        }

        super.onDraw(canvas);
    }

    public int getUnderLineColor() {
        return lineColor;
    }

    public void setUnderLineColor(int mColor) {
        this.lineColor = mColor;
        invalidate();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom + (int) lineStrokeWidth);
    }

    public float getUnderlineWidth() {
        return lineStrokeWidth;
    }

    public void setUnderlineWidth(float mStrokeWidth) {
        this.lineStrokeWidth = mStrokeWidth;
        invalidate();
    }
}
