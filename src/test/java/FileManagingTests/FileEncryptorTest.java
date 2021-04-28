package FileManagingTests;

import FileManaging.FileEncryptor;
import FileManaging.FileNameAndContent;
import FileManaging.FileOperations;
import basicEncryptions.IBasicEncryption;
import basicEncryptions.ShiftUpEncryption;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EAction;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileEncryptorTest {

    @Test
    public void encryptDecrypt() {
        IBasicEncryption basicEncryptionMock = mock(IBasicEncryption.class);
        int randomKey = 3;
        when(basicEncryptionMock.computeChar('a', randomKey, EAction.encrypt)).thenReturn((int) 'b');
        when(basicEncryptionMock.computeChar('b', randomKey, EAction.encrypt)).thenReturn((int) 'c');

        String actualResult = FileEncryptor.encryptDecrypt("ab", randomKey, basicEncryptionMock, EAction.encrypt);

        assertEquals("bc", actualResult);
    }

    @Test
    public void encryptAndDecryptIntegrationBasicShiftUp() {
        BasicConfigurator.configure();
        String filesLocations = "testFiles\\encryptDecrypt";
        String fileIn = filesLocations + "testEncryptDecrypt.txt";
        try {
            FileOperations.writeFile(fileIn, "Tested Data");
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }
        ShiftUpEncryption encryptionSU = new ShiftUpEncryption();
        FileEncryptor fileEncryptor = new FileEncryptor(encryptionSU);

        fileEncryptor.encryptFolder(filesLocations);
        fileEncryptor.decryptFolder(filesLocations);

        String originalData = "";
        String actualData = "";
        try {
            originalData = FileOperations.readFile(fileIn);
            actualData = FileOperations.readFile(filesLocations + "\\decrypted\\testEncryptDecrypt.txt");
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }

        assertEquals(originalData, actualData);
    }

    @Test
    public void decryptFolder() {
        BasicConfigurator.configure();
        IEncryptionAlgorithm encryptionAlgorithmMock = mock(IEncryptionAlgorithm.class);

        String mainDirectory = "decryptTest";
        String directoryLocationEncrypted;
        try {
            FileOperations.createDirectory("", mainDirectory);
            directoryLocationEncrypted = FileOperations.createDirectory(mainDirectory, "encrypted");
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }

        MockedStatic<FileOperations> fileOperationsMockedStatic = Mockito.mockStatic(FileOperations.class);

        ArrayList<FileNameAndContent> fileNameAndContents = new ArrayList<>();
        ArrayList<FileNameAndContent> decryptionNameAndContents = new ArrayList<>();

        fileNameAndContents.add(new FileNameAndContent("file1.txt", "before1"));
        decryptionNameAndContents.add(new FileNameAndContent("file1.txt", "after1"));
        fileNameAndContents.add(new FileNameAndContent("file2.txt", "before2"));
        decryptionNameAndContents.add(new FileNameAndContent("file2.txt", "after2"));
        fileNameAndContents.add(new FileNameAndContent("file3.txt", "before3"));
        decryptionNameAndContents.add(new FileNameAndContent("file3.txt", "after3"));

        fileOperationsMockedStatic.when(() -> FileOperations.readDirectory(directoryLocationEncrypted)).thenReturn(fileNameAndContents);
        fileOperationsMockedStatic.when(() -> FileOperations.readFile(directoryLocationEncrypted + "\\key.txt")).thenReturn("1,2,3");
        fileOperationsMockedStatic.when(() -> FileOperations.createDirectory(any(), any())).thenCallRealMethod();
        fileOperationsMockedStatic.when(() -> FileOperations.writeMultipleFilesToDirectory(any(), any())).thenCallRealMethod();
        fileOperationsMockedStatic.when(() -> FileOperations.writeFile(any(), any())).thenCallRealMethod();
        fileOperationsMockedStatic.when(() -> FileOperations.readDirectory(mainDirectory + "\\decrypted")).thenCallRealMethod();
        fileOperationsMockedStatic.when(() -> FileOperations.readFile(mainDirectory + "\\decrypted\\file1.txt")).thenCallRealMethod();
        fileOperationsMockedStatic.when(() -> FileOperations.readFile(mainDirectory + "\\decrypted\\file2.txt")).thenCallRealMethod();
        fileOperationsMockedStatic.when(() -> FileOperations.readFile(mainDirectory + "\\decrypted\\file3.txt")).thenCallRealMethod();
        //fileOperationsMockedStatic.when(() -> FileOperations.writeFile(directoryLocationEncrypted + "\\key.txt",any())).

        ArrayList<Integer> keys = new ArrayList<>();
        keys.add(1);
        keys.add(2);
        keys.add(3);

        when(encryptionAlgorithmMock.decryptFolder(fileNameAndContents, keys)).thenReturn(decryptionNameAndContents);

        FileEncryptor fileEncryptor = new FileEncryptor(encryptionAlgorithmMock);
        fileEncryptor.decryptFolder(mainDirectory);

        ArrayList<FileNameAndContent> actualDecryption;
        try {
            actualDecryption = FileOperations.readDirectory(mainDirectory + "\\decrypted");
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }

        assertEquals(decryptionNameAndContents, actualDecryption);

        fileOperationsMockedStatic.close();
    }

    @Test
    public void decryptFileIllegalKeysTwoCommasInARow() {
        testerDecryptFileIllegalKeysFormat("1,,2");
    }

    @Test
    public void decryptFileIllegalKeysEndWithComma() {
        testerDecryptFileIllegalKeysFormat("1,3,");
    }

    @Test
    public void decryptFileIllegalKeysStartWithComma() {
        testerDecryptFileIllegalKeysFormat(",1,3");
    }

    @Test
    public void decryptFileIllegalKeysWithSpaces() {
        testerDecryptFileIllegalKeysFormat("1 ,3");
    }

    @Test
    public void decryptFileIllegalKeysFormatLetters() {
        testerDecryptFileIllegalKeysFormat("1,f");
    }

    @Test
    public void decryptFileTooLargeKey() {
        testerDecryptFileIllegalKeysFormat("3,1111,2");
    }

    public void testerDecryptFileIllegalKeysFormat(String keys) {
        IEncryptionAlgorithm encryptionAlgorithmMock = mock(IEncryptionAlgorithm.class);

        MockedStatic<FileOperations> fileOperationsMockedStatic = Mockito.mockStatic(FileOperations.class);
        fileOperationsMockedStatic.when(() -> FileOperations.readFile("directory\\encrypted\\key.txt")).thenReturn(keys);

        FileEncryptor fileEncryptor = new FileEncryptor(encryptionAlgorithmMock);
        fileEncryptor.decryptFolder("directory");

        //make sure there was an error and there for the decryptFile function in of fileEncryptor didn't
        // reach the part where it calls to decryptFile of its variable (encryptionAlgorithmMock)
        Mockito.verify(encryptionAlgorithmMock, Mockito.times(0)).decryptFolder(any(), any());

        fileOperationsMockedStatic.close();
    }
}
