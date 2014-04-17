package com.kb.android.drawable;

import android.graphics.Color;
import android.test.AndroidTestCase;

/**
 * Created by kb on 16.04.2014.
 */
public class TriangleTest extends AndroidTestCase {
    public void testFillColor() throws Exception {
        Triangle triangle = new Triangle(getContext());

        triangle.setFillColor(Color.RED);
        assertTrue(triangle.getFillColor() == Color.RED);

        triangle.setFillColor(Color.BLUE);
        assertTrue(triangle.getFillColor() == Color.BLUE);
    }

    public void testStrokeColor() throws Exception {
        Triangle triangle = new Triangle(getContext());

        triangle.setStrokeColor(Color.RED);
        assertTrue(triangle.getStrokeColor() == Color.RED);

        triangle.setStrokeColor(Color.BLUE);
        assertTrue(triangle.getStrokeColor() == Color.BLUE);
    }

    public void testStrokeWidth() throws Exception {
        Triangle triangle = new Triangle(getContext());

        triangle.setStrokeWidth(10);
        assertTrue(triangle.getStrokeWidth() == 10);

        triangle.setStrokeWidth(50.5f);
        assertTrue(triangle.getStrokeWidth() == 50.5f);
    }

    public void testDirection() throws Exception {
        Triangle triangle = new Triangle(getContext());

        triangle.setDirection(Triangle.Direction.TOP);
        assertTrue(triangle.getDirection() == Triangle.Direction.TOP);

        triangle.setDirection(Triangle.Direction.BOTTOM);
        assertTrue(triangle.getDirection() == Triangle.Direction.BOTTOM);

        triangle.setDirection(Triangle.Direction.LEFT);
        assertTrue(triangle.getDirection() == Triangle.Direction.LEFT);

        triangle.setDirection(Triangle.Direction.RIGHT);
        assertTrue(triangle.getDirection() == Triangle.Direction.RIGHT);
    }
}
