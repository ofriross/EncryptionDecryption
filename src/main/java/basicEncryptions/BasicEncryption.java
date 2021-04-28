package basicEncryptions;

import FileManaging.FileEncryptor;
import FileManaging.FileNameAndContent;
import Keys.Key;
import Keys.SingleKey;
import enums.EAction;

import java.util.ArrayList;

public abstract class BasicEncryption implements IBasicEncryption {
    protected SingleKey singleKey;

    public String getType() {
        return singleKey.getType();
    }

    public <T extends Key> ArrayList<FileNameAndContent> encryptFolder(ArrayList<FileNameAndContent> data, T key) {
        ArrayList<FileNameAndContent> encryption = new ArrayList<>();
        int keyValue = ((SingleKey) key).getValue();
        for (FileNameAndContent datum : data) {
            String currentFileEncryption = FileEncryptor.encryptDecrypt(datum.getFileContent(), keyValue, this, EAction.encrypt);
            encryption.add(new FileNameAndContent(datum.getFileName(), currentFileEncryption));
        }
        return encryption;
    }

    public ArrayList<FileNameAndContent> decryptFolder(ArrayList<FileNameAndContent> data, ArrayList<Integer> keys) {
        ArrayList<FileNameAndContent> decryption = new ArrayList<>();
        for (FileNameAndContent datum : data) {
            String currentFileDecryption = FileEncryptor.encryptDecrypt(datum.getFileContent(), keys.get(0), this, EAction.decrypt);
            for (int i = 1; i < keys.size(); i++) {
                currentFileDecryption = FileEncryptor.encryptDecrypt(currentFileDecryption, keys.get(i), this, EAction.decrypt);
            }
            decryption.add(new FileNameAndContent(datum.getFileName(), currentFileDecryption));
        }
        return decryption;
    }

    public Key initKey(String encryptionType) {
        this.singleKey = new SingleKey(encryptionType);
        return singleKey;
    }

    public int getKeyStrength() {
        return singleKey.getKeyStrength();
    }
}
