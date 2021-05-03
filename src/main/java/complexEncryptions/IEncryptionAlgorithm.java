package complexEncryptions;

import Keys.Key;

import java.util.ArrayList;

public interface IEncryptionAlgorithm {
    <T extends Key> String performEncryption(String data, T key);

    String performDecryption(String data, ArrayList<Integer> keys);

    int getKeyStrength();

    String getType();

    Key initKey();
}