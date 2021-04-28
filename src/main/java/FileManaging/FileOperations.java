package FileManaging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileOperations {
    public static String readFile(String fileName) throws FileNotFoundException {
        String data = new String();
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("The file '" + fileName + "' wasn't found");
        }
        return data;
    }

    public static void writeFile(String fileName, String data) throws IOException {
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(data);
            myWriter.close();
        } catch (IOException e) {
            throw new IOException("The file '" + fileName + "' wasn't found");
        }
    }
}
