package FileManaging;

import Exceptions.InvalidEncryptionKeyException;
import General.Consts;
import Keys.Key;
import basicEncryptions.IBasicEncryption;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EAction;
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
    private Key key;

    public FileEncryptor(IEncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        key = encryptionAlgorithm.initKey();
    }


    public void encryptFile(String fileIn, String fileOut, String keyPath) {
        new EncryptionProcessLogEventArgs(encryptionAlgorithm, fileIn, fileOut, (new Date().getTime()), EEventType.encryptStart);
        EncryptionProcessDebugLogEventArgs encryptionProcessDebugLogEventArgs = new EncryptionProcessDebugLogEventArgs(encryptionAlgorithm, fileIn, fileOut, (new Date().getTime()), EEventType.dataBeforeEncryption);
        String data;
        try {
            data = FileOperations.readFile(fileIn);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            return;
        }
        EncryptionLogger.addEncryptionLogEvent(encryptionProcessDebugLogEventArgs, encryptionAlgorithm, EEventType.dataBeforeEncryption, ELogType.debug, Optional.of(data));
        String encryption = encryptionAlgorithm.encryptFile(data, key);
        while (doesContainProblematicAsciiValues(encryption)) {
            key.updateKey();
            encryption = encryptionAlgorithm.encryptFile(data, key);
        }
        EncryptionLogger.addEncryptionLogEvent(encryptionProcessDebugLogEventArgs, encryptionAlgorithm, EEventType.dataAfterEncryption, ELogType.debug, Optional.of(encryption));
        try {
            FileOperations.writeFile(fileOut, encryption);
            FileOperations.writeFile(keyPath, key.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }
        new EncryptionProcessLogEventArgs(encryptionAlgorithm, fileIn, fileOut, (new Date().getTime()), EEventType.encryptEnd);
    }

    public void decryptFile(String fileIn, String fileOut, String keyPath) {
        new EncryptionProcessLogEventArgs(encryptionAlgorithm, fileIn, fileOut, (new Date().getTime()), EEventType.decryptStart);
        EncryptionProcessDebugLogEventArgs encryptionProcessDebugLogEventArgs = new EncryptionProcessDebugLogEventArgs(encryptionAlgorithm, fileIn, fileOut, (new Date().getTime()), EEventType.dataBeforeEncryption);
        String data;
        String keyString;
        ArrayList<Integer> keysArray;
        try {
            data = FileOperations.readFile(fileIn);
            keyString = FileOperations.readFile(keyPath);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            return;
        }
        EncryptionLogger.addEncryptionLogEvent(encryptionProcessDebugLogEventArgs, encryptionAlgorithm, EEventType.dataBeforeDecryption, ELogType.debug, Optional.of(data));
        try {
            checkKeyIsValid(keyString);
            keysArray = arrangeKeys(keyString);
        } catch (InvalidEncryptionKeyException exception) {
            exception.printStackTrace();
            return;
        }
        String decryption = encryptionAlgorithm.decryptFile(data, keysArray);
        EncryptionLogger.addEncryptionLogEvent(encryptionProcessDebugLogEventArgs, encryptionAlgorithm, EEventType.dataAfterDecryption, ELogType.debug, Optional.of(data));
        try {
            FileOperations.writeFile(fileOut, decryption);
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }
        new EncryptionProcessLogEventArgs(encryptionAlgorithm, fileIn, fileOut, (new Date().getTime()), EEventType.decryptEnd);
    }

    private static boolean doesContainProblematicAsciiValues(String data) {
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == 13 || data.charAt(i) == 113)
                return true;
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
                if (currentKey < 0 || currentKey > Consts.MAX_ASCII_VALUE)
                    throw new InvalidEncryptionKeyException("the key '" + currentKey +
                            "' is not valid, key values must be between 0 and " + Consts.MAX_ASCII_VALUE);
                keysArray.add(currentKey);
                lastCommaPosition = i;
            }
        }
        int currentKey = Integer.parseInt(keyString.substring(lastCommaPosition + 1));
        if (currentKey < 0 || currentKey > Consts.MAX_ASCII_VALUE)
            throw new InvalidEncryptionKeyException("the key '" + currentKey +
                    "' is not valid, key values must be between 0 and " + Consts.MAX_ASCII_VALUE);
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

    public static String encryptDecrypt(String data, int key, IBasicEncryption basicEncryption, EAction action) {
        StringBuilder encryption = new StringBuilder(data);
        for (int index = 0; index < data.length(); index++) {
            int ch = basicEncryption.computeChar(data.charAt(index), key, action);
            while (ch < 0)
                ch += (Consts.MAX_ASCII_VALUE + 1);
            ch %= (Consts.MAX_ASCII_VALUE + 1);
            encryption.setCharAt(index, (char) ch);
        }
        return encryption.toString();
    }
}
