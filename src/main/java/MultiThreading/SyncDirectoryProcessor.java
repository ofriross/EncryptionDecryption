package MultiThreading;

import FileManaging.FileEncryptor;
import complexEncryptions.IEncryptionAlgorithm;

public class SyncDirectoryProcessor implements IDirectoryProcessor {
    private final EncryptionDecryptionThread encryptionDecryptionThread;

    public SyncDirectoryProcessor(String directory, IEncryptionAlgorithm encryptionAlgorithm) {
        FolderEncryptionMonitor folderEncryptionMonitor = new FolderEncryptionMonitor(directory, encryptionAlgorithm);
        FileEncryptor fileEncryptor = new FileEncryptor(encryptionAlgorithm);
        encryptionDecryptionThread = new EncryptionDecryptionThread(folderEncryptionMonitor, fileEncryptor, directory);
    }


    public void encryptAndDecryptFolder() {
        try {
            encryptionDecryptionThread.start();
        } catch (RuntimeException runtimeException) {
            runtimeException.printStackTrace();
        }
    }
}
