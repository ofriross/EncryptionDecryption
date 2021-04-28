package basicEncryptions;

import General.Consts;
import Keys.Key;
import Keys.SingleKey;
import enums.EAction;

public class ShiftMultiplyEncryption extends BasicEncryption {
    public Key initKey() {
        return super.initKey("Shift Multiply");
    }

    public int computeChar(int ch, int key, EAction eAction) {
        if (key % 2 == 0)
            key += 1;
        if (eAction == EAction.encrypt)
            return ch * key;
        else
            for (int i = 0; i <= Consts.MAX_ASCII_VALUE; i++)
                if ((i * key) % (Consts.MAX_ASCII_VALUE + 1) == ch)
                    return i;
        return 0;
    }
}