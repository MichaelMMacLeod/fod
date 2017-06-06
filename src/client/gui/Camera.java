package client.gui;

import common.drawable.Drawable;

import java.awt.*;

final class Camera {
    private Camera() {}

    static void draw(Graphics g, Point focus, Drawable[] drawables, int width, int height) {
        for (Drawable shape : drawables) {
            Point[] points = shape.getPoints();

            for (Point p : points) {
                p.x = p.x + width / 2;
                p.y = p.y + height / 2;
            }

            int[] xpoints = new int[points.length];
            int[] ypoints = new int[points.length];

            for (int i = 0; i < points.length; i++) {
                xpoints[i] = points[i].x;
                ypoints[i] = points[i].y;
            }

            g.setColor(shape.getOutlineColor());
            g.drawPolygon(xpoints, ypoints, points.length);
        }
    }
}
