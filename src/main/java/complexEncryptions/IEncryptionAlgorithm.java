package complexEncryptions;

import Keys.Key;

import java.util.ArrayList;

public interface IEncryptionAlgorithm {
    <T extends Key> String encryptFile(String data, T key);

    String decryptFile(String data, ArrayList<Integer> keys);

    int getKeyStrength();

    String getType();

    Key initKey();
}