package MultiThreading;

import FileManaging.FileEncryptor;
import complexEncryptions.IEncryptionAlgorithm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ASyncDirectoryProcessor implements IDirectoryProcessor {
    private final EncryptionDecryptionThread[] encryptionDecryptionThreads;

    public ASyncDirectoryProcessor(int numberOfThreads, String directory, IEncryptionAlgorithm encryptionAlgorithm) {
        FolderEncryptionMonitor folderEncryptionMonitor = new FolderEncryptionMonitor(directory, encryptionAlgorithm);
        FileEncryptor fileEncryptor = new FileEncryptor(encryptionAlgorithm);
        encryptionDecryptionThreads = new EncryptionDecryptionThread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            encryptionDecryptionThreads[i] = new EncryptionDecryptionThread(folderEncryptionMonitor, fileEncryptor, directory);
        }
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        for (int i = 0; i < 10; i++) {
            Runnable worker = new EncryptionDecryptionThread(fileEncryptor,directory );
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }

    public void encryptAndDecryptFolder() {
        for (EncryptionDecryptionThread thread : encryptionDecryptionThreads) {
            try {
                thread.start();
            } catch (RuntimeException runtimeException) {
                runtimeException.printStackTrace();
            }
        }
    }
}
