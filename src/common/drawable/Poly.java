package common.drawable;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class Poly implements Drawable {
    final Point2D.Double center;

    final Point2D.Double[] points;

    Color fillColor;

    Poly() {
        this(Color.BLACK);
    }

    Poly(Color fillColor, Point2D.Double[] points) {
        center = new Point2D.Double(0, 0);
        this.points = points;
        this.fillColor = fillColor;
    }

    Poly(Color fillColor) {
        center = new Point2D.Double(0, 0);
        points = new Point2D.Double[] {
                new Point2D.Double(-20, -20),
                new Point2D.Double(20, 0),
                new Point2D.Double(-20, 20)
        };
        this.fillColor = fillColor;
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
    public Color getFillColor() {
        return fillColor;
    }

    @Override
    public boolean overlaps(Drawable other) {
        Point[] points = getPoints();

        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            xPoints[i] = points[i].x;
            yPoints[i] = points[i].y;
        }

        Point[] otherPoints = other.getPoints();

        int[] xPointsOther = new int[otherPoints.length];
        int[] yPointsOther = new int[otherPoints.length];

        for (int i = 0; i < otherPoints.length; i++) {
            xPointsOther[i] = otherPoints[i].x;
            yPointsOther[i] = otherPoints[i].y;
        }

        Polygon polygon = new Polygon(xPoints, yPoints, points.length);
        Polygon polygonOther = new Polygon(xPointsOther, yPointsOther, otherPoints.length);

        Area polygonArea = new Area(polygon);

        polygonArea.intersect(new Area(polygonOther));

        return !polygonArea.isEmpty();
    }
}
