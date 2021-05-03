package MultiThreading;

import FileManaging.FileEncryptor;
import enums.EAction;

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
        FileToEncryptDecrypt fileToEncryptDecrypt = folderEncryptionMonitor.getFileToEncryptDecrypt();
        while (fileToEncryptDecrypt != null) {
            if (fileToEncryptDecrypt.getAction() == EAction.encrypt) {
                fileEncryptor.encryptFile(directory + "\\" + fileToEncryptDecrypt.getFileName(),
                        directory + "\\encrypted\\" + fileToEncryptDecrypt.getFileName(), directory + "\\encrypted\\key.txt");
            } else {
                fileEncryptor.decryptFile(directory + "\\encrypted\\" + fileToEncryptDecrypt.getFileName(),
                        directory + "\\decrypted\\" + fileToEncryptDecrypt.getFileName(), directory + "\\encrypted\\key.txt");
            }
            folderEncryptionMonitor.imDone();
            fileToEncryptDecrypt = folderEncryptionMonitor.getFileToEncryptDecrypt();
        }
    }
}
