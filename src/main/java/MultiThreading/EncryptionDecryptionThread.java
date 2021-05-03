package MultiThreading;

import FileManaging.FileEncryptor;
import General.Constants;
import enums.EActionEncryptOrDecrypt;

import java.io.IOException;

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
        FileToEncryptDecrypt fileToEncryptDecrypt = folderEncryptionMonitor.getNextFileToEncryptDecrypt();
        while (fileToEncryptDecrypt != null) {
            if (fileToEncryptDecrypt.getAction() == EActionEncryptOrDecrypt.encrypt) {
                try {
                    fileEncryptor.encryptFile(directory + "\\" + fileToEncryptDecrypt.getFileName(),
                            directory + "\\" + Constants.ENCRYPT_FOLDER_NAME + "\\" + fileToEncryptDecrypt.getFileName(),
                            directory + "\\" + Constants.ENCRYPT_FOLDER_NAME + "\\" + Constants.KEY_FILE_NAME + ".txt");
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }

            } else {
                try {
                    fileEncryptor.decryptFile(directory + "\\" + Constants.ENCRYPT_FOLDER_NAME + "\\" + fileToEncryptDecrypt.getFileName(),
                            directory + "\\" + Constants.DECRYPT_FOLDER_NAME + "\\" + fileToEncryptDecrypt.getFileName(),
                            directory + "\\" + Constants.ENCRYPT_FOLDER_NAME + "\\" + Constants.KEY_FILE_NAME + ".txt");
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
            }
            folderEncryptionMonitor.imDone();
            fileToEncryptDecrypt = folderEncryptionMonitor.getNextFileToEncryptDecrypt();
        }
    }
}
