import java.util.Random;

public class ShiftMultiplyEncryption implements BasicEncryption {

    public String encryptFile(String data, Key key) {
        key.makeKey(Key.KeyType.single);
        int keyVal = key.getValue();
        if (keyVal % 2 == 0)
            keyVal += 1;
        return FileEncryptor.encryptDecrypt(data, keyVal, FileEncryptor.BaseEnc.shiftMultiply);
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
            if (currentKey % 2 == 0)
                currentKey += 1;
            data = this.decrypt(data, currentKey);
            i = comma + 1;
            comma = keys.substring(i).indexOf(',');
            if (comma != -1)
                comma += i;
        }
        currentKey = Integer.parseInt(keys.substring(i));
        if (currentKey % 2 == 0)
            currentKey += 1;
        data = this.decrypt(data, currentKey);
        return data;
    }

    private String decrypt(String data, int key) {
        StringBuilder encryption = new StringBuilder(data);
        for (int index = 0; index < data.length(); index++) {
            boolean foundI = false;
            int i;
            for (i = 0; i < 128 && !foundI; i++)
                if ((i * key) % 128 == data.charAt(index))
                    foundI = true;
            encryption.setCharAt(index, (char) (i - 1));
        }
        return encryption.toString();
    }
}