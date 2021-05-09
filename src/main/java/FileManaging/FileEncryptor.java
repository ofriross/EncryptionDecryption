package FileManaging;

import Exceptions.InvalidEncryptionKeyException;
import General.Constants;
import Keys.Key;
import complexEncryptions.IEncryptionAlgorithm;
import enums.*;
import logs.EncryptionLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class FileEncryptor {
    public IEncryptionAlgorithm encryptionAlgorithm;
    private final Key key;

    public IEncryptionAlgorithm getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public FileEncryptor(IEncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        key = encryptionAlgorithm.initKey();
    }

    public void encryptFile(String inputFilePath, String outputFilePath, String keyPath) throws IOException {
        EncryptionLogger.addLog(Optional.empty(), encryptionAlgorithm, inputFilePath, outputFilePath, new Date().getTime(),
                EActionEncryptOrDecrypt.encrypt, EInputType.file, EProgress.start, EEventType.process);

        String data = FileOperations.readFile(inputFilePath);
        EncryptionLogger.addLog(Optional.of(data), encryptionAlgorithm, inputFilePath, outputFilePath, new Date().getTime(),
                EActionEncryptOrDecrypt.encrypt, EInputType.file, EProgress.start, EEventType.processDebug);

        String encryptedData = encryptionAlgorithm.performEncryption(data, key);
        EncryptionLogger.addLog(Optional.of(encryptedData), encryptionAlgorithm, inputFilePath, outputFilePath, new Date().getTime(),
                EActionEncryptOrDecrypt.encrypt, EInputType.file, EProgress.end, EEventType.processDebug);

        FileOperations.writeFile(outputFilePath, encryptedData);
        FileOperations.writeFile(keyPath, key.toString());

        EncryptionLogger.addLog(Optional.empty(), encryptionAlgorithm, inputFilePath, outputFilePath, new Date().getTime(),
                EActionEncryptOrDecrypt.encrypt, EInputType.file, EProgress.end, EEventType.process);
    }

    public void decryptFile(String inputFilePath, String outputFilePath, String keyPath) throws IOException, InvalidEncryptionKeyException {
        EncryptionLogger.addLog(Optional.empty(), encryptionAlgorithm, inputFilePath, outputFilePath, new Date().getTime(),
                EActionEncryptOrDecrypt.decrypt, EInputType.file, EProgress.start, EEventType.process);

        String data = FileOperations.readFile(inputFilePath);
        EncryptionLogger.addLog(Optional.of(data), encryptionAlgorithm, inputFilePath, outputFilePath, new Date().getTime(),
                EActionEncryptOrDecrypt.decrypt, EInputType.file, EProgress.start, EEventType.processDebug);

        String keyString = FileOperations.readFile(keyPath);

        ArrayList<Integer> keysArray;
        checkKeyIsValid(keyString);
        keysArray = arrangeKeys(keyString);

        String decryptedData = encryptionAlgorithm.performDecryption(data, keysArray);
        EncryptionLogger.addLog(Optional.of(decryptedData), encryptionAlgorithm, inputFilePath, outputFilePath, new Date().getTime(),
                EActionEncryptOrDecrypt.decrypt, EInputType.file, EProgress.end, EEventType.processDebug);

        FileOperations.writeFile(outputFilePath, decryptedData);

        EncryptionLogger.addLog(Optional.empty(), encryptionAlgorithm, inputFilePath, outputFilePath, new Date().getTime(),
                EActionEncryptOrDecrypt.decrypt, EInputType.file, EProgress.end, EEventType.process);
    }

    private static ArrayList<Integer> arrangeKeys(String keyString) throws InvalidEncryptionKeyException {
        ArrayList<Integer> keysArray = new ArrayList<>();
        int lastCommaPosition = -1;
        for (int i = 0; i < keyString.length(); i++) {
            char ch = keyString.charAt(i);
            if (ch == ',') {
                int currentKey = Integer.parseInt(keyString.substring(lastCommaPosition + 1, i));
                if (currentKey < 0 || currentKey > Constants.MAX_ASCII_VALUE)
                    throw new InvalidEncryptionKeyException("the key '" + currentKey +
                            "' is not valid, key values must be between 0 and " + Constants.MAX_ASCII_VALUE);
                keysArray.add(currentKey);
                lastCommaPosition = i;
            }
        }
        int currentKey = Integer.parseInt(keyString.substring(lastCommaPosition + 1));
        if (currentKey < 0 || currentKey > Constants.MAX_ASCII_VALUE)
            throw new InvalidEncryptionKeyException("the key '" + currentKey +
                    "' is not valid, key values must be between 0 and " + Constants.MAX_ASCII_VALUE);
        keysArray.add(currentKey);
        return keysArray;
    }

    private static void checkKeyIsValid(String keyString) throws InvalidEncryptionKeyException {
        String basicErrorMessage = "Key from file must be like 'x,y,z'(x,y,z-numbers)";
        boolean isLastComma = true;
        for (int i = 0; i < keyString.length(); i++) {
            int ch = keyString.charAt(i);
            if (ch == ',')
                if (isLastComma)
                    throw new InvalidEncryptionKeyException(basicErrorMessage);
                else
                    isLastComma = true;
            else {
                isLastComma = false;
                if (ch < '0' || ch > '9')
                    throw new InvalidEncryptionKeyException(basicErrorMessage);
            }
        }
        if (isLastComma)
            throw new InvalidEncryptionKeyException(basicErrorMessage);
    }
}
