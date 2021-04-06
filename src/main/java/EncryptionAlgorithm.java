public interface EncryptionAlgorithm {
    String encryptFile(String data, Key key);
    EncryptionAlgorithm getBase();
}