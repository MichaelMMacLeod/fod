package common.drawable;

import java.awt.*;
import java.awt.geom.Point2D;

public class Poly implements Drawable, Moveable {
    private Point2D.Double center;
    private Point2D.Double[] points;

    private double dx, dy;

    public Poly() {
        center = new Point2D.Double(0, 0);
        points = new Point2D.Double[] {
                new Point2D.Double(-10, -10),
                new Point2D.Double(10, 0),
                new Point2D.Double(-10, 10)
        };
    }

    @Override
    public void move() {
        center.x += dx;
        center.y += dy;

        for (Point2D.Double p : points) {
            p.x += dx;
            p.y += dy;
        }

        dx = 0;
        dy = 0;
    }

    @Override
    public void translate(double dx, double dy) {
        this.dx += dx;
        this.dy += dy;
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
