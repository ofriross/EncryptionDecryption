package FileManaging;

import complexEncryptions.EncryptionAlgorithm;

import java.util.Random;

public class FileEncryptor {
    public EncryptionAlgorithm encryptionAlgorithm;
    private String fileName;

    /*public String writeEncryption(String encryption, Act act) {
        String outFile;
        String substring = fileName.substring(fileName.length() - 14, fileName.length() - 4);
        String substring1 = fileName.substring(0, fileName.length() - 14);
        String substring2 = fileName.substring(fileName.length() - 4);
        String substring3 = fileName.substring(0, fileName.length() - 4);
        if (act == Act.encrypt)
            if (substring.equals("_decrypted"))
                outFile = substring1 + "_encrypted" + substring2;
            else
                outFile = substring3 + "_encrypted" + substring2;
        else if (substring.equals("_encrypted"))
            outFile = substring1 + "_decrypted" + substring2;
        else
            outFile = substring3 + "_decrypted" + substring2;
        FileManaging.FileOp.writeFile(outFile, encryption);
        return outFile;
    }*/

    public FileEncryptor(EncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    /*public String encryptFile(String fileIn,String keyPath) {
        this.fileName = fileIn;
        String data = FileManaging.FileOp.readFile(fileIn);
        return writeEncryption(encryptionAlgorithm.encryptFile(data, keyPath,-1), Act.encrypt);
    }*/

    public void encryptFile(String fileIn, String fileOut, String keyPath) {
        this.fileName = fileIn;
        String data = FileOp.readFile(fileIn);

        FileOp.writeFile(fileOut, encryptionAlgorithm.encryptFile(data, key));
        FileOp.writeFile(keyPath, key.toString());
    }

    /*public String decryptFile(String fileIn, String keyPath) {
        this.fileName = fileIn;
        String data = FileManaging.FileOp.readFile(fileIn);
        String keys = FileManaging.FileOp.readFile(keyPath);
        return writeEncryption(encryptionAlgorithm.decryptFile(data, keys), Act.decrypt);
    }*/

    public void decryptFile(String fileIn, String fileOut, String keyPath) {
        BasicEncryption base = (BasicEncryption) encryptionAlgorithm.getBase();
        this.fileName = fileIn;
        String data = FileOp.readFile(fileIn);
        String keys = FileOp.readFile(keyPath);
        FileOp.writeFile(fileOut, base.decryptFile(data, keys));
    }

    public static String encryptDecrypt(String data, int key, BaseEnc baseEnc) {
        StringBuilder encryption = new StringBuilder(data);
        for (int index = 0; index < data.length(); index++) {
            int ch;
            if (baseEnc == BaseEnc.shiftUp)
                ch = data.charAt(index) + key;
            else
                ch = data.charAt(index) * key;
            if(ch<0)
                ch+=128;
            ch %= 128;
            encryption.setCharAt(index, (char) ch);
        }
        return encryption.toString();
    }
}
