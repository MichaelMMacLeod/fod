package common.message;

import common.drawable.Drawable;

import java.awt.*;
import java.io.Serializable;

public class ShapeData implements Serializable {
    public final Point focus;
    public final Drawable[] shapes;
    public final boolean clientIsAlive;

    public ShapeData(Point focus, Drawable[] shapes, boolean clientIsAlive) {
        this.focus = focus;
        this.shapes = shapes;
        this.clientIsAlive = clientIsAlive;
    }

    /**
     * Creates a blank message.
     */
    public ShapeData() {
        this(new Point(0, 0), new Drawable[0], true);
    }
}
