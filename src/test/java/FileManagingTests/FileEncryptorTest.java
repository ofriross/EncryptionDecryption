package FileManagingTests;

import FileManaging.FileEncryptor;
import FileManaging.FileOperations;
import General.Constants;
import basicEncryptions.ShiftUpEncryption;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class FileEncryptorTest {

    @Test
    public void encryptAndDecryptIntegrationBasicShiftUp() {
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

        //TODO: add this back //fileEncryptor.encryptFolder(filesLocations);
        //TODO: add this back //fileEncryptor.decryptFolder(filesLocations);

        String originalData = "";
        String actualData = "";
        try {
            originalData = FileOperations.readFile(fileIn);
            actualData = FileOperations.readFile(Path.of(filesLocations, Constants.DECRYPT_FOLDER_NAME, "testEncryptDecrypt.txt").toString());
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }

        assertEquals(originalData, actualData);
    }

    //TODO: add this back //
    /*@Test
    public void decryptFolder() {
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

        when(encryptionAlgorithmMock.decryptFile(fileNameAndContents, keys)).thenReturn(decryptionNameAndContents);

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
    }*/

    /*@Test
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
    }*/
}
