package complexEncryptions;

import Keys.Key;

public abstract class EncryptionAlgorithm implements IEncryptionAlgorithm {
    protected Key key;
    protected final IEncryptionAlgorithm encryptionAlgorithm;

    public int getKeyStrength() {
        return key.getKeyStrength();
    }

    public String getType() {
        return key.getType();
    }

    public EncryptionAlgorithm(IEncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }
}
