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

    public <T extends Key> ArrayList<FileNameAndContent> encryptFolder(ArrayList<FileNameAndContent> data, T key) {
        ArrayList<FileNameAndContent> encryption = encryptionAlgorithm.encryptFolder(data, ((RepeatKey) key).getRepeatedKey());
        for (int i = 0; i < n - 1; i++) {
            encryption = encryptionAlgorithm.encryptFolder(encryption, ((RepeatKey) key).getRepeatedKey());
        }
        return encryption;
    }

    public ArrayList<FileNameAndContent> decryptFolder(ArrayList<FileNameAndContent> data, ArrayList<Integer> keys) {
        return encryptionAlgorithm.decryptFolder(data, keys);
    }

    public RepeatEncryption(IEncryptionAlgorithm encryptionAlgorithm, int n) {
        super(encryptionAlgorithm);
        this.n = n;
    }
}
