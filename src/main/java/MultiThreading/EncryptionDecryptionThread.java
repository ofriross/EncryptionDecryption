package MultiThreading;

import FileManaging.FileEncryptor;
import General.Constants;
import enums.EActionEncryptOrDecrypt;
import enums.EInputType;
import enums.ELogType;
import enums.EProgress;
import logs.EncryptionExceptionLogEventArgs;
import logs.EncryptionLogger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.Optional;

public class EncryptionDecryptionThread implements Runnable {
    //private final FolderEncryptionMonitor folderEncryptionMonitor;
    private final FileEncryptor fileEncryptor;
    private final String directoryPath;
    private final String fileName;
    private final EActionEncryptOrDecrypt actionEncryptOrDecrypt;

    /*public EncryptionDecryptionThread(FolderEncryptionMonitor folderEncryptionMonitor, FileEncryptor fileEncryptor, String directory) {
        this.folderEncryptionMonitor = folderEncryptionMonitor;
        this.fileEncryptor = fileEncryptor;
        this.directory = directory;
    }*/

    public EncryptionDecryptionThread(FileEncryptor fileEncryptor, String directoryPath, String fileName, EActionEncryptOrDecrypt actionEncryptOrDecrypt) {
        this.fileEncryptor = fileEncryptor;
        this.directoryPath = directoryPath;
        this.fileName = fileName;
        this.actionEncryptOrDecrypt = actionEncryptOrDecrypt;
    }

    @Override
    public void run() {
        if (actionEncryptOrDecrypt == EActionEncryptOrDecrypt.encrypt) {
            try {
                fileEncryptor.encryptFile(Path.of(directoryPath, fileName).toString(),
                        Path.of(directoryPath, Constants.ENCRYPT_FOLDER_NAME, fileName).toString(),
                        Path.of(directoryPath, Constants.ENCRYPT_FOLDER_NAME, Constants.KEY_FILE_NAME).toString());
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        } else {
            try {
                fileEncryptor.decryptFile(Path.of(directoryPath, Constants.ENCRYPT_FOLDER_NAME, fileName).toString(),
                        Path.of(directoryPath, Constants.DECRYPT_FOLDER_NAME, fileName).toString(),
                        Path.of(directoryPath, Constants.ENCRYPT_FOLDER_NAME, Constants.KEY_FILE_NAME).toString());
            } catch (Exception exception) {
                EncryptionExceptionLogEventArgs encryptionExceptionLogEventArgs = new EncryptionExceptionLogEventArgs(fileEncryptor.getEncryptionAlgorithm(),
                        Path.of(directoryPath, Constants.ENCRYPT_FOLDER_NAME, fileName).toString(),
                        Path.of(directoryPath, Constants.DECRYPT_FOLDER_NAME, fileName).toString(),
                        (new Date()).getTime(), EActionEncryptOrDecrypt.encrypt, EInputType.folder, EProgress.start);
                EncryptionLogger.addEncryptionLogEvent(encryptionExceptionLogEventArgs, fileEncryptor.getEncryptionAlgorithm(), ELogType.error, Optional.of(exception.toString()));
                exception.printStackTrace();
            }
        }
    }
}
