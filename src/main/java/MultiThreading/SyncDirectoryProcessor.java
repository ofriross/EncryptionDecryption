/*package MultiThreading;

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
}*/
package MultiThreading;

import FileManaging.FileEncryptor;
import FileManaging.FileOperations;
import General.Constants;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EActionEncryptOrDecrypt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SyncDirectoryProcessor implements IDirectoryProcessor {
    public static void encryptAndDecryptFolder(String directoryPath, IEncryptionAlgorithm encryptionAlgorithm) {
        long startTime = new Date().getTime();

        FileOperations.createFolder(directoryPath, Constants.ENCRYPT_FOLDER_NAME);
        FileOperations.createFolder(directoryPath, Constants.DECRYPT_FOLDER_NAME);

        ArrayList<String> allFilesNames;
        try {
            allFilesNames = FileOperations.getTxtFilesNamesFromFolder(directoryPath);
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }

        FileEncryptor fileEncryptor = new FileEncryptor(encryptionAlgorithm);
        ExecutorService executor = Executors.newFixedThreadPool(1);

        for (String allFilesName : allFilesNames) {
            Runnable encryptionDecryptionThread = new EncryptionDecryptionThread(fileEncryptor, directoryPath, allFilesName, EActionEncryptOrDecrypt.encrypt);
            executor.execute(encryptionDecryptionThread);
        }
        executor.shutdown();
        while (!executor.isTerminated()) ;
        System.out.println("The files got encrypted");

        executor = Executors.newFixedThreadPool(1);
        for (String allFilesName : allFilesNames) {
            Runnable encryptionDecryptionThread = new EncryptionDecryptionThread(fileEncryptor, directoryPath, allFilesName, EActionEncryptOrDecrypt.decrypt);
            executor.execute(encryptionDecryptionThread);
        }
        executor.shutdown();
        while (!executor.isTerminated()) ;
        System.out.println("The files got decrypted");

        long endTime = new Date().getTime();
        System.out.println("The encryption and the decryption took in total " + (endTime - startTime) + "(milliseconds) with the Synced Processor.");
    }
}

