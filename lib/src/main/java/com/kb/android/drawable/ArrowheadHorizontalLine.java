package com.kb.android.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;


public class ArrowheadHorizontalLine extends View {

    private int mLineColor = Color.BLACK;
    private float mLineWidth = 0;

    private int mArrowheadColor = Color.BLACK;

    private boolean mStartArrowhead = true;
    private boolean mEndArrowhead = true;


    private Paint mLinePaint;
    private Paint mArrowheadPaint;

    public ArrowheadHorizontalLine(Context context) {
        super(context);
        init(null, 0);
    }

    public ArrowheadHorizontalLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ArrowheadHorizontalLine(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // load attributes and init variables
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ArrowheadHorizontalLine, defStyle, 0);

        mLineColor = a.getColor(R.styleable.ArrowheadHorizontalLine_lineColor, mLineColor);
        mLineWidth = a.getDimension(R.styleable.ArrowheadHorizontalLine_lineWidth, mLineWidth);

        mArrowheadColor = a.getColor(R.styleable.ArrowheadHorizontalLine_arrowheadColor, mArrowheadColor);

        mStartArrowhead = a.getBoolean(R.styleable.ArrowheadHorizontalLine_startArrowhead, mStartArrowhead);
        mEndArrowhead = a.getBoolean(R.styleable.ArrowheadHorizontalLine_endArrowhead, mEndArrowhead);

        a.recycle();

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(getLineColor());
        mLinePaint.setStrokeWidth(getLineWidth());

        mArrowheadPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArrowheadPaint.setStyle(Paint.Style.FILL);
        mArrowheadPaint.setColor(getArrowheadColor());

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        Path mLinePath = new Path();
        mLinePath.moveTo(0 + paddingLeft, contentHeight / 2 + paddingTop);
        mLinePath.lineTo(contentWidth + paddingLeft, contentHeight / 2 + paddingTop);
        canvas.drawPath(mLinePath, mLinePaint);

        if (isStartArrowhead()) {
            canvas.drawPath(drawTriangle(1), mArrowheadPaint);
        }

        if (isEndArrowhead()) {
            canvas.drawPath(drawTriangle(-1), mArrowheadPaint);
        }
    }

    private Path drawTriangle(int arrowheadIndex) {
        Point p1 = new Point(0, 0), p2 = new Point(0, 0), p3 = new Point(0, 0);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        if (arrowheadIndex == 1) {
            p1 = new Point(Math.round(contentWidth * 0.95f), 0);
            p2 = new Point(Math.round(contentWidth * 0.95f), contentHeight);
            p3 = new Point(contentWidth, contentHeight / 2);
        }

        if (arrowheadIndex == -1) {
            p1 = new Point(Math.round(contentWidth * 0.05f), 0);
            p2 = new Point(Math.round(contentWidth * 0.05f), contentHeight);
            p3 = new Point(0, contentHeight / 2);
        }

        Path path = new Path();
        path.moveTo(p1.x + paddingLeft, p1.y + paddingTop);
        path.lineTo(p2.x + paddingLeft, p2.y + paddingTop);
        path.lineTo(p3.x + paddingLeft, p3.y + paddingTop);

        return path;
    }


    /**
     * Gets the line color value.
     * @return The line color value.
     */
    public int getLineColor() {
        return mLineColor;
    }

    /**
     * Sets the line color.
     * @param lineColor The line color value to use.
     */
    public void setLineColor(int lineColor) {
        mLineColor = lineColor;
        invalidateTextPaintAndMeasurements();
    }


    /**
     * Gets the line width value.
     * @return The line width value.
     */
    public float getLineWidth() {
        return mLineWidth;
    }

    /**
     * Sets the line width.
     * @param lineWidth The line width value to use.
     */
    public void setLineWidth(float lineWidth) {
        mLineWidth = lineWidth;
        invalidateTextPaintAndMeasurements();
    }


    /**
     * Gets the arrowhead color value.
     * @return The arrowhead color value.
     */
    public int getArrowheadColor() {
        return mArrowheadColor;
    }

    /**
     * Sets the arrowhead color.
     * @param arrowheadColor The arrowhead color value to use.
     */
    public void setArrowheadColor(int arrowheadColor) {
        mArrowheadColor = arrowheadColor;
        invalidateTextPaintAndMeasurements();
    }


    /**
     * Gets start arrowhead value.
     * @return The start arrowhead value.
     */
    public boolean isStartArrowhead() {
        return mStartArrowhead;
    }


    /**
     * Gets end arrowhead value.
     * @return The end arrowhead value.
     */
    public boolean isEndArrowhead() {
        return mEndArrowhead;
    }
}
