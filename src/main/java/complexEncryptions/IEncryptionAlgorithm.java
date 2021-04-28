package complexEncryptions;

import FileManaging.FileNameAndContent;
import Keys.Key;

import java.util.ArrayList;

public interface IEncryptionAlgorithm {
    <T extends Key> ArrayList<FileNameAndContent> encryptFolder(ArrayList<FileNameAndContent> data, T key);

    ArrayList<FileNameAndContent> decryptFolder(ArrayList<FileNameAndContent> data, ArrayList<Integer> keys);

    int getKeyStrength();

    String getType();

    Key initKey();
}