package com.kb.android.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * Created by kb on 06.08.2014.
 */
public class AxisHorizontal extends AxisAbstract {

    public AxisHorizontal(Context context) {
        super(context);
    }

    public AxisHorizontal(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AxisHorizontal(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
            float majorValueStep = contentWidth / (mMajorTicksCount + 1);

            for(int i = 0; i < mMajorTicksCount + 2; i++) {
                if (mMajorTicksAboveLength > 0 || mMajorTicksBelowLength > 0) {
                    Path tickPath = new Path();
                    tickPath.moveTo(paddingLeft + i * majorValueStep, majorTicksY1);
                    tickPath.lineTo(paddingLeft + i * majorValueStep, majorTicksY2);
                    canvas.drawPath(tickPath, paint);

                    if (mMinorTicksCount > 0) {
                        Rect rect = new Rect((int) (paddingLeft + (i - 1) * majorValueStep), minorTicksY1, (int) (paddingLeft + i * majorValueStep), minorTicksY2);
                        drawMinorTicks(mMinorTicksCount, rect, canvas, paint);
                    }
                }
            }
        }

        // @TODO draw labels
        if (mIsLabels) {
            Paint labelPaint = new Paint(paint);
            labelPaint.setColor(mLabelsColor);
            labelPaint.setTextSize(mLabelsSize);

            float majorLabelStep = (mMaximum - mMinimum) / (mMajorTicksCount + 2);
            float majorValueStep = contentWidth / (mMajorTicksCount + 1);

            for (int i = 0; i < mMajorTicksCount + 2; i++) {
                String labelValue = String.format(mLabelsFormat, mMinimum + i * majorLabelStep);
                float labelWidth = labelPaint.measureText(labelValue);
                float labelX = paddingLeft + i * majorValueStep - labelWidth / 2.0f;
                if (labelX < paddingLeft) {
                    labelX = paddingLeft;
                }
                if (labelX + labelWidth > contentWidth + paddingLeft) {
                    labelX = paddingLeft + contentWidth - labelWidth;
                }
                canvas.drawText(labelValue, labelX, paddingTop + contentHeight * 0.99f, labelPaint);

            }
        }

        // draw marks
        if (mMark > mMinimum && mMark < mMaximum) {
            int markLocation = pointToPixel(mMark, contentWidth);

            Paint mark = new Paint(paint);
            mark.setColor(mMarkStrokeColor);
            mark.setStrokeWidth(mMarkStrokeWidth);
            mark.setTextSize(contentHeight / 2.5f);

            Path tickPath = new Path();
            tickPath.moveTo(paddingLeft + markLocation, paddingTop);
            tickPath.lineTo(paddingLeft + markLocation, paddingTop + contentHeight);

            canvas.drawPath(tickPath, mark);

            if (mIsMarkLabel) {
                String markLabel = String.format(mMarkLabelFormat, mMark);
                canvas.drawText(markLabel, paddingLeft + markLocation * 1.01f, paddingTop + contentHeight * 0.99f, mark);
            }
        }
    }

    @Override
    protected void drawMinorTicks(int ticksCount, Rect rect, Canvas canvas, Paint paint) {
        float valueStep = rect.width() / (ticksCount + 1);
        for(int i = 1; i < ticksCount + 1; i++) {
            Path tickPath = new Path();
            tickPath.moveTo(rect.left + i * valueStep, rect.top);
            tickPath.lineTo(rect.left + i * valueStep, rect.bottom);
            canvas.drawPath(tickPath, paint);
        }
    }
}
