package complexEncryptions;

import Keys.Key;
import Keys.RepeatKey;

import java.util.ArrayList;

public class RepeatEncryption extends EncryptionAlgorithm {
    private final int timesToRepeat;

    public Key initKey() {
        return key = new RepeatKey(timesToRepeat, encryptionAlgorithm.initKey());
    }

    public <T extends Key> String performEncryption(String data, T key) {
        String encryption = encryptionAlgorithm.performEncryption(data, ((RepeatKey) key).getRepeatedKey());
        for (int i = 0; i < timesToRepeat - 1; i++) {
            encryption = encryptionAlgorithm.performEncryption(encryption, ((RepeatKey) key).getRepeatedKey());
        }
        return encryption;
    }

    public String performDecryption(String data, ArrayList<Integer> keys) {
        return encryptionAlgorithm.performDecryption(data, keys);
    }

    public RepeatEncryption(IEncryptionAlgorithm encryptionAlgorithm, int timesToRepeat) {
        super(encryptionAlgorithm);
        this.timesToRepeat = timesToRepeat;
    }
}
