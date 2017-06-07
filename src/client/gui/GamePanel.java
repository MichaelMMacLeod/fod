package client.gui;

import client.input.InputManager;
import common.drawable.Drawable;
import common.message.InputData;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final int width, height;

    private final InputManager inputManager;
    private final String[] keys = {"a", "w", "d", "q"};

    private Drawable[] shapes;
    private Point focus;

    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;

        inputManager = new InputManager(this);
        for (String s : keys)
            inputManager.addKey(s);

        shapes = new Drawable[0];
    }

    public void updateVisuals(Point focus, Drawable[] shapes) {
        this.focus = focus;
        this.shapes = shapes;
    }

    public InputData getInputData() {
        boolean[] pressed = new boolean[keys.length];
        for (int i = 0; i < pressed.length; i++)
            pressed[i] = inputManager.pressed(keys[i]);

        boolean[] held = new boolean[keys.length];
        for (int i = 0; i < held.length; i++)
            held[i] = inputManager.held(keys[i]);

        return new InputData(keys, pressed, held);
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
