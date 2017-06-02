package common.drawable;

import java.awt.geom.Point2D;

public class Ship extends Poly {
    private Point2D.Double vector;

    public Ship() {
        vector = new Point2D.Double(0, 0);
    }

    @Override
    public void move() {
        dx += vector.x;
        dy += vector.y;

        center.x += dx;
        center.y += dy;

        for (Point2D.Double p : points) {
            p.x += dx;
            p.y += dy;
        }

        dx = 0;
        dy = 0;

        dt = 0;
    }

    public void thrust(double force) {
        vector.x += force * Math.cos(rotation);
        vector.y += force * Math.sin(rotation);
        System.out.println(vector);
    }
}
