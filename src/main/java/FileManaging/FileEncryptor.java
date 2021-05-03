package FileManaging;

import Exceptions.InvalidEncryptionKeyException;
import General.Constants;
import Keys.Key;
import basicEncryptions.IBasicEncryption;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EActionEncryptOrDecrypt;
import enums.EEventType;
import enums.ELogType;
import logs.EncryptionLogger;
import logs.EncryptionProcessDebugLogEventArgs;
import logs.EncryptionProcessLogEventArgs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class FileEncryptor {
    public IEncryptionAlgorithm encryptionAlgorithm;
    private final Key key;

    public FileEncryptor(IEncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        key = encryptionAlgorithm.initKey();
    }

    public void encryptFile(String inputFilePath, String outputFilePath, String keyPath) throws IOException {
        //TODO: don't re-make me every time.
        new EncryptionProcessLogEventArgs(encryptionAlgorithm, inputFilePath, outputFilePath, (new Date().getTime()), EEventType.encryptFileStart);
        EncryptionProcessDebugLogEventArgs encryptionProcessDebugLogEventArgs = new EncryptionProcessDebugLogEventArgs(encryptionAlgorithm, inputFilePath, outputFilePath, (new Date().getTime()), EEventType.dataBeforeEncryption);
        String data;
        try {
            data = FileOperations.readFile(inputFilePath);
        } catch (FileNotFoundException exception) {
            throw exception;
        }
        EncryptionLogger.addEncryptionLogEvent(encryptionProcessDebugLogEventArgs, encryptionAlgorithm, EEventType.dataBeforeEncryption, ELogType.debug, Optional.of(data));
        String encryptedData = encryptData(data);
        EncryptionLogger.addEncryptionLogEvent(encryptionProcessDebugLogEventArgs, encryptionAlgorithm, EEventType.dataAfterEncryption, ELogType.debug, Optional.of(encryptedData));
        try {
            FileOperations.writeFile(outputFilePath, encryptedData);
            FileOperations.writeFile(keyPath, key.toString());
        } catch (IOException exception) {
            throw exception;
        }
        new EncryptionProcessLogEventArgs(encryptionAlgorithm, inputFilePath, outputFilePath, (new Date().getTime()), EEventType.encryptFileEnd);
    }

    private String encryptData(String data) {
        String encryptedData = encryptionAlgorithm.performEncryption(data, key);
        //TODO: catch the problem in computeChar
        while (doesContainProblematicAsciiValues(encryptedData)) {
            key.getNextKey();
            encryptedData = encryptionAlgorithm.performEncryption(data, key);
        }
        return encryptedData;
    }

    public void decryptFile(String inputFilePath, String outputFilePath, String keyPath) throws IOException, InvalidEncryptionKeyException {
        new EncryptionProcessLogEventArgs(encryptionAlgorithm, inputFilePath, outputFilePath, (new Date().getTime()), EEventType.decryptFileStart);
        EncryptionProcessDebugLogEventArgs encryptionProcessDebugLogEventArgs = new EncryptionProcessDebugLogEventArgs(encryptionAlgorithm, inputFilePath, outputFilePath, (new Date().getTime()), EEventType.dataBeforeEncryption);
        String data, keyString;
        try {
            data = FileOperations.readFile(inputFilePath);
            keyString = FileOperations.readFile(keyPath);
        } catch (FileNotFoundException exception) {
            throw exception;
        }
        EncryptionLogger.addEncryptionLogEvent(encryptionProcessDebugLogEventArgs, encryptionAlgorithm, EEventType.dataBeforeDecryption, ELogType.debug, Optional.of(data));
        ArrayList<Integer> keysArray;
        try {
            checkKeyIsValid(keyString);
            keysArray = arrangeKeys(keyString);
        } catch (InvalidEncryptionKeyException exception) {
            throw exception;
        }
        String decryptedData = encryptionAlgorithm.performDecryption(data, keysArray);
        EncryptionLogger.addEncryptionLogEvent(encryptionProcessDebugLogEventArgs, encryptionAlgorithm, EEventType.dataAfterDecryption, ELogType.debug, Optional.of(data));
        try {
            FileOperations.writeFile(outputFilePath, decryptedData);
        } catch (IOException exception) {
            throw exception;
        }
        new EncryptionProcessLogEventArgs(encryptionAlgorithm, inputFilePath, outputFilePath, (new Date().getTime()), EEventType.decryptFileEnd);
    }

    /**
     * 13 and 113 are problematic to identify between them and 'enter'(10)
     * in txt object and there for, it check if they exist
     */
    private static boolean doesContainProblematicAsciiValues(String data) {
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == 13 || data.charAt(i) == 113) {
                return true;
            }
        }
        return false;
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
        boolean isLastComma = true;
        for (int i = 0; i < keyString.length(); i++) {
            int ch = keyString.charAt(i);
            if (ch == ',')
                if (isLastComma)
                    throw new InvalidEncryptionKeyException("Key from file must be like 'x,y,z'(x,y,z-numbers)");
                else
                    isLastComma = true;
            else {
                isLastComma = false;
                if (ch < '0' || ch > '9')
                    throw new InvalidEncryptionKeyException("Key from file must be like 'x,y,z'(x,y,z-numbers)");
            }
        }
        if (isLastComma)
            throw new InvalidEncryptionKeyException("Key from file must be like 'x,y,z'(x,y,z-numbers)");
    }
}
