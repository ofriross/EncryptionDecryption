package FileManaging;

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

    public void decryptFile(String fileIn, String fileOut, String keyPath) {
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

    private static ArrayList<Integer> String(String keyString) {
        ArrayList<Integer> keys = new ArrayList<>();
        boolean isLastComma = false;
        for (int i = 0; i < keyString.length(); i++) {
            ;
        }
        return keys;
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
