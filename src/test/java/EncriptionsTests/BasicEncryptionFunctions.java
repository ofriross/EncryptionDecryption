package EncriptionsTests;

import FileManaging.FileNameAndContent;
import Keys.SingleKey;
import basicEncryptions.IBasicEncryption;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class BasicEncryptionFunctions {
    public static void testerEncryptFile(int keyValue, ArrayList<String> data, ArrayList<String> expectedData, IBasicEncryption IBasicEncryption) {
        SingleKey keyMock = Mockito.mock(SingleKey.class);
        when(keyMock.getValue()).thenReturn(keyValue);

        ArrayList<FileNameAndContent> fileNameAndContents = new ArrayList<>();
        ArrayList<FileNameAndContent> expectedEncryption = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            fileNameAndContents.add(new FileNameAndContent("fileMock", data.get(i)));
            expectedEncryption.add(new FileNameAndContent("fileMock", expectedData.get(i)));
        }
        //TODO add this back //ArrayList<FileNameAndContent> actualEncryption = IBasicEncryption.encryptFile(fileNameAndContents, keyMock);

        //TODO add this back //assertEquals(expectedEncryption, actualEncryption);
    }
}
