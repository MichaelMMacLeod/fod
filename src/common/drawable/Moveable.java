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
}
