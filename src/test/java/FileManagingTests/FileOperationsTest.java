package FileManagingTests;

import FileManaging.FileOperations;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class FileOperationsTest {
    @Test(expected = FileNotFoundException.class)
    public void readFile() throws IOException {
        FileOperations.readFile("NotARealFileLocationToReadFrom");
    }

    @Test(expected = IOException.class)
    public void writeFile() throws IOException {
        FileOperations.writeFile("NotARealFileLocationToWriteInto:\\notARealFolder\\NotARealTextFile.txt", "data");
    }
}
