package MultiThreading;

import FileManaging.FileOperations;
import General.Constants;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EActionEncryptOrDecrypt;
import enums.EEventType;
import logs.EncryptionProcessLogEventArgs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class FolderEncryptionMonitor {
    private ArrayList<String> allFilesNames;
    private final IEncryptionAlgorithm encryptionAlgorithm;
    private final String directory;
    private int startedThreadsCount = 0;
    private int endedThreadsCount = 0;
    private boolean logPrintDecryptOnce = false;

    public FolderEncryptionMonitor(String directory, IEncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.directory = directory;
        try {
            allFilesNames = FileOperations.getFileNamesFromFolder(directory);
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }
        FileOperations.createFolder(directory, Constants.ENCRYPT_FOLDER_NAME);
        FileOperations.createFolder(directory, Constants.DECRYPT_FOLDER_NAME);
    }

    public synchronized FileToEncryptDecrypt getNextFileToEncryptDecrypt() {
        startedThreadsCount++;
        if (startedThreadsCount == 1)
            new EncryptionProcessLogEventArgs(encryptionAlgorithm, directory, directory + "\\" + Constants.ENCRYPT_FOLDER_NAME, (new Date()).getTime(), EEventType.encryptFolderStart);
        if (startedThreadsCount <= allFilesNames.size())
            return new FileToEncryptDecrypt(allFilesNames.get(startedThreadsCount - 1), EActionEncryptOrDecrypt.encrypt);
        while (endedThreadsCount < allFilesNames.size()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!logPrintDecryptOnce) {
            new EncryptionProcessLogEventArgs(encryptionAlgorithm, directory + "\\" + Constants.ENCRYPT_FOLDER_NAME, directory + "\\" + Constants.DECRYPT_FOLDER_NAME, (new Date()).getTime(), EEventType.decryptFolderStart);
            logPrintDecryptOnce = true;
        }
        if (startedThreadsCount <= 2 * allFilesNames.size())
            return new FileToEncryptDecrypt(allFilesNames.get(startedThreadsCount - 1 - allFilesNames.size()), EActionEncryptOrDecrypt.decrypt);
        return null;
    }

    public synchronized void imDone() {
        endedThreadsCount++;
        if (endedThreadsCount == allFilesNames.size())
            new EncryptionProcessLogEventArgs(encryptionAlgorithm, directory, directory + "\\" + Constants.ENCRYPT_FOLDER_NAME, (new Date()).getTime(), EEventType.encryptFolderEnd);
        if (endedThreadsCount == 2 * allFilesNames.size())
            new EncryptionProcessLogEventArgs(encryptionAlgorithm, directory + "\\" + Constants.ENCRYPT_FOLDER_NAME, directory + "\\" + Constants.DECRYPT_FOLDER_NAME, (new Date()).getTime(), EEventType.decryptFolderEnd);
        notifyAll();
    }
}
