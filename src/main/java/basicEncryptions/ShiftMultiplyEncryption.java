package basicEncryptions;

import General.Constants;
import Keys.Key;
import enums.EActionEncryptOrDecrypt;

public class ShiftMultiplyEncryption extends BasicEncryption {
    public Key initKey() {
        return super.initKey("Shift Multiply");
    }

    public int computeChar(int currentChar, int key, EActionEncryptOrDecrypt eActionEncryptOrDecrypt) {
        if (key % 2 == 0)
            key += 1;
        int computedChar = 0;
        if (eActionEncryptOrDecrypt == EActionEncryptOrDecrypt.encrypt)
            computedChar = currentChar * key;
        else
            for (int i = 0; i <= Constants.MAX_ASCII_VALUE; i++)
                if ((i * key) % (Constants.MAX_ASCII_VALUE + 1) == currentChar)
                    computedChar = i;
        return computedChar;
    }
}