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


public class Triangle extends View {
    public static final int DIRECTION_TOP = 0;
    public static final int DIRECTION_BOTTOM = 1;
    public static final int DIRECTION_LEFT = 2;
    public static final int DIRECTION_RIGHT = 3;

    // TODO: use a default from R.color...
    private int mFillColor = Color.BLACK;

    // TODO: use a default from R.color...
    private int mStrokeColor = Color.BLACK;
    // TODO: use a default from R.dimen...
    private float mStrokeWidth = 0;

    private int mDirection = DIRECTION_TOP;

    private Path mTrianglePath = null;

    private Paint mTrianglePaintFill = null;
    private Paint mTrianglePaintStroke = null;

    public Triangle(Context context) {
        super(context);
        init(null, 0);
    }

    public Triangle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public Triangle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // load attributes and init variables
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Triangle, defStyle, 0);

        mFillColor = a.getColor(R.styleable.Triangle_fillColor, mFillColor);

        mStrokeColor = a.getColor(R.styleable.Triangle_fillColor, mStrokeColor);
        mStrokeWidth = a.getDimension(R.styleable.Triangle_strokeWidth, mStrokeWidth);

        mDirection = a.getInteger(R.styleable.Triangle_direction, mDirection);

        a.recycle();

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTrianglePaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTrianglePaintFill.setStyle(Paint.Style.FILL);
        mTrianglePaintFill.setColor(getFillColor());

        mTrianglePaintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTrianglePaintStroke.setStyle(Paint.Style.STROKE);
        mTrianglePaintStroke.setColor(getStrokeColor());

        mTrianglePaintStroke.setStrokeWidth(getStrokeWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce allocations per draw cycle.
        /*
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;
        */

        mTrianglePath = drawTriangle();
        canvas.drawPath(mTrianglePath, mTrianglePaintFill);
        canvas.drawPath(mTrianglePath, mTrianglePaintStroke);
    }

    private Path drawTriangle() {
        Point p1 = null, p2 = null, p3 = null;

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        p1 = new Point(0 + paddingLeft, 0 + paddingTop);
        p2 = new Point(contentWidth + paddingLeft, 0 + paddingTop);
        p3 = new Point(contentWidth / 2 + paddingLeft, contentHeight + paddingTop);


        Path path = new Path();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);

        return path;
    }


    /**
     * Gets the fill color value.
     * @return The fill color value.
     */
    public int getFillColor() {
        return mFillColor;
    }

    /**
     * Sets the triangle fill color.
     * @param fillColor The fill color value to use.
     */
    public void setFillColor(int fillColor) {
        mFillColor = fillColor;
        invalidateTextPaintAndMeasurements();
    }


    /**
     * Gets the fill color value.
     * @return The fill color value.
     */
    public int getStrokeColor() {
        return mStrokeColor;
    }

    /**
     * Sets the triangle fill color.
     * @param strokeColor The fill color value to use.
     */
    public void setStrokeColor(int strokeColor) {
        mStrokeColor = strokeColor;
        invalidateTextPaintAndMeasurements();
    }


    /**
     * Gets the fill color value.
     * @return The fill color value.
     */
    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    /**
     * Sets the triangle fill color.
     * @param strokeWidth The fill color value to use.
     */
    public void setStrokeWidth(float strokeWidth) {
        mStrokeWidth = strokeWidth;
        invalidateTextPaintAndMeasurements();
    }
}
