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
}
