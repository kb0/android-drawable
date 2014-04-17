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

    public enum Direction {
        TOP, BOTTOM, LEFT, RIGHT;

        public static Direction valueOf(int i) {
            switch (i) {
                case 0: return Direction.TOP;
                case 1: return Direction.BOTTOM;
                case 2: return Direction.LEFT;
                case 3: return Direction.RIGHT;

                default:
                    return Direction.TOP;
            }
        }
    }

    private Direction mDirection = Direction.TOP;

    private int mFillColor = Color.BLACK;

    private int mStrokeColor = Color.BLACK;
    private float mStrokeWidth = 0;


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

        mDirection = Direction.valueOf(a.getInteger(R.styleable.Triangle_direction, 0));

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

        Path mTrianglePath = drawTriangle();
        canvas.drawPath(mTrianglePath, mTrianglePaintFill);
        canvas.drawPath(mTrianglePath, mTrianglePaintStroke);
    }

    private Path drawTriangle() {
        Point p1 = new Point(0, 0), p2 = new Point(0, 0), p3 = new Point(0, 0);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        if (mDirection == Direction.BOTTOM) {
            p1 = new Point(0, 0);
            p2 = new Point(contentWidth, 0);
            p3 = new Point(contentWidth / 2, contentHeight);
        }

        if (mDirection == Direction.TOP) {
            p1 = new Point(0, contentHeight);
            p2 = new Point(contentWidth, contentHeight);
            p3 = new Point(contentWidth / 2, 0);
        }

        if (mDirection == Direction.RIGHT) {
            p1 = new Point(0, 0);
            p2 = new Point(0, contentHeight);
            p3 = new Point(contentWidth, contentHeight / 2);
        }

        if (mDirection == Direction.LEFT) {
            p1 = new Point(contentWidth, 0);
            p2 = new Point(contentWidth, contentHeight);
            p3 = new Point(0, contentHeight / 2);
        }

        Path path = new Path();
        path.moveTo(p1.x + paddingLeft, p1.y + paddingTop);
        path.lineTo(p2.x + paddingLeft, p2.y + paddingTop);
        path.lineTo(p3.x + paddingLeft, p3.y + paddingTop);

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
     * Gets the stroke color value.
     * @return The stroke color value.
     */
    public int getStrokeColor() {
        return mStrokeColor;
    }

    /**
     * Sets the triangle stroke color.
     * @param strokeColor The stroke color value to use.
     */
    public void setStrokeColor(int strokeColor) {
        mStrokeColor = strokeColor;
        invalidateTextPaintAndMeasurements();
    }


    /**
     * Gets the stroke width value.
     * @return The stroke width value.
     */
    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    /**
     * Sets the triangle stroke width.
     * @param strokeWidth The stroke width value to use.
     */
    public void setStrokeWidth(float strokeWidth) {
        mStrokeWidth = strokeWidth;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the triangle direction.
     * @return The triangle direction value.
     */
    public Direction getDirection() {
        return mDirection;
    }

    /**
     * Sets the triangle direction.
     * @param direction The triangle direction value to use.
     */
    public void setDirection(Direction direction) {
        mDirection = direction;
        invalidateTextPaintAndMeasurements();
    }
}
