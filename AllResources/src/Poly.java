import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

/**
 * A Poly is an polygon defined by a set of verticies and a color.
 */
public class Poly implements Drawable {
    /**
     * The center of the poly.
     */
    final Point2D.Double center;

    /**
     * the points which make up the polygon.
     */
    final Point2D.Double[] points;

    /**
     * Color to fill in the poly.
     */
    Color fillColor;

    /**
     * Creates a black triangle.
     */
    Poly() {
        this(Color.BLACK);
    }

    /**
     * Creates a poly
     * @param fillColor is the color of the poly.
     * @param points    are the points that make up the polygon.
     */
    Poly(Color fillColor, Point2D.Double[] points) {
        center = new Point2D.Double(0, 0);
        this.points = points;
        this.fillColor = fillColor;
    }

    /**
     * Creates a triangle
     * @param fillColor is the color of the triangle.
     */
    Poly(Color fillColor) {
        center = new Point2D.Double(0, 0);
        points = new Point2D.Double[] {
                new Point2D.Double(-20, -20),
                new Point2D.Double(20, 0),
                new Point2D.Double(-20, 20)
        };
        this.fillColor = fillColor;
    }

    /**
     * @return the double precision center point converted to an integer point.
     */
    public Point getCenter() {
        return new Point((int) center.x, (int) center.y);
    }

    /**
     * @return the double precision polygon verticies converted to integer points.
     */
    @Override
    public Point[] getPoints() {
        Point[] ints = new Point[points.length];

        for (int i = 0; i < ints.length; i++) {
            ints[i] = new Point((int) points[i].x, (int) points[i].y);
        }

        return ints;
    }

    /**
     * @return the color of this polygon.
     */
    @Override
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Checks if this polygon overlaps another Drawable.
     * @param other is the other Drawable.
     * @return true if they overlap, false otherwise.
     */
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

        // kinda a hack, but still probably the best way to go about doing this.

        Polygon polygon = new Polygon(xPoints, yPoints, points.length);
        Polygon polygonOther = new Polygon(xPointsOther, yPointsOther, otherPoints.length);

        Area polygonArea = new Area(polygon);

        polygonArea.intersect(new Area(polygonOther));

        return !polygonArea.isEmpty();
    }
}
