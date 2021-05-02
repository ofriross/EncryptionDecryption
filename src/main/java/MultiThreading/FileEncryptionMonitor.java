package MultiThreading;

import FileManaging.FileOperations;
import complexEncryptions.EncryptionAlgorithm;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EEventType;
import logs.EncryptionProcessLogEventArgs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class FileEncryptionMonitor {
    private ArrayList<String> allFilesNames;
    private final IEncryptionAlgorithm encryptionAlgorithm;
    private final String directory;
    private boolean isDone = false;
    private int count = 0;

    public FileEncryptionMonitor(String directory, IEncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.directory = directory;
        new EncryptionProcessLogEventArgs(encryptionAlgorithm, directory, directory + "\\encrypted", (new Date()).getTime(), EEventType.encryptFolderStart);
        try {
            allFilesNames = FileOperations.getFileNamesFromFolder(directory);
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }
        FileOperations.createFolder(directory, "encrypted");
    }

    public synchronized String getFileToEncrypt() {
        count++;
        if (count <= allFilesNames.size())
            return allFilesNames.get(count - 1);
        return null;
    }

    public synchronized void imDone() {
        if (count == allFilesNames.size())
            new EncryptionProcessLogEventArgs(encryptionAlgorithm, directory, directory + "\\encrypted", (new Date()).getTime(), EEventType.encryptFolderEnd);

    }
}
