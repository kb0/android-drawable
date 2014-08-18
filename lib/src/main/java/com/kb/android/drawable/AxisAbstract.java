package com.kb.android.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kb on 06.08.2014.
 */
abstract public class AxisAbstract extends View {

    protected int mAxisLineColor = Color.BLACK;
    protected float mAxisLineWidth = 1.0f;

    protected int mMajorTicksCount = 10;
    protected float mMajorTicksAboveLength = 0.75f;
    protected float mMajorTicksBelowLength = 0.75f;

    protected int mMinorTicksCount = 4;
    protected float mMinorTicksAboveLength = 0.25f;
    protected float mMinorTicksBelowLength = 0.25f;

    protected boolean mIsLabels = true;
    protected String mLabelsFormat = "%.2f";
    protected int mLabelsColor = Color.BLACK;
    protected float mLabelsSize = 12.0f;

    protected float mMinimum = 0.0f;
    protected float mMaximum = 1.0f;

    protected float mMark = -1.0f;
    protected int mMarkStrokeColor = Color.RED;
    protected float mMarkStrokeWidth = 5.0f;

    protected boolean mIsMarkLabel = true;
    protected String mMarkLabelFormat = "%.2f";

    public AxisAbstract(Context context) {
        super(context);
        init(null, 0);
    }

    public AxisAbstract(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public AxisAbstract(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // load attributes and init variables
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Axis, defStyle, 0);

        mAxisLineColor = a.getColor(R.styleable.Axis_axisColor, mAxisLineColor);
        mAxisLineWidth = a.getDimension(R.styleable.Axis_axisWidth, mAxisLineWidth);

        mMajorTicksCount = a.getInteger(R.styleable.Axis_majorTicks, mMajorTicksCount);
        mMajorTicksAboveLength = a.getDimension(R.styleable.Axis_majorTicksAboveLength, mMajorTicksAboveLength);
        mMajorTicksBelowLength = a.getDimension(R.styleable.Axis_majorTicksBelowLength, mMajorTicksBelowLength);
        mIsLabels = a.getBoolean(R.styleable.Axis_labels, mIsLabels);
        if (a.hasValue(R.styleable.Axis_labels)) {
            mLabelsFormat = a.getString(R.styleable.Axis_labels);

            mLabelsColor = a.getColor(R.styleable.Axis_labelsColor, mLabelsColor);
            mLabelsSize = a.getDimension(R.styleable.Axis_labelsSize, mLabelsSize);
        }

        mMinorTicksCount = a.getInteger(R.styleable.Axis_minorTicks, mMinorTicksCount);
        mMinorTicksAboveLength = a.getDimension(R.styleable.Axis_minorTicksAboveLength, mMinorTicksAboveLength);
        mMinorTicksBelowLength = a.getDimension(R.styleable.Axis_minorTicksBelowLength, mMinorTicksBelowLength);

        mMinimum = a.getFloat(R.styleable.Axis_minimum, mMinimum);
        mMaximum = a.getFloat(R.styleable.Axis_maximum, mMaximum);

        /*
        @TODO add mark support
        @TODO add multiply marks support
        <attr name="mark" format="float" />
        <attr name="markDrawable" format="reference" />
         */

        if (a.hasValue(R.styleable.Axis_mark)) {
            mMark = a.getFloat(R.styleable.Axis_mark, mMark);

            mIsMarkLabel = a.getBoolean(R.styleable.Axis_markLabel, mIsMarkLabel);
            if (a.hasValue(R.styleable.Axis_markLabelFormat)) {
                mMarkLabelFormat = a.getString(R.styleable.Axis_markLabelFormat);
            }

            mMarkStrokeColor = a.getColor(R.styleable.Axis_markStrokeColor, mMarkStrokeColor);
            mMarkStrokeWidth = a.getDimension(R.styleable.Axis_markStrokeWidth, mMarkStrokeWidth);
        }
    }

    protected int pointToPixel(float value, int length) {
        if ((value > mMaximum) || (value < mMinimum)) {
            return -1;
        }

        float percent = (value - mMinimum) / (mMaximum - mMinimum);
        return (int) (length * percent);
    }

    public void setMark(float mark) {
        mMark = mark;
        invalidate();
    }

    protected abstract void drawMinorTicks(int ticksCount, Rect rect, Canvas canvas, Paint paint);
}
