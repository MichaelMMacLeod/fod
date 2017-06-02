package client.gui;

import common.drawable.Poly;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final int width, height;

    private Poly[] shapes;
    private Point focus;

    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;

        shapes = new Poly[0];
    }

    public void updateVisuals(Point focus, Poly[] shapes) {
        this.focus = focus;
        this.shapes = shapes;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Camera.draw(g, focus, shapes, getWidth(), getHeight());
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
