import java.awt.geom.Point2D;

public class Bullet extends Poly implements VectorMovement {
    /**
     * The current speed vector.
     */
    private final Point2D.Double vector;

    /**
     * Values used to hold the current transformation.
     */
    private double dx, dy, dt;

    /**
     * Current rotation of this bullet.
     */
    private double rotation;

    /**
     * Time the bullet was created. Used to check when this bullet should be removed.
     */
    private final double creationTime;

    /**
     * The ship that this bullet comes from.
     */
    public final Ship source;

    /**
     * Creates a Bullet object with an initial movement vector, and a reference to the ship it was fired from.
     * @param vector is the initial speed vector of the bullet.
     * @param source is the ship that this bullet was fired from.
     */
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

    /**
     * Helps to remove old bullets (to conserve memory).
     * @return true if this bullet has been around for a little while.
     */
    public boolean shouldBeRemoved() {
        return System.currentTimeMillis() - creationTime > 10000;
    }

    /**
     * @return the current speed vector of this bullet.
     */
    public Point2D.Double getVector() {
        return vector;
    }

    /**
     * Sets the current speed vector.
     * @param x is the x component of the vector.
     * @param y is the y component of the vector.
     */
    public void setVector(double x, double y) {
        vector.x = x;
        vector.y = y;
    }

    /**
     * Does nothing. Exists because thrust exists in VectorMovement.
     */
    @Override
    public void thrust(double force) {}

    /**
     * Applies the current transformation, then removes the current transformation.
     */
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

    /**
     * Appends a rotation to the current transformation.
     * @param dt is the angle in radians.
     */
    @Override
    public void rotate(double dt) {
        this.dt += dt;
    }

    /**
     * Appends a translation to the current transformation.
     * @param dx is the distance in the x direction.
     * @param dy is the distance in the y direction.
     */
    @Override
    public void translate(double dx, double dy) {
        this.dx += dx;
        this.dy += dy;
    }

    /**
     * Removes the current transformation.
     */
    @Override
    public void removeTransformation() {
        dx = 0;
        dy = 0;
        dt = 0;
    }
}
