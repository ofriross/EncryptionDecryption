import java.util.ArrayList;
import java.util.Random;

public class DoubleEncryption implements EncryptionAlgorithm {
    EncryptionAlgorithm encryptionAlgorithm;

    public String encryptFile(String data, Key key) {
        key.makeKey(Key.KeyType.doubled);
        String firstEnc = encryptionAlgorithm.encryptFile(data, key.getDouble1());
        return encryptionAlgorithm.encryptFile(firstEnc, key.getDouble2());
        //FileOp.writeFile(keyPath, firstKey.concat(",").concat(FileOp.readFile(keyPath)));
    }

    public EncryptionAlgorithm getBase() {
        return encryptionAlgorithm.getBase();
    }

    /*public String decryptFile(String data, String keys) {
        int comma = keys.indexOf(',');
        String firstEnc = encryptionAlgorithm.decryptFile(data, keys.substring(0, comma));
        return encryptionAlgorithm.decryptFile(firstEnc, keys.substring(comma + 1));
    }*/


    public DoubleEncryption(EncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }
}
