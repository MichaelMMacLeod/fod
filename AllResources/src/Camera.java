import java.awt.*;

final class Camera {
    /**
     * No need to instantiate a Camera object.
     */
    private Camera() {}

    /**
     * Draws shapes on the screen.
     * @param g         is the graphics object to draw on.
     * @param focus     is the location to center the window on.
     * @param drawables is the list of shapes to draw.
     * @param width     is the width of the window.
     * @param height    is the height of the window.
     */
    static void draw(Graphics g, Point focus, Drawable[] drawables, int width, int height) {
        for (Drawable shape : drawables) {
            Point[] points = shape.getPoints();

            for (Point p : points) {
                p.x = p.x - focus.x + width / 2;
                p.y = p.y - focus.y + height / 2;
            }

            int[] xpoints = new int[points.length];
            int[] ypoints = new int[points.length];

            for (int i = 0; i < points.length; i++) {
                xpoints[i] = points[i].x;
                ypoints[i] = points[i].y;
            }

            g.setColor(shape.getFillColor());
            g.fillPolygon(xpoints, ypoints, points.length);

            g.setColor(Color.BLACK);
            g.drawPolygon(xpoints, ypoints, points.length);
        }
    }
}
