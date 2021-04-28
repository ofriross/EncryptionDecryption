package EncriptionsTests;

import FileManaging.FileNameAndContent;
import Keys.DoubleKey;
import Keys.Key;
import complexEncryptions.DoubleEncryption;
import complexEncryptions.IEncryptionAlgorithm;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DoubleEncryptionTest {
    @Test
    public void encryptFile() {
        DoubleKey keyDoubleMock = Mockito.mock(DoubleKey.class);
        Key key1 = mock(Key.class);
        Key key2 = mock(Key.class);
        IEncryptionAlgorithm encryptionAlgorithmMock = Mockito.mock(IEncryptionAlgorithm.class);
        DoubleEncryption doubleEncryptionSUMock = new DoubleEncryption(encryptionAlgorithmMock);

        when(keyDoubleMock.getDouble1()).thenReturn(key1);
        when(keyDoubleMock.getDouble2()).thenReturn(key2);

        ArrayList<FileNameAndContent> data = new ArrayList<>();
        ArrayList<FileNameAndContent> afterFirstEncryption = new ArrayList<>();
        ArrayList<FileNameAndContent> expectedEncryption = new ArrayList<>();
        data.add(new FileNameAndContent("file", "first"));
        afterFirstEncryption.add(new FileNameAndContent("file", "second"));
        expectedEncryption.add(new FileNameAndContent("file", "third"));
        when(encryptionAlgorithmMock.encryptFolder(data, key1)).thenReturn(afterFirstEncryption);
        when(encryptionAlgorithmMock.encryptFolder(afterFirstEncryption, key2)).thenReturn(expectedEncryption);

        ArrayList<FileNameAndContent> actualEncryption = doubleEncryptionSUMock.encryptFolder(data, keyDoubleMock);

        assertEquals(expectedEncryption, actualEncryption);
    }
}
