package basicEncryptions;

import Keys.Key;
import enums.EActionEncryptOrDecrypt;

public class ShiftUpEncryption extends BasicEncryption {

    public Key initKey() {
        return super.initKey("Shift Up");
    }

    public int computeChar(int currentChar, int key, EActionEncryptOrDecrypt eActionEncryptOrDecrypt) {
        if (eActionEncryptOrDecrypt == EActionEncryptOrDecrypt.decrypt)
            key = -key;
        return currentChar + key;
    }
}
