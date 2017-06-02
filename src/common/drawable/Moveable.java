package common.drawable;

public interface Moveable {
    /**
     * Applies the current transformation.
     */
    public void transform();

    /**
     * Removes all of the current transformations.
     */
    public void removeTransformation();

    /**
     * Adds a translation to the current transformation.
     */
    public void translate(double dx, double dy);

    /**
     * Adds a rotation to the current transformation.
     */
    public void rotate(double dt);
}
