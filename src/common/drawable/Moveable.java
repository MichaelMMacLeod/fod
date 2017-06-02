package common.drawable;

public interface Moveable {
    /**
     * Applies the current movement, then resets it to zero.
     */
    public void move();

    /**
     * Adds a translation to the current movement.
     */
    public void translate(double dx, double dy);

    /**
     * Adds a rotation to the current movement.
     */
    public void rotate(double dt);

    /**
     * Returns the current x translation.
     */
    public double getDX();

    /**
     * Returns the current y translation.
     */
    public double getDY();

    /**
     * Returns the current rotation movement.
     */
    public double getDT();
}
