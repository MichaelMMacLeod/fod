package client.gui;

import common.drawable.Drawable;

import java.awt.*;

final class Camera {
    private Camera() {}

    static void draw(Graphics g, Drawable[] drawables, Point focus) {
        for (Drawable shape : drawables) {
            Point[] points = shape.getPoints();

            for (Point p : points) {
                p.x -= focus.x;
                p.y -= focus.y;
            }

            int[] xpoints = new int[points.length];
            int[] ypoints = new int[points.length];

            for (int i = 0; i < points.length; i++) {
                xpoints[i] = points[i].x;
                ypoints[i] = points[i].y;
            }

            g.drawPolygon(xpoints, ypoints, points.length);
        }
    }
}
