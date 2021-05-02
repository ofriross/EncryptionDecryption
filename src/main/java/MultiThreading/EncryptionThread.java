package MultiThreading;

import FileManaging.FileEncryptor;

public class EncryptionThread extends Thread {
    private final FileEncryptionMonitor fileEncryptionMonitor;
    private final FileEncryptor fileEncryptor;
    private final String directory;

    public EncryptionThread(FileEncryptionMonitor fileEncryptionMonitor, FileEncryptor fileEncryptor, String directory) {
        this.fileEncryptionMonitor = fileEncryptionMonitor;
        this.fileEncryptor = fileEncryptor;
        this.directory = directory;
    }

    @Override
    public void run() {
        String fileName = fileEncryptionMonitor.getFileToEncrypt();
        while (fileName != null) {
            fileEncryptor.encryptFile(directory + "\\" + fileName,
                    directory + "\\encrypted\\" + fileName, directory + "\\encrypted\\key.txt");
            fileEncryptionMonitor.imDone();
            fileName = fileEncryptionMonitor.getFileToEncrypt();
        }
    }
}
