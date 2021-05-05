package MultiThreading;

import Events.EventTypeProcess;
import FileManaging.FileOperations;
import General.Constants;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EActionEncryptOrDecrypt;
import enums.EInputType;
import enums.EProgress;
import logs.EncryptionProcessLogEventArgs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;

public class FolderEncryptionMonitor {
    private ArrayList<String> allFilesNames;
    private final IEncryptionAlgorithm encryptionAlgorithm;
    private final String directory;
    private int startedThreadsCount = 0;
    private int endedThreadsCount = 0;

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

    public synchronized FileToEncryptDecrypt getNextFileToEncryptOrDecrypt() {
        int currentThreadTurn = startedThreadsCount;
        startedThreadsCount++;
        if (currentThreadTurn == 0)
            new EncryptionProcessLogEventArgs(encryptionAlgorithm, directory,
                    Path.of(directory, Constants.ENCRYPT_FOLDER_NAME).toString(),
                    (new Date()).getTime(),
                    new EventTypeProcess(EActionEncryptOrDecrypt.encrypt, EInputType.folder, EProgress.start));
        if (currentThreadTurn < allFilesNames.size())
            return new FileToEncryptDecrypt(allFilesNames.get(currentThreadTurn), EActionEncryptOrDecrypt.encrypt);
        while (endedThreadsCount < allFilesNames.size()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (currentThreadTurn == allFilesNames.size()) {
            new EncryptionProcessLogEventArgs(encryptionAlgorithm,
                    Path.of(directory, Constants.ENCRYPT_FOLDER_NAME).toString(),
                    Path.of(directory, Constants.DECRYPT_FOLDER_NAME).toString(),
                    (new Date()).getTime(),
                    new EventTypeProcess(EActionEncryptOrDecrypt.decrypt, EInputType.folder, EProgress.start));
        }
        if (currentThreadTurn < 2 * allFilesNames.size()) {
            return new FileToEncryptDecrypt(allFilesNames.get(currentThreadTurn - allFilesNames.size()), EActionEncryptOrDecrypt.decrypt);
        }
        return null;
    }

    public synchronized void imDone() {
        endedThreadsCount++;
        if (endedThreadsCount == allFilesNames.size())
            new EncryptionProcessLogEventArgs(encryptionAlgorithm, directory,
                    Path.of(directory, Constants.ENCRYPT_FOLDER_NAME).toString(),
                    (new Date()).getTime(),
                    new EventTypeProcess(EActionEncryptOrDecrypt.encrypt, EInputType.folder, EProgress.end));
        if (endedThreadsCount == 2 * allFilesNames.size())
            new EncryptionProcessLogEventArgs(encryptionAlgorithm,
                    Path.of(directory, Constants.ENCRYPT_FOLDER_NAME).toString(),
                    Path.of(directory, Constants.DECRYPT_FOLDER_NAME).toString(),
                    (new Date()).getTime(),
                    new EventTypeProcess(EActionEncryptOrDecrypt.decrypt, EInputType.folder, EProgress.end));
        notifyAll();
    }
}
