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

        super.move();
    }

    public void thrust(double force) {
        vector.x += force * Math.cos(rotation);
        vector.y += force * Math.sin(rotation);
        System.out.println(vector);
    }
}
