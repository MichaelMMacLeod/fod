package common.drawable;

import java.awt.*;
import java.awt.geom.Point2D;

public class Poly implements Drawable {
    Point2D.Double center;

    Point2D.Double[] points;

    Color outlineColor;

    Poly() {
        this(Color.BLACK);
    }

    Poly(Color outlineColor) {
        center = new Point2D.Double(0, 0);
        points = new Point2D.Double[] {
                new Point2D.Double(-20, -20),
                new Point2D.Double(20, 0),
                new Point2D.Double(-20, 20)
        };
        this.outlineColor = outlineColor;
    }

    public Point getCenter() {
        return new Point((int) center.x, (int) center.y);
    }

    @Override
    public Point[] getPoints() {
        Point[] ints = new Point[points.length];

        for (int i = 0; i < ints.length; i++) {
            ints[i] = new Point((int) points[i].x, (int) points[i].y);
        }

        return ints;
    }

    @Override
    public Color getOutlineColor() {
        return outlineColor;
    }
}
