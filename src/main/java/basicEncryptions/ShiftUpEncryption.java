package basicEncryptions;

import Keys.Key;
import enums.EAction;

public class ShiftUpEncryption extends BasicEncryption {

    public Key initKey() {
        return super.initKey("Shift Up");
    }

    public int computeChar(int ch, int key, EAction eAction) {
        if (eAction == EAction.decrypt)
            key = -key;
        return ch + key;
    }
}
