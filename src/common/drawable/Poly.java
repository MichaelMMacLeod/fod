package common.drawable;

import java.awt.*;
import java.awt.geom.Point2D;

public class Poly implements Drawable{
    private Point2D.Double center;
    private Point2D.Double[] points;

    public Poly() {
        center = new Point2D.Double(0, 0);
        points = new Point2D.Double[] {
                new Point2D.Double(-10, -10),
                new Point2D.Double(10, 0),
                new Point2D.Double(-10, 10)
        };
    }

    @Override
    public Point[] getPoints() {
        Point[] ints = new Point[points.length];

        for (int i = 0; i < ints.length; i++) {
            ints[i] = new Point((int) points[i].x, (int) points[i].y);
        }

        return ints;
    }
}
