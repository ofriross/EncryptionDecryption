package complexEncryptions;

import FileManaging.FileNameAndContent;
import Keys.Key;
import Keys.RepeatKey;

import java.util.ArrayList;

public class RepeatEncryption extends EncryptionAlgorithm {
    private final int n;

    public Key initKey() {
        key = new RepeatKey(n);
        ((RepeatKey) key).setRepeatedKey(encryptionAlgorithm.initKey());
        return key;
    }

    public <T extends Key> String encryptFile(String data, T key) {
        String encryption = encryptionAlgorithm.encryptFile(data, ((RepeatKey) key).getRepeatedKey());
        for (int i = 0; i < n - 1; i++) {
            encryption = encryptionAlgorithm.encryptFile(encryption, ((RepeatKey) key).getRepeatedKey());
        }
        return encryption;
    }

    public String decryptFile(String data, ArrayList<Integer> keys) {
        return encryptionAlgorithm.decryptFile(data, keys);
    }

    public RepeatEncryption(IEncryptionAlgorithm encryptionAlgorithm, int n) {
        super(encryptionAlgorithm);
        this.n = n;
    }
}
