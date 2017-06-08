import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * I wrote this class a long time ago and forgot how it works.
 *
 * Basically just instantiate a InputManager object, then use .addKey() to tell it which keys to track.
 * .held() returns true when a key is down.
 * .pressed() returns true when a key is down and .pressed() has not been called before when the key is down.
 *
 * held:
 * a is down
 * held() --> true
 * a is down
 * held() --> true
 * a is up
 * held() --> false
 * a is down
 * held() --> true
 *
 * pressed:
 * a is down
 * pressed() --> true
 * a is down
 * pressed() --> false
 * a is up
 * pressed() --> false
 * a is down
 * pressed() --> true
 *
 * This class also handles mouse presses/held/location, but it's not used in FOD currently.
 */
public class InputManager {
    private final JPanel panel;

    private final ArrayList<String> keyValues;
    private final ArrayList<Boolean> keys;
    private final ArrayList<Boolean> keysChecked;

    private Point mouse;
    private boolean mouseDown;
    private boolean mouseDownChecked;

    public InputManager(JPanel panel) {
        this.panel = panel;

        mouse = new Point();
        mouseDown = false;
        mouseDownChecked = false;

        keyValues = new ArrayList<>();
        keys = new ArrayList<>();
        keysChecked = new ArrayList<>();

        MouseAdapter adapter = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                mouse = e.getPoint();
            }

            public void mousePressed(MouseEvent e) {
                mouse = e.getPoint();
                mouseDown = true;
            }

            public void mouseDragged(MouseEvent e) {
                mouse = e.getPoint();
                mouseDown = true;
            }

            public void mouseReleased(MouseEvent e) {
                mouse = e.getPoint();
                mouseDown = false;
                mouseDownChecked = false;
            }
        };
        panel.addMouseListener(adapter);
        panel.addMouseMotionListener(adapter);

        Action pressed = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < keyValues.size(); i++) {
                    if (keyValues.get(i).equalsIgnoreCase(
                            e.getActionCommand())) {
                        keys.set(i, true);
                        break;
                    }
                }
            }
        };
        Action released = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < keyValues.size(); i++) {
                    if (keyValues.get(i).equalsIgnoreCase(
                            e.getActionCommand())) {
                        keys.set(i, false);
                        keysChecked.set(i, false);
                        break;
                    }
                }
            }
        };

        panel.getActionMap().put("pressed", pressed);
        panel.getActionMap().put("released", released);
    }

    public double mx() {
        return mouse.getX();
    }
    public double my() {
        return mouse.getY();
    }

    public boolean pressed(String key) {
        if (key.equalsIgnoreCase("mouse")) {
            if (mouseDown && !mouseDownChecked) {
                mouseDownChecked = true;
                return true;
            }
        } else {
            for (int i = 0; i < keyValues.size(); i++) {
                if (keyValues.get(i).equalsIgnoreCase(key)
                        && keys.get(i)
                        && !keysChecked.get(i)) {
                    keysChecked.set(i, true);
                    return true;
                }
            }
        }


        return false;
    }

    public boolean held(String key) {
        if (key.equalsIgnoreCase("mouse")) {
            return mouseDown;
        } else {
            for (int i = 0; i < keyValues.size(); i++) {
                if (keyValues.get(i).equalsIgnoreCase(key) && keys.get(i)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void addKey(String key) {
        key = key.toUpperCase();

        keyValues.add(key);
        keys.add(false);
        keysChecked.add(false);

        panel.getInputMap().put(KeyStroke.getKeyStroke(key),
                "pressed");
        panel.getInputMap().put(KeyStroke.getKeyStroke("released " + key),
                "released");
    }
}
