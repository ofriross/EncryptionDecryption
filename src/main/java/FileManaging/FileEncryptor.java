package FileManaging;

import Exceptions.InvalidEncryptionKeyException;
import General.Consts;
import Keys.Key;
import basicEncryptions.BasicEncryption;
import basicEncryptions.IBasicEncryption;
import complexEncryptions.EncryptionAlgorithm;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EAction;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class FileEncryptor {
    public IEncryptionAlgorithm encryptionAlgorithm;
    private Key key;

    public FileEncryptor(IEncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        key = encryptionAlgorithm.initKey();
    }


    public void encryptFile(String fileIn, String fileOut, String keyPath) {
        String data;
        try {
            data = FileOperations.readFile(fileIn);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            return;
        }
        String encryption = encryptionAlgorithm.encryptFile(data, encryptionAlgorithm.initKey());
        try {
            FileOperations.writeFile(fileOut, encryption);
            FileOperations.writeFile(keyPath, key.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }
    }

    public void decryptFile(String fileIn, String fileOut, String keyPath) throws InvalidEncryptionKeyException {
        String data;
        String keyString;
        ArrayList<Integer> keysArray = new ArrayList<>();
        try {
            data = FileOperations.readFile(fileIn);
            keyString = FileOperations.readFile(keyPath);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            return;
        }
        try {
            checkKeyIsValid(keyString);
            keysArray = arrangeKeys(keyString);
        } catch (InvalidEncryptionKeyException exception) {
            exception.printStackTrace();
            return;
        }
        String decryption = encryptionAlgorithm.decryptFile(data, keysArray);
        try {
            FileOperations.writeFile(fileOut, decryption);
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }
    }

    private static ArrayList<Integer> arrangeKeys(String keyString) throws InvalidEncryptionKeyException {
        ArrayList<Integer> keysArray = new ArrayList<>();
        int lastCommaPosition = 0;
        for (int i = 0; i < keyString.length(); i++) {
            char ch = keyString.charAt(i);
            if (ch == ',') {
                int currentKey = Integer.parseInt(keyString.substring(lastCommaPosition, i));
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
                ch += 128;
            ch %= 128;
            encryption.setCharAt(index, (char) ch);
        }
        return encryption.toString();
    }
}
