import java.awt.geom.Point2D;

public class Bullet extends Poly implements VectorMovement {
    private final Point2D.Double vector;

    private double dx, dy, dt;

    private double rotation;

    private final double creationTime;

    public final Ship source;

    public Bullet(Point2D.Double vector, Ship source) {
        super(source.getFillColor(),
                new Point2D.Double[] {
                        new Point2D.Double(-10, -10),
                        new Point2D.Double(10, -10),
                        new Point2D.Double(10, 10),
                        new Point2D.Double(-10, 10)

                });

        this.vector = vector;

        creationTime = System.currentTimeMillis();

        this.source = source;
    }

    public boolean shouldBeRemoved() {
        return System.currentTimeMillis() - creationTime > 10000;
    }

    public Point2D.Double getVector() {
        return vector;
    }

    public void setVector(double x, double y) {
        vector.x = x;
        vector.y = y;
    }

    @Override
    public void thrust(double force) {}

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
    public void rotate(double dt) {
        this.dt += dt;
    }

    @Override
    public void translate(double dx, double dy) {
        this.dx += dx;
        this.dy += dy;
    }

    @Override
    public void removeTransformation() {
        dx = 0;
        dy = 0;
        dt = 0;
    }
}
