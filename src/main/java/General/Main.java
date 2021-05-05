package General;

import FileManaging.FileEncryptor;
import MultiThreading.ASyncDirectoryProcessor;
import basicEncryptions.ShiftMultiplyEncryption;
import basicEncryptions.ShiftUpEncryption;
import basicEncryptions.XorEncryption;
import complexEncryptions.DoubleEncryption;
import complexEncryptions.RepeatEncryption;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        //FileOperations.writeFile("debuggingFiles\\tst.txt","a");
        /*int ch=256;
        int lastChar=0;
        for (int i = 0; i < 256; i++) {
            ch = i;
            FileOperations.writeFile("debuggingFiles\\tst.txt", String.valueOf((char)ch));
            String cha = FileOperations.readFile("debuggingFiles\\tst.txt");
            int chAfter=cha.charAt(0);
            if (chAfter!=i)
                System.out.println("heyyy '" + chAfter + "'");
        }
        System.out.println("last ch="+ch);
        if (ch == 255)
            return;*/
        BasicConfigurator.configure();
        String file = "C:\\enc\\test.txt";
        String directory = "debuggingFiles";
        String keyPath = "C:\\enc\\key.txt";
        String keyPathM = "C:\\enc\\keyM.txt";
        String keyPathX = "C:\\enc\\keyX.txt";
        String keyPathXD = "C:\\enc\\keyXD.txt";
        String keyPathR = "C:\\enc\\keyR.txt";
        String keyPathD = "C:\\enc\\keyD.txt";
        String keyPathDD = "C:\\enc\\keyDD.txt";
        String keyPath3D = "C:\\enc\\key3D.txt";
        ShiftUpEncryption encSU = new ShiftUpEncryption();
        ShiftMultiplyEncryption encSM = new ShiftMultiplyEncryption();
        XorEncryption encXor = new XorEncryption();
        DoubleEncryption encXorD = new DoubleEncryption(encXor);
        RepeatEncryption encSUR = new RepeatEncryption(encSU, 8);
        DoubleEncryption encSUD = new DoubleEncryption(encSU);
        DoubleEncryption encSUDD = new DoubleEncryption(encSUD);
        RepeatEncryption encSU3D = new RepeatEncryption(encSUD, 3);
        String enc = "C:\\enc\\test_encrypted_su.txt";
        String encm = "C:\\enc\\test_encrypted_sm.txt";
        String encx = "C:\\enc\\test_encrypted_xor.txt";
        String encxd = "C:\\enc\\test_encrypted_xord.txt";
        String encR = "C:\\enc\\test_encrypted_suR.txt";
        String encd = "C:\\enc\\test_encrypted_sud.txt";
        String encdd = "C:\\enc\\test_encrypted_sudd.txt";
        String enc3d = "C:\\enc\\test_encrypted_su3d.txt";
        FileEncryptor fe = new FileEncryptor(encSU);
        FileEncryptor fem = new FileEncryptor(encSM);
        FileEncryptor fex = new FileEncryptor(encXor);
        FileEncryptor fexd = new FileEncryptor(encXorD);
        FileEncryptor feR = new FileEncryptor(encSUR);
        FileEncryptor fed = new FileEncryptor(encSUD);
        FileEncryptor fedd = new FileEncryptor(encSUDD);
        FileEncryptor fe3d = new FileEncryptor(encSU3D);
        //if (num == 1) {
        //fe.encryptFile(directory+"\\tested1.txt",directory+"\\tested1_encrypted.txt",directory+"\\key.txt");
        //fem.encryptFile(directory+"\\tested1.txt",directory+"\\tested1_encrypted.txt",directory+"\\key.txt");
        //fex.encryptFile(directory+"\\tested1.txt",directory+"\\tested1_encrypted.txt",directory+"\\key.txt");
        //feR.encryptFolder(directory);
        //fexd.encryptFolder(directory);
        //fed.encryptFolder(directory);
        //fedd.encryptFolder(directory);
        //fe3d.encryptFile(directory+"\\tested1.txt",directory+"\\tested1_encrypted.txt",directory+"\\key.txt");
        //fe.encryptFolder("debuggingFiles");
        System.out.print("the files got encrypted.\n");

            /*System.out.println("encSU="+encSU.getKeyStrength());
            System.out.println("encSM="+encSM.getKeyStrength());
            System.out.println("encXor="+encXor.getKeyStrength());
            System.out.println("encXorD="+encXorD.getKeyStrength());
            System.out.println("encSU5="+encSU5.getKeyStrength());
            System.out.println("encSUDD="+encSUDD.getKeyStrength());
            System.out.println("encSU3D="+encSU3D.getKeyStrength());

            ArrayList<IEncryptionAlgorithm> ar = new ArrayList<IEncryptionAlgorithm>();
            ar.add(encSU3D);
            ar.add(encSM);
            System.out.println("\nUnsorted");
            for (int i = 0; i < ar.size(); i++)
                System.out.println(ar.get(i));

            Collections.sort(ar, new SortByKeyStrength());

            System.out.println("\nSorted by keyStrength");
            for (int i = 0; i < ar.size(); i++)
                System.out.println(ar.get(i));*/
        //}
        //if (num == 2) {
        //fe.decryptFile(directory+"\\tested1_encrypted.txt",directory+"\\tested1_decrypted.txt",directory+"\\key.txt");
        //fem.decryptFile(directory+"\\tested1_encrypted.txt",directory+"\\tested1_decrypted.txt",directory+"\\key.txt");
        //fex.decryptFile(directory+"\\tested1_encrypted.txt",directory+"\\tested1_decrypted.txt",directory+"\\key.txt");
        //fexd.decryptFolder(directory);
        //feR.decryptFolder(directory);
        //fed.decryptFolder(directory);
        //fedd.decryptFolder(directory);
        //fe3d.decryptFile(directory+"\\tested1_encrypted.txt",directory+"\\tested1_decrypted.txt",directory+"\\key.txt");
        //fe.decryptFolder("debuggingFiles");
        System.out.print("the files got decrypted.\n");
        //}


        /*ArrayList<FileNameAndContent> allFilesNameAndData;
        try {
            allFilesNameAndData = FileOperations.readDirectory("C:\\enc");
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }
        String directoryLocation = FileOperations.createDirectory("C:\\enc", "encrypted");
        FileOperations.writeMultipleFilesToDirectory(allFilesNameAndData,directoryLocation);*/

//        SyncDirectoryProcessor syncDirectoryProcessor = new SyncDirectoryProcessor("debuggingFiles", encSU);
//        syncDirectoryProcessor.encryptAndDecryptFolder();
        ASyncDirectoryProcessor aSyncDirectoryProcessor = new ASyncDirectoryProcessor(10, "debuggingFiles", encSU);
        aSyncDirectoryProcessor.encryptAndDecryptFolder();
    }
}
