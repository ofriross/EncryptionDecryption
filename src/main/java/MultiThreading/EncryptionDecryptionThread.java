package MultiThreading;

import FileManaging.FileEncryptor;
import General.Constants;
import enums.EActionEncryptOrDecrypt;
import enums.EEventType;
import enums.EInputType;
import enums.EProgress;
import logs.EncryptionLogger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.Optional;

public class EncryptionDecryptionThread implements Runnable {
    private final FileEncryptor fileEncryptor;
    private final String directoryPath;
    private final String fileName;
    private final EActionEncryptOrDecrypt actionEncryptOrDecrypt;

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
                EncryptionLogger.addLog(Optional.of(exception.toString()), fileEncryptor.getEncryptionAlgorithm(),
                        Path.of(directoryPath, Constants.ENCRYPT_FOLDER_NAME, fileName).toString(),
                        Path.of(directoryPath, Constants.DECRYPT_FOLDER_NAME, fileName).toString(),
                        new Date().getTime(), EActionEncryptOrDecrypt.encrypt, EInputType.folder,
                        EProgress.end, EEventType.exception);
                exception.printStackTrace();
            }
        } else {
            try {
                fileEncryptor.decryptFile(Path.of(directoryPath, Constants.ENCRYPT_FOLDER_NAME, fileName).toString(),
                        Path.of(directoryPath, Constants.DECRYPT_FOLDER_NAME, fileName).toString(),
                        Path.of(directoryPath, Constants.ENCRYPT_FOLDER_NAME, Constants.KEY_FILE_NAME).toString());
            } catch (Exception exception) {
                EncryptionLogger.addLog(Optional.of(exception.toString()), fileEncryptor.getEncryptionAlgorithm(),
                        Path.of(directoryPath, Constants.ENCRYPT_FOLDER_NAME, fileName).toString(),
                        Path.of(directoryPath, Constants.DECRYPT_FOLDER_NAME, fileName).toString(),
                        new Date().getTime(), EActionEncryptOrDecrypt.decrypt, EInputType.folder,
                        EProgress.end, EEventType.exception);
                exception.printStackTrace();
            }
        }
    }
}
