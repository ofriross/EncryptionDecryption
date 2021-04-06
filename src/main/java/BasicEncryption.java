public interface BasicEncryption extends EncryptionAlgorithm{
    String decryptFile(String data, String key);
}
