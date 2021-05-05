package MultiThreading;

import FileManaging.FileEncryptor;
import General.Constants;
import enums.EActionEncryptOrDecrypt;

import java.io.IOException;
import java.nio.file.Path;

public class EncryptionDecryptionThread extends Thread {
    private final FolderEncryptionMonitor folderEncryptionMonitor;
    private final FileEncryptor fileEncryptor;
    private final String directory;

    public EncryptionDecryptionThread(FolderEncryptionMonitor folderEncryptionMonitor, FileEncryptor fileEncryptor, String directory) {
        this.folderEncryptionMonitor = folderEncryptionMonitor;
        this.fileEncryptor = fileEncryptor;
        this.directory = directory;
    }

    @Override
    public void run() {
        FileToEncryptDecrypt fileToEncryptDecrypt = folderEncryptionMonitor.getNextFileToEncryptOrDecrypt();
        while (fileToEncryptDecrypt != null) {
            if (fileToEncryptDecrypt.getAction() == EActionEncryptOrDecrypt.encrypt) {
                try {
                    fileEncryptor.encryptFile(Path.of(directory, fileToEncryptDecrypt.getFileName()).toString(),
                            Path.of(directory, Constants.ENCRYPT_FOLDER_NAME, fileToEncryptDecrypt.getFileName()).toString(),
                            Path.of(directory, Constants.ENCRYPT_FOLDER_NAME, Constants.KEY_FILE_NAME).toString());
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }

            } else {
                try {
                    fileEncryptor.decryptFile(Path.of(directory, Constants.ENCRYPT_FOLDER_NAME, fileToEncryptDecrypt.getFileName()).toString(),
                            Path.of(directory, Constants.DECRYPT_FOLDER_NAME, fileToEncryptDecrypt.getFileName()).toString(),
                            Path.of(directory, Constants.ENCRYPT_FOLDER_NAME, Constants.KEY_FILE_NAME).toString());
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
            }
            folderEncryptionMonitor.imDone();
            fileToEncryptDecrypt = folderEncryptionMonitor.getNextFileToEncryptOrDecrypt();
        }
    }
}
