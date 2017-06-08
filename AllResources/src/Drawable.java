import java.awt.*;
import java.io.Serializable;

public interface Drawable extends Serializable {
    /**
     * @return the points which make up the drawable.
     */
    Point[] getPoints();

    /**
     * @return the java.awt.Color which outlines the drawable.
     */
    Color getFillColor();

    /**
     * @return true if the two Drawables overlap, false otherwise.
     */
    boolean overlaps(Drawable other);
}
