package common.drawable;

import java.awt.*;
import java.io.Serializable;

public interface Drawable extends Serializable {
    /**
     * @return the points which make up the drawable.
     */
    public Point[] getPoints();
}
