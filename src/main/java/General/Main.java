package General;

import FileManaging.FileEncryptor;
import MultiThreading.ASyncDirectoryProcessor;
import MultiThreading.EncryptionDecryptionThread;
import MultiThreading.SyncDirectoryProcessor;
import basicEncryptions.ShiftMultiplyEncryption;
import basicEncryptions.ShiftUpEncryption;
import basicEncryptions.XorEncryption;
import complexEncryptions.DoubleEncryption;
import complexEncryptions.RepeatEncryption;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        ASyncDirectoryProcessor.encryptAndDecryptFolder(10, "debuggingFiles", encSU3D);
        System.out.println();
        SyncDirectoryProcessor.encryptAndDecryptFolder( "debuggingFiles", encSU);
    }
}
