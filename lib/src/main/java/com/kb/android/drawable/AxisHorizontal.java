package com.kb.android.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kb on 06.08.2014.
 */
public class AxisHorizontal extends View {

    private int mAxisLineColor = Color.BLACK;
    private float mAxisLineWidth = 1.0f;

    private int mMajorTicksCount = 10;
    private float mMajorTicksAboveLength = 0.75f;
    private float mMajorTicksBelowLength = 0.75f;
    private boolean mIsMajorLabels = true;
    private String mMajorLabelsFormat = "%.2f";

    private int mMinorTicksCount = 4;
    private float mMinorTicksAboveLength = 0.25f;
    private float mMinorTicksBelowLength = 0.25f;
    private boolean mIsMinorLabels = false;
    private String mMinorLabelsFormat = "%.2f";

    private float mMinimum = 0.0f;
    private float mMaximum = 1.0f;


    public AxisHorizontal(Context context) {
        super(context);
        init(null, 0);
    }

    public AxisHorizontal(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public AxisHorizontal(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // load attributes and init variables
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AxisHorizontal, defStyle, 0);

        mAxisLineColor = a.getColor(R.styleable.AxisHorizontal_axisColor, mAxisLineColor);
        mAxisLineWidth = a.getDimension(R.styleable.AxisHorizontal_axisWidth, mAxisLineWidth);

        mMajorTicksCount = a.getInteger(R.styleable.AxisHorizontal_majorTicks, mMajorTicksCount);
        mMajorTicksAboveLength = a.getDimension(R.styleable.AxisHorizontal_majorTicksAboveLength, mMajorTicksAboveLength);
        mMajorTicksBelowLength = a.getDimension(R.styleable.AxisHorizontal_majorTicksBelowLength, mMajorTicksBelowLength);
        mIsMajorLabels = a.getBoolean(R.styleable.AxisHorizontal_majorLabels, mIsMajorLabels);
        if (a.hasValue(R.styleable.AxisHorizontal_majorLabels)) {
            mMajorLabelsFormat = a.getString(R.styleable.AxisHorizontal_majorLabels);
        }

        mMinorTicksCount = a.getInteger(R.styleable.AxisHorizontal_minorTicks, mMinorTicksCount);
        mMinorTicksAboveLength = a.getDimension(R.styleable.AxisHorizontal_minorTicksAboveLength, mMinorTicksAboveLength);
        mMinorTicksBelowLength = a.getDimension(R.styleable.AxisHorizontal_minorTicksBelowLength, mMinorTicksBelowLength);
        mIsMinorLabels = a.getBoolean(R.styleable.AxisHorizontal_minorLabels, mIsMinorLabels);
        if (a.hasValue(R.styleable.AxisHorizontal_minorLabels)) {
            mMinorLabelsFormat = a.getString(R.styleable.AxisHorizontal_minorLabels);
        }

        mMinimum = a.getFloat(R.styleable.AxisHorizontal_minimum, mMinimum);
        mMaximum = a.getFloat(R.styleable.AxisHorizontal_maximum, mMaximum);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        int baseline = contentHeight / 2 + paddingTop;
        int majorTicksY1 = (int) (baseline - mMajorTicksAboveLength * contentHeight / 2.0);
        int majorTicksY2 = (int) (baseline + mMajorTicksBelowLength * contentHeight / 2.0);

        int minorTicksY1 = (int) (baseline - mMinorTicksAboveLength * contentHeight / 2.0);
        int minorTicksY2 = (int) (baseline + mMinorTicksBelowLength * contentHeight / 2.0);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mAxisLineColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mAxisLineWidth);

        // draw line
        Path linePath = new Path();
        linePath.moveTo(0 + paddingLeft, baseline);
        linePath.lineTo(contentWidth + paddingLeft, baseline);
        canvas.drawPath(linePath, paint);

        // draw major & minors ticks
        if (mMajorTicksCount > 0) {
            float majorLabelStep = (mMaximum - mMinimum) / (mMajorTicksCount + 1);
            float majorValueStep = contentWidth / (mMajorTicksCount + 1);

            for(int i = 1; i < mMajorTicksCount + 1; i++) {
                if (mMajorTicksAboveLength > 0 || mMajorTicksBelowLength > 0) {
                    Path tickPath = new Path();
                    tickPath.moveTo(paddingLeft + i * majorValueStep, majorTicksY1);
                    tickPath.lineTo(paddingLeft + i * majorValueStep, majorTicksY2);
                    canvas.drawPath(tickPath, paint);

                    if (mMinorTicksCount > 0) {
                        Rect rect = new Rect((int) (paddingLeft + (i - 1) * majorValueStep), minorTicksY1, (int) (paddingLeft + i * majorValueStep), minorTicksY2);
                        drawHorizontalTicks(mMinorTicksCount, rect, canvas, paint);
                    }
                }
            }
        }

        // draw labels
    }

    protected void drawHorizontalTicks(int ticksCount, Rect rect, Canvas canvas, Paint paint) {
        float valueStep = rect.width() / (ticksCount + 1);
        for(int i = 1; i < ticksCount + 1; i++) {
            Path tickPath = new Path();
            tickPath.moveTo(rect.left + i * valueStep, rect.top);
            tickPath.lineTo(rect.left + i * valueStep, rect.bottom);
            canvas.drawPath(tickPath, paint);
        }
    }
}
