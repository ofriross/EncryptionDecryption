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
            if (comma!=-1)
                comma+=i;
        }
        currentKey = Integer.parseInt(keys.substring(i));
        data = FileEncryptor.encryptDecrypt(data, -currentKey, FileEncryptor.BaseEnc.shiftUp);
        return data;
    }

    /*private String encryptDecrypt(String data, int key) {
        StringBuilder encryption = new StringBuilder(data);
        for (int index = 0; index < data.length(); index++) {
            int ch = data.charAt(index) + key;
            if (ch > 127)
                ch -= 128;
            if (ch < 0)
                ch += 128;
            encryption.setCharAt(index, (char) ch);
        }
        return encryption.toString();
    }*/

    /*public ShiftUpEncryption() {
        Random r = new Random();
        key = r.nextInt(3) + 1;
    }

    public void setKey(int key) {
        this.key = key;
    }*/
}



/*
import java.util.Random;

public class ShiftUpEncryption implements EncryptionAlgorithm {

    private int key;
    private String fileName;

    private String encrypt(String data) {
        StringBuilder encrypt = new StringBuilder(data);
        for (int index = 0; index < data.length(); index++) {
            int ch = data.charAt(index) + key;
            if (ch > 127)
                ch -= 128;
            if (ch < 0)
                ch += 128;
            encrypt.setCharAt(index, (char) ch);
        }
        return encrypt.toString();
    }

    public String getFileName() {
        return fileName;
    }

    public String encryptFile(FileEncryptor.Act act, String data) {
        if (act == FileEncryptor.Act.decrypt)
            key = -key;
        //String data = FileOp.readFile(fileName);
        String encryption = encrypt(data);
        if (act == FileEncryptor.Act.decrypt)
            key = -key;
        return FileEncryptor.writeEncryption(encryption, act, fileName);
    }

    public ShiftUpEncryption(String fileIn) {
        Random r = new Random();
        this.key = r.nextInt(3) + 1;
        this.fileName = fileIn;
    }

    public ShiftUpEncryption(String fileIn, int key) {
        this.key = key;
        this.fileName = fileIn;
    }
}


*/
