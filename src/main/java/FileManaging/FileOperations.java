package FileManaging;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileOperations {
    public static String readFile(String fileName) throws FileNotFoundException {
        StringBuilder data = new StringBuilder();
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNext()) {
                data.append(myReader.next());
                //data.append("\n");
            }
            data.deleteCharAt(data.length() - 1);
            myReader.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("The file '" + fileName + "' wasn't found");
        }
        System.out.println("DATA==================================="+data.toString());
        return data.toString();
    }

    /*public static String readFile(String inputFilePath) throws FileNotFoundException {
        Scanner s = null;
        String dataFromFile = "";
        try {
            s = new Scanner(new BufferedReader(new FileReader(inputFilePath)));
            while (s.hasNext()) {
                dataFromFile = s.next();
                char[] myChar = dataFromFile.toCharArray();
                // do something
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
        return
    }*/

    public static void writeFile(String fileName, String data) throws IOException {
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(data);
            myWriter.close();
        } catch (IOException e) {
            throw new IOException("The file '" + fileName + "' wasn't found");
        }
    }

    public static String createFolder(String directoryLocation, String directoryName) {
        String folderName = directoryLocation + "\\" + directoryName;
        File fileToCreate = new File(folderName);
        if (!fileToCreate.exists()) {
            fileToCreate.mkdirs();
        }
        return folderName;
    }

    public static ArrayList<String> getFileNamesFromFolder(String directory) throws IOException {
        ArrayList<String> folderContent = new ArrayList<>();
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null)
            throw new IOException("The folder '" + directory + "' is empty or does not exist");
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                if (!file.getName().equals("key.txt"))
                    folderContent.add(file.getName());
            }
        }
        return folderContent;
    }
}
