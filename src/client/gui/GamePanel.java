package client.gui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final int width, height;

    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Camera.draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
