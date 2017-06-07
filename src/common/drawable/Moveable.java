package common.drawable;

interface Moveable {
    /**
     * Applies the current transformation.
     */
    void transform();

    /**
     * Removes all of the current transformations.
     */
    void removeTransformation();

    /**
     * Adds a translation to the current transformation.
     */
    void translate(double dx, double dy);

    /**
     * Adds a rotation to the current transformation.
     */
    void rotate(double dt);
}
