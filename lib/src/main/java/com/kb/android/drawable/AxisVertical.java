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
public class AxisVertical extends AxisAbstract {

    public AxisVertical(Context context) {
        super(context);
    }

    public AxisVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AxisVertical(Context context, AttributeSet attrs, int defStyle) {
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

        int baseline = contentWidth / 2 + paddingLeft;
        int majorTicksX1 = (int) (baseline - mMajorTicksAboveLength * contentWidth / 2.0);
        int majorTicksX2 = (int) (baseline + mMajorTicksBelowLength * contentWidth / 2.0);

        int minorTicksX1 = (int) (baseline - mMinorTicksAboveLength * contentWidth / 2.0);
        int minorTicksX2 = (int) (baseline + mMinorTicksBelowLength * contentWidth / 2.0);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mAxisLineColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mAxisLineWidth);

        // draw line
        Path linePath = new Path();
        linePath.moveTo(baseline, 0 + paddingTop);
        linePath.lineTo(baseline, contentHeight + paddingTop);
        canvas.drawPath(linePath, paint);

        // draw major & minors ticks
        if (mMajorTicksCount > 0) {
            float majorValueStep = contentHeight / (mMajorTicksCount + 1);

            for(int i = 0; i < mMajorTicksCount + 2; i++) {
                if (mMajorTicksAboveLength > 0 || mMajorTicksBelowLength > 0) {
                    Path tickPath = new Path();
                    tickPath.moveTo(majorTicksX1, paddingTop + i * majorValueStep);
                    tickPath.lineTo(majorTicksX2, paddingTop + i * majorValueStep);
                    canvas.drawPath(tickPath, paint);

                    if (mMinorTicksCount > 0) {
                        Rect rect = new Rect(minorTicksX1, (int) (paddingTop + (i - 1) * majorValueStep), minorTicksX2, (int) (paddingTop + i * majorValueStep));
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
            float majorValueStep = contentHeight / (mMajorTicksCount + 1);

            for (int i = 0; i < mMajorTicksCount + 2; i++) {
                String labelValue = String.format(mLabelsFormat, mMinimum + i * majorLabelStep);
                float labelHeight = labelPaint.getTextSize();
                float labelY = paddingTop + i * majorValueStep - labelHeight / 2.0f;
                if (labelY < paddingTop) {
                    labelY = paddingTop;
                }
                if (labelY + labelHeight > contentHeight + paddingTop) {
                    labelY = paddingTop + contentHeight - labelHeight;
                }
                canvas.drawText(labelValue, paddingLeft, labelY, labelPaint);

            }
        }

        // draw marks
        if (mMark > mMinimum && mMark < mMaximum) {
            int markLocation = pointToPixel(mMark, contentHeight);

            Paint mark = new Paint(paint);
            mark.setColor(mMarkStrokeColor);
            mark.setStrokeWidth(mMarkStrokeWidth);
            mark.setTextSize(18.0f);

            Path tickPath = new Path();
            tickPath.moveTo(paddingLeft + 0, paddingTop + markLocation);
            tickPath.lineTo(paddingLeft + contentHeight, paddingTop + markLocation);

            canvas.drawPath(tickPath, mark);

            if (mIsMarkLabel) {
                String markLabel = String.format(mMarkLabelFormat, mMark);
                canvas.drawText(markLabel, paddingLeft + markLocation * 1.01f, paddingTop + contentHeight * 0.99f, mark);
            }
        }
    }

    @Override
    protected void drawMinorTicks(int ticksCount, Rect rect, Canvas canvas, Paint paint) {
        float valueStep = rect.height() / (ticksCount + 1);
        for(int i = 1; i < ticksCount + 1; i++) {
            Path tickPath = new Path();
            tickPath.moveTo(rect.left, rect.top + i * valueStep);
            tickPath.lineTo(rect.right, rect.top + i * valueStep);
            canvas.drawPath(tickPath, paint);
        }
    }
}
