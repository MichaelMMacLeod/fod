package common.message;

import common.drawable.Drawable;

import java.awt.*;
import java.io.Serializable;

public class ShapeData implements Serializable {
    public final Point focus;
    public final Drawable[] shapes;

    public ShapeData(Point focus, Drawable[] shapes) {
        this.focus = focus;
        this.shapes = shapes;
    }

    /**
     * Creates a blank message.
     */
    public ShapeData() {
        this(new Point(0, 0), new Drawable[0]);
    }
}
