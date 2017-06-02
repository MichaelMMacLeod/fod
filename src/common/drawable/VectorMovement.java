package common.drawable;

import java.awt.geom.Point2D;

public interface VectorMovement extends Moveable {
    /**
     * Thrusts in the direction of the angle of rotation.
     * @param force is the power of the thrust.
     */
    public void thrust(double force);
}
