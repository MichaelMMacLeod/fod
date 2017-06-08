import javax.swing.*;
import java.awt.*;

/**
 * The GUI portion of the client.
 */
public class GamePanel extends JPanel {
    /**
     * Initial width and height of the GamePanel.
     */
    private final int width, height;

    /**
     * Handles input.
     */
    private final InputManager inputManager;
    private final String[] keys = {"a", "w", "d", "q", "f", "s"};

    /**
     * What to draw on the screen, and where.
     */
    private Drawable[] shapes;
    private Point focus;

    /**
     * Creates a GamePanel.
     * @param width  is the width of the window.
     * @param height is the height of the window.
     */
    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;

        inputManager = new InputManager(this);
        for (String s : keys)
            inputManager.addKey(s);

        shapes = new Drawable[0];
    }

    /**
     * Update what is displayed on the screen.
     * @param focus  is where to focus the center of the screen.
     * @param shapes are the shapes to draw on screen.
     */
    public void updateVisuals(Point focus, Drawable[] shapes) {
        this.focus = focus;
        this.shapes = shapes;
    }

    /**
     * @return and InputData object which identifies when keys are pressed or held.
     */
    public InputData getInputData() {
        boolean[] pressed = new boolean[keys.length];
        for (int i = 0; i < pressed.length; i++)
            pressed[i] = inputManager.pressed(keys[i]);

        boolean[] held = new boolean[keys.length];
        for (int i = 0; i < held.length; i++)
            held[i] = inputManager.held(keys[i]);

        return new InputData(keys, pressed, held);
    }

    /**
     * Draws the GUI (called by the awt thread)
     * @param g is the graphics object to draw on.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Camera.draw(g, focus, shapes, getWidth(), getHeight());
    }

    /**
     * @return A dimension with the initial size of the window in it.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
