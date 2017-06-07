package common.message;

import java.io.Serializable;

public class InputData implements Serializable {
    public final String[] keys;
    public final boolean[] pressed;
    public final boolean[] held;

    public InputData(String[] keys, boolean[] pressed, boolean[] held) {
        this.keys = keys;
        this.pressed = pressed;
        this.held = held;
    }

    /**
     * Creates a copy of an InputData
     * @param other is the original data to be copied.
     */
    public InputData(InputData other) {
        keys = new String[other.keys.length];
        pressed = new boolean[other.pressed.length];
        held = new boolean[other.held.length];

        System.arraycopy(other.keys, 0, keys, 0, other.keys.length);
        System.arraycopy(other.pressed, 0, pressed, 0, other.pressed.length);
        System.arraycopy(other.held, 0, held, 0, other.held.length);
    }
}
