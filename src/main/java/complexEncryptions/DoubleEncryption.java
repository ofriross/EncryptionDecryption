package complexEncryptions;

import Keys.DoubleKey;
import Keys.Key;

import java.util.ArrayList;

public class DoubleEncryption extends EncryptionAlgorithm {
    public Key initKey() {
        key = new DoubleKey();
        ((DoubleKey) key).setDouble1(encryptionAlgorithm.initKey());
        ((DoubleKey) key).setDouble2(encryptionAlgorithm.initKey());
        return key;
    }

    public <T extends Key> String encryptFile(String data, T key) {
        String firstEncryption = encryptionAlgorithm.encryptFile(data, ((DoubleKey) key).getDouble1());
        return encryptionAlgorithm.encryptFile(firstEncryption, ((DoubleKey) key).getDouble2());
    }

    public String decryptFile(String data, ArrayList<Integer> keys) {
        return encryptionAlgorithm.decryptFile(data, keys);
    }

    public DoubleEncryption(IEncryptionAlgorithm encryptionAlgorithm) {
        super(encryptionAlgorithm);
    }
}
