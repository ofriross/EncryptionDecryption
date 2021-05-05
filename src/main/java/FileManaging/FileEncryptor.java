package FileManaging;

import Events.EventTypeProcess;
import Events.EventTypeProcessDebug;
import Exceptions.InvalidEncryptionKeyException;
import General.Constants;
import Keys.Key;
import complexEncryptions.IEncryptionAlgorithm;
import enums.*;
import logs.EncryptionLogger;
import logs.EncryptionProcessDebugLogEventArgs;
import logs.EncryptionProcessLogEventArgs;

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
        //TODO: don't re-make me every time. use more generic structure
        new EncryptionProcessLogEventArgs(encryptionAlgorithm, inputFilePath, outputFilePath, (new Date().getTime()),
                new EventTypeProcess(EActionEncryptOrDecrypt.encrypt, EInputType.file, EProgress.start));
        EncryptionProcessDebugLogEventArgs encryptionProcessDebugLogEventArgs = new EncryptionProcessDebugLogEventArgs(encryptionAlgorithm,
                inputFilePath, outputFilePath, (new Date().getTime()),
                new EventTypeProcessDebug(EActionEncryptOrDecrypt.encrypt, EInputType.file, EProgress.start));

        String data = FileOperations.readFile(inputFilePath);

        EncryptionLogger.addEncryptionLogEvent(encryptionProcessDebugLogEventArgs, encryptionAlgorithm,
                new EventTypeProcessDebug(EActionEncryptOrDecrypt.encrypt, EInputType.data, EProgress.start),
                ELogType.debug, Optional.of(data));

        String encryptedData = encryptionAlgorithm.performEncryption(data, key);

        EncryptionLogger.addEncryptionLogEvent(encryptionProcessDebugLogEventArgs, encryptionAlgorithm,
                new EventTypeProcessDebug(EActionEncryptOrDecrypt.encrypt, EInputType.data, EProgress.end),
                ELogType.debug, Optional.of(data));

        FileOperations.writeFile(outputFilePath, encryptedData);
        FileOperations.writeFile(keyPath, key.toString());

        new EncryptionProcessLogEventArgs(encryptionAlgorithm, inputFilePath, outputFilePath, (new Date().getTime()),
                new EventTypeProcess(EActionEncryptOrDecrypt.encrypt, EInputType.file, EProgress.end));
    }

    /*private String encryptData(String data) {
        while (doesContainProblematicAsciiValues(encryptedData)) {
            key.getNextKey();
            encryptedData = encryptionAlgorithm.performEncryption(data, key);
        }
        return encryptedData;
    }*/

    public void decryptFile(String inputFilePath, String outputFilePath, String keyPath) throws IOException, InvalidEncryptionKeyException {
        new EncryptionProcessLogEventArgs(encryptionAlgorithm, inputFilePath, outputFilePath, (new Date().getTime()),
                new EventTypeProcess(EActionEncryptOrDecrypt.decrypt, EInputType.file, EProgress.start));
        EncryptionProcessDebugLogEventArgs encryptionProcessDebugLogEventArgs = new EncryptionProcessDebugLogEventArgs(encryptionAlgorithm, inputFilePath, outputFilePath, (new Date().getTime()),
                new EventTypeProcessDebug(EActionEncryptOrDecrypt.decrypt, EInputType.file, EProgress.start));
        String data = FileOperations.readFile(inputFilePath);
        String keyString = FileOperations.readFile(keyPath);

        EncryptionLogger.addEncryptionLogEvent(encryptionProcessDebugLogEventArgs, encryptionAlgorithm,
                new EventTypeProcessDebug(EActionEncryptOrDecrypt.decrypt, EInputType.data, EProgress.start),
                ELogType.debug, Optional.of(data));

        ArrayList<Integer> keysArray;
        checkKeyIsValid(keyString);
        keysArray = arrangeKeys(keyString);

        String decryptedData = encryptionAlgorithm.performDecryption(data, keysArray);

        EncryptionLogger.addEncryptionLogEvent(encryptionProcessDebugLogEventArgs, encryptionAlgorithm,
                new EventTypeProcessDebug(EActionEncryptOrDecrypt.decrypt, EInputType.data, EProgress.end),
                ELogType.debug, Optional.of(data));
        FileOperations.writeFile(outputFilePath, decryptedData);

        new EncryptionProcessLogEventArgs(encryptionAlgorithm, inputFilePath, outputFilePath, (new Date().getTime()),
                new EventTypeProcess(EActionEncryptOrDecrypt.decrypt, EInputType.file, EProgress.end));
    }

    /*
     * 13 and 113 are problematic to identify between them and 'enter'(10)
     * in txt object and there for, it check if they exist
     */
    /*private static boolean doesContainProblematicAsciiValues(String data) {
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == 13 || data.charAt(i) == 113) {
                return true;
            }
        }
        return false;
    }*/
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
