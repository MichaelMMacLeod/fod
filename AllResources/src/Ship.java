import java.awt.*;
import java.awt.geom.Point2D;

/**
 * A Ship is a polygon which can move. It has health in the range [0,10].
 *
 */
public class Ship extends Poly implements VectorMovement {
    /**
     * The current speed vector of the ship.
     */
    private final Point2D.Double vector;

    /**
     * Values used to handle the current transformation.
     */
    private double dx, dy, dt;

    /**
     * The current rotation of the ship.
     */
    private double rotation;

    /**
     * Set to false when the ship hits 0 health.
     */
    private boolean alive = true;

    /**
     * Health of the ship. When it hits 0, the ship dies.
     */
    private double health = 10;

    /**
     * Creates a black ship.
     */
    public Ship() {
        this(Color.BLACK);
    }

    /**
     * Creates a ship.
     * @param outlineColor is the color of the ship
     * // TODO: change 'outlineColor' to 'fillColor'
     */
    public Ship(Color outlineColor) {
        super(outlineColor);

        vector = new Point2D.Double(0, 0);
    }

    /**
     * @return this ship's current health.
     */
    public double getHealth() {
        return health;
    }

    /**
     * Heals the ship.
     * @param amount is the amount to heal.
     */
    public void heal(double amount) {
        if (health + amount < 10)
            health += amount;
    }

    /**
     * Damages the ship.
     * @param amount is the amount to damage.
     */
    public void damage(double amount) {
        health -= amount;

        if (health <= 0)
            kill();
    }

    /**
     * Kills the ship. Called when the ship's health becomes 0.
     */
    private void kill() {
        alive = false;
    }

    /**
     * @return true if this ship is alive, false otherwise.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * @return the current speed vector of this ship.
     */
    public Point2D.Double getVector() {
        return vector;
    }

    /**
     * @return the current rotation of the ship.
     */
    public double getRotation() {
        return rotation;
    }

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
     * Removes the current transformation.
     */
    @Override
    public void removeTransformation() {
        dx = 0;
        dy = 0;
        dt = 0;
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
     * Thrusts the ship in its current direction.
     * @param force is the power of the thrust.
     */
    @Override
    public void thrust(double force) {
        vector.x += force * Math.cos(rotation);
        vector.y += force * Math.sin(rotation);
    }
}
