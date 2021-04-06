public class RepeatEncryption implements EncryptionAlgorithm {
    private int n;
    private EncryptionAlgorithm encryptionAlgorithm;

    public String encryptFile(String data, Key key) {
        String encryption = encryptionAlgorithm.encryptFile(data, key);
        key.setRepeatN(n);
        for (int i = 0; i < n - 1; i++) {
            encryption = encryptionAlgorithm.encryptFile(encryption, key);
        }
        return encryption;
    }

    public EncryptionAlgorithm getBase() {
        return encryptionAlgorithm.getBase();
    }

    /*public String decryptFile(String data, String key) {
        String decryption = encryptionAlgorithm.decryptFile(data, key);
        for (int i = 0; i < n - 1; i++) {
            decryption = encryptionAlgorithm.decryptFile(decryption, key);
        }
        return decryption;
    }*/

    public RepeatEncryption(EncryptionAlgorithm encryptionAlgorithm, int n) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.n = n;
    }
}
