package basicEncryptions;

import FileManaging.FileEncryptor;
import Keys.Key;
import Keys.SingleKey;
import enums.EAction;

import java.util.ArrayList;

public abstract class BasicEncryption implements IBasicEncryption {
    protected SingleKey singleKey;

    public String getType() {
        return singleKey.getType();
    }

    public <T extends Key> String encryptFile(String data, T key) {
        int keyValue = ((SingleKey) key).getValue();
        return FileEncryptor.encryptDecrypt(data, keyValue, this, EAction.encrypt);
    }

    public String decryptFile(String data, ArrayList<Integer> keys) {
        String decryption = FileEncryptor.encryptDecrypt(data, keys.get(0), this, EAction.decrypt);
        for (int i = 1; i < keys.size(); i++)
            decryption = FileEncryptor.encryptDecrypt(decryption, keys.get(i), this, EAction.decrypt);
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
