package complexEncryptions;

import FileManaging.FileNameAndContent;
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

    public <T extends Key> ArrayList<FileNameAndContent> encryptFolder(ArrayList<FileNameAndContent> data, T key) {
        ArrayList<FileNameAndContent> firstEncryption = encryptionAlgorithm.encryptFolder(data, ((DoubleKey) key).getDouble1());
        return encryptionAlgorithm.encryptFolder(firstEncryption, ((DoubleKey) key).getDouble2());
    }

    public ArrayList<FileNameAndContent> decryptFolder(ArrayList<FileNameAndContent> data, ArrayList<Integer> keys) {
        return encryptionAlgorithm.decryptFolder(data, keys);
    }

    public DoubleEncryption(IEncryptionAlgorithm encryptionAlgorithm) {
        super(encryptionAlgorithm);
    }
}
