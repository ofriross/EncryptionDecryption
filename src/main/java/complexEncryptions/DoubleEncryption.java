package complexEncryptions;

import Keys.DoubleKey;
import Keys.Key;

import java.util.ArrayList;

public class DoubleEncryption extends EncryptionAlgorithm {
    public Key initKey() {
        return key = new DoubleKey(encryptionAlgorithm.initKey(), encryptionAlgorithm.initKey());
    }

    public <T extends Key> String performEncryption(String data, T key) {
        String firstEncryption = encryptionAlgorithm.performEncryption(data, ((DoubleKey) key).getDouble1());
        return encryptionAlgorithm.performEncryption(firstEncryption, ((DoubleKey) key).getDouble2());
    }

    public String performDecryption(String data, ArrayList<Integer> keys) {
        return encryptionAlgorithm.performDecryption(data, keys);
    }

    public DoubleEncryption(IEncryptionAlgorithm encryptionAlgorithm) {
        super(encryptionAlgorithm);
    }
}
