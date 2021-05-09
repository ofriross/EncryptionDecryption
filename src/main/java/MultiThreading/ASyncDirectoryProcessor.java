package MultiThreading;

import FileManaging.FileEncryptor;
import FileManaging.FileOperations;
import General.Constants;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EActionEncryptOrDecrypt;
import enums.EEventType;
import enums.EInputType;
import enums.EProgress;
import logs.EncryptionLogger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ASyncDirectoryProcessor implements IDirectoryProcessor {
    public static void encryptAndDecryptFolder(int numberOfThreads, String directoryPath, IEncryptionAlgorithm encryptionAlgorithm) {
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
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        for (String allFilesName : allFilesNames) {
            Runnable encryptionDecryptionThread = new EncryptionDecryptionThread(fileEncryptor, directoryPath, allFilesName, EActionEncryptOrDecrypt.encrypt);
            executor.execute(encryptionDecryptionThread);
        }
        executor.shutdown();
        while (!executor.isTerminated()) ;
        System.out.println("The files got encrypted");

        executor = Executors.newFixedThreadPool(numberOfThreads);
        EncryptionLogger.addLog(Optional.empty(), encryptionAlgorithm,
                directoryPath,
                Path.of(directoryPath, Constants.ENCRYPT_FOLDER_NAME).toString(),
                new Date().getTime(),
                EActionEncryptOrDecrypt.decrypt, EInputType.folder, EProgress.start, EEventType.process);
        for (String allFilesName : allFilesNames) {
            Runnable encryptionDecryptionThread = new EncryptionDecryptionThread(fileEncryptor, directoryPath, allFilesName, EActionEncryptOrDecrypt.decrypt);
            executor.execute(encryptionDecryptionThread);
        }
        executor.shutdown();
        while (!executor.isTerminated()) ;
        System.out.println("The files got decrypted");

        long endTime = new Date().getTime();
        System.out.println("The encryption and the decryption took in total " + (endTime - startTime) + "(milliseconds) with the ASynced Processor.");
    }
}
