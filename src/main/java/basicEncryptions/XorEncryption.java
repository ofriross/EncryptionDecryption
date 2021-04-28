package basicEncryptions;

import FileManaging.FileEncryptor;
import Keys.Key;
import Keys.SingleKey;
import enums.EAction;

import java.util.ArrayList;

public class XorEncryption extends BasicEncryption {

    public Key initKey() {
        return super.initKey("Xor");
    }


    public int computeChar(int ch1, int key, EAction eAction) {
        String binaryKey = Integer.toBinaryString(key);
        binaryKey = String.format("%8s", binaryKey).replaceAll(" ", "0");  // 8-bit Integer
        String binaryCurrCh = Integer.toBinaryString(ch1);
        binaryCurrCh = String.format("%8s", binaryCurrCh).replaceAll(" ", "0");  // 8-bit Integer
        StringBuilder ch2 = new StringBuilder(binaryCurrCh);
        for (int binIndex = 0; binIndex < 8; binIndex++) {
            if (binaryCurrCh.charAt(binIndex) == binaryKey.charAt(binIndex))
                ch2.setCharAt(binIndex, '0');
            else
                ch2.setCharAt(binIndex, '1');
        }
        return Integer.parseInt(ch2.toString(), 2);
    }
}
