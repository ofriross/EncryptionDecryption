import java.util.ArrayList;
import java.util.Random;

public class DoubleEncryption implements EncryptionAlgorithm {
    EncryptionAlgorithm encryptionAlgorithm;

    public String encryptFile(String data, Key key) {
        key.makeKey(Key.KeyType.doubled);
        String firstEnc = encryptionAlgorithm.encryptFile(data, key.getDouble1());
        return encryptionAlgorithm.encryptFile(firstEnc, key.getDouble2());
    }

    public EncryptionAlgorithm getBase() {
        return encryptionAlgorithm.getBase();
    }

    public DoubleEncryption(EncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }
}
