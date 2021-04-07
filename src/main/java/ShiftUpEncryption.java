import java.util.Random;

public class ShiftUpEncryption implements BasicEncryption {

    public String encryptFile(String data, Key key) {
        key.makeKey(Key.KeyType.single);
        int keyVal = key.getValue();
        return FileEncryptor.encryptDecrypt(data, keyVal, FileEncryptor.BaseEnc.shiftUp);
    }

    public EncryptionAlgorithm getBase() {
        return this;
    }

    public String decryptFile(String data, String keys) {
        int i = 0;
        int comma = keys.substring(i).indexOf(',');
        int currentKey;
        while (comma != -1) {
            currentKey = Integer.parseInt(keys.substring(i, comma));
            data = FileEncryptor.encryptDecrypt(data, -currentKey, FileEncryptor.BaseEnc.shiftUp);
            i = comma + 1;
            comma = keys.substring(i).indexOf(',');
            if (comma != -1)
                comma += i;
        }
        currentKey = Integer.parseInt(keys.substring(i));
        data = FileEncryptor.encryptDecrypt(data, -currentKey, FileEncryptor.BaseEnc.shiftUp);
        return data;
    }
}
