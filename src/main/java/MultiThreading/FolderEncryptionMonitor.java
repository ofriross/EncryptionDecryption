package MultiThreading;

import FileManaging.FileOperations;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EAction;
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

    public FolderEncryptionMonitor(String directory, IEncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.directory = directory;
        try {
            allFilesNames = FileOperations.getFileNamesFromFolder(directory);
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }
        FileOperations.createFolder(directory, "encrypted");
        FileOperations.createFolder(directory, "decrypted");
    }

    public synchronized FileToEncryptDecrypt getFileToEncryptDecrypt() {
        startedThreadsCount++;
        if (startedThreadsCount == 1)
            new EncryptionProcessLogEventArgs(encryptionAlgorithm, directory, directory + "\\encrypted", (new Date()).getTime(), EEventType.encryptFolderStart);
        if (startedThreadsCount <= allFilesNames.size())
            return new FileToEncryptDecrypt(allFilesNames.get(startedThreadsCount - 1), EAction.encrypt);
        if (startedThreadsCount == allFilesNames.size() + 1)
            new EncryptionProcessLogEventArgs(encryptionAlgorithm,  directory + "\\encrypted", directory + "\\decrypted", (new Date()).getTime(), EEventType.decryptFolderStart);
        if (startedThreadsCount <= 2 * allFilesNames.size())
            return new FileToEncryptDecrypt(allFilesNames.get(startedThreadsCount - 1 - allFilesNames.size()), EAction.decrypt);
        return null;
    }

    public synchronized void imDone() {
        endedThreadsCount++;
        if (endedThreadsCount == allFilesNames.size())
            new EncryptionProcessLogEventArgs(encryptionAlgorithm, directory, directory + "\\encrypted", (new Date()).getTime(), EEventType.encryptFolderEnd);
        if (endedThreadsCount == 2 * allFilesNames.size())
            new EncryptionProcessLogEventArgs(encryptionAlgorithm, directory + "\\encrypted", directory + "\\decrypted", (new Date()).getTime(), EEventType.decryptFolderEnd);

    }
}
