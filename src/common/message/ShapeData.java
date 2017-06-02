package common.message;

import common.drawable.Poly;

import java.awt.*;
import java.io.Serializable;

public class ShapeData implements Serializable {
    public final Point focus;
    public final Poly[] shapes;

    public ShapeData(Point focus, Poly[] shapes) {
        this.focus = focus;
        this.shapes = shapes;
    }
}
