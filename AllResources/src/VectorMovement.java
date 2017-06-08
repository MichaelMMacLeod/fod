interface VectorMovement extends Moveable {
    /**
     * Thrusts in the direction of the angle of rotation.
     * @param force is the power of the thrust.
     */
    void thrust(double force);
}
