package EncriptionsTests;

import FileManaging.FileNameAndContent;
import Keys.RepeatKey;
import complexEncryptions.IEncryptionAlgorithm;
import complexEncryptions.RepeatEncryption;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class RepeatEncryptionTest {
    @Test
    public void encryptFileMultipleRepeat() {
        String[] data = {"first", "second", "third", "forth", "fifth"};
        testerEncryptFile(4, data);
    }

    @Test
    public void encryptFileSingleRepeat() {
        String[] data = {"hey", "ney"};
        testerEncryptFile(1, data);
    }

    private void testerEncryptFile(int repeatN, String[] data) {

        IEncryptionAlgorithm encryptionAlgorithmMock = Mockito.mock(IEncryptionAlgorithm.class);
        RepeatKey repeatKey = (RepeatKey) (new RepeatEncryption(encryptionAlgorithmMock, repeatN)).initKey();

        FileNameAndContent[] fileNameAndContents = new FileNameAndContent[repeatN+1];
        for (int i = 0; i < fileNameAndContents.length; i++)
            fileNameAndContents[i] = new FileNameAndContent("file", data[i]);

        ArrayList<FileNameAndContent> fileNameAndContents1 = new ArrayList<>();
        fileNameAndContents1.add(fileNameAndContents[0]);
        for (int i = 1; i <= repeatN; i++) {
            ArrayList<FileNameAndContent> fileNameAndContents2 = new ArrayList<>();
            fileNameAndContents2.add(fileNameAndContents[i]);
            when(encryptionAlgorithmMock.encryptFolder(fileNameAndContents1, repeatKey.getRepeatedKey())).thenReturn(fileNameAndContents2);
            fileNameAndContents1 = fileNameAndContents2;
        }

        RepeatEncryption repeatEncryptionMOCK = new RepeatEncryption(encryptionAlgorithmMock, repeatN);
        ArrayList<FileNameAndContent> fileNameAndContentsFirst = new ArrayList<>();
        fileNameAndContentsFirst.add(fileNameAndContents[0]);
        ArrayList<FileNameAndContent> expectedEncryption = new ArrayList<>();
        expectedEncryption.add(fileNameAndContents[repeatN]);

        ArrayList<FileNameAndContent> actualEncryption = repeatEncryptionMOCK.encryptFolder(fileNameAndContentsFirst, repeatKey);

        assertEquals(expectedEncryption, actualEncryption);
    }
}
