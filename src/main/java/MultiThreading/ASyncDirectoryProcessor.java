package MultiThreading;

import FileManaging.FileEncryptor;
import complexEncryptions.IEncryptionAlgorithm;

public class ASyncDirectoryProcessor implements IDirectoryProcessor {
    private final EncryptionDecryptionThread[] encryptionDecryptionThreads;

    public ASyncDirectoryProcessor(int numberOfThreads, String directory, IEncryptionAlgorithm encryptionAlgorithm) {
        FolderEncryptionMonitor folderEncryptionMonitor = new FolderEncryptionMonitor(directory, encryptionAlgorithm);
        FileEncryptor fileEncryptor = new FileEncryptor(encryptionAlgorithm);
        encryptionDecryptionThreads = new EncryptionDecryptionThread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            encryptionDecryptionThreads[i] = new EncryptionDecryptionThread(folderEncryptionMonitor, fileEncryptor, directory);
        }
    }

    public void encryptAndDecryptFolder() {
        for (EncryptionDecryptionThread thread : encryptionDecryptionThreads) {
            thread.start();
        }
    }
}
