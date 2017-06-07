package common.drawable;

import java.awt.*;
import java.awt.geom.Point2D;

public class Ship extends Poly implements VectorMovement {
    private final Point2D.Double vector;

    private double dx, dy, dt;

    private double rotation;

    public Ship() {
        this(Color.BLACK);
    }

    public Ship(Color outlineColor) {
        super(outlineColor);

        vector = new Point2D.Double(0, 0);
    }

    public Point2D.Double getVector() {
        return vector;
    }

    public double getRotation() {
        return rotation;
    }

    @Override
    public void transform() {
        dx += vector.x;
        dy += vector.y;

        for (Point2D.Double point : points) {
            point.x += dx;
            point.y += dy;
        }
        center.x += dx;
        center.y += dy;

        double cos = Math.cos(dt), sin = Math.sin(dt);

        for (Point2D.Double point : points) {
            Point2D.Double prime = new Point2D.Double(point.x - center.x, point.y - center.y);
            point.setLocation(
                    prime.x * cos - prime.y * sin + center.x,
                    prime.x * sin + prime.y * cos + center.y
            );
        }

        rotation += dt;

        removeTransformation();
    }

    @Override
    public void removeTransformation() {
        dx = 0;
        dy = 0;
        dt = 0;
    }

    @Override
    public void rotate(double dt) {
        this.dt += dt;
    }

    @Override
    public void translate(double dx, double dy) {
        this.dx += dx;
        this.dy += dy;
    }

    @Override
    public void thrust(double force) {
        vector.x += force * Math.cos(rotation);
        vector.y += force * Math.sin(rotation);
    }
}
