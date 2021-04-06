import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*String test = "abc.txt";
        test = test.substring(0, test.length() - 4) + "_encrypted"+ test.substring(test.length() - 4);//+ "encrypted" + test.substring(test.length() - 4)
        StringBuilder string = new StringBuilder(test);
        string.setCharAt(0, (char) (test.charAt(0) + 2));
        System.out.println(test);*/

        //C:\enc\test.txt

        System.out.println("please press\n1 to encrypt a file\nor\n2 to decrypt a file");
        Scanner scan = new Scanner(System.in);
        int num = scan.nextInt();

        String newFile = "";
        String keyPath = "C:\\enc\\key.txt";
        String keyPathM = "C:\\enc\\keyM.txt";
        String keyPathX = "C:\\enc\\keyX.txt";
        String keyPathXD = "C:\\enc\\keyXD.txt";
        String keyPath5 = "C:\\enc\\key5.txt";
        String keyPathD = "C:\\enc\\keyD.txt";
        String keyPathDD = "C:\\enc\\keyDD.txt";
        String keyPath3D = "C:\\enc\\key3D.txt";
        ShiftUpEncryption encSU = new ShiftUpEncryption();
        ShiftMultiplyEncryption encSM = new ShiftMultiplyEncryption();
        XorEncryption encXor = new XorEncryption();
        DoubleEncryption encXorD = new DoubleEncryption(encXor);
        RepeatEncryption encSU5 = new RepeatEncryption(encSU, 5);
        DoubleEncryption encSUD = new DoubleEncryption(encSU);
        DoubleEncryption encSUDD = new DoubleEncryption(encSUD);
        RepeatEncryption encSU3D = new RepeatEncryption(encSUD, 3);
        String file = "C:\\enc\\test.txt";
        String enc = "C:\\enc\\test_encrypted_su.txt";
        String encm = "C:\\enc\\test_encrypted_sm.txt";
        String encx = "C:\\enc\\test_encrypted_xor.txt";
        String encxd = "C:\\enc\\test_encrypted_xord.txt";
        String enc5 = "C:\\enc\\test_encrypted_su5.txt";
        String encd = "C:\\enc\\test_encrypted_sud.txt";
        String encdd = "C:\\enc\\test_encrypted_sudd.txt";
        String enc3d = "C:\\enc\\test_encrypted_su3d.txt";
        FileEncryptor fe = new FileEncryptor(encSU);
        FileEncryptor fem = new FileEncryptor(encSM);
        FileEncryptor fex = new FileEncryptor(encXor);
        FileEncryptor fexd = new FileEncryptor(encXorD);
        FileEncryptor fe5 = new FileEncryptor(encSU5);
        FileEncryptor fed = new FileEncryptor(encSUD);
        FileEncryptor fedd = new FileEncryptor(encSUDD);
        FileEncryptor fe3d = new FileEncryptor(encSU3D);
        if (num == 1) {
            /*System.out.println("please enter a file location to encrypt");
            Scanner sc = new Scanner(System.in);
            String file = sc.nextLine();*/
            fe.encryptFile(file, enc, keyPath);
            fem.encryptFile(file, encm, keyPathM);
            fex.encryptFile(file, encx, keyPathX);
            fexd.encryptFile(file, encxd, keyPathXD);
            fe5.encryptFile(file, enc5, keyPath5);
            fed.encryptFile(file, encd, keyPathD);
            fedd.encryptFile(file, encdd, keyPathDD);
            fe3d.encryptFile(file, enc3d, keyPath3D);
            //newFile = fe.decryptFile(file, keyPath);
            System.out.print("the files got encrypted.");
        }
        if (num == 2) {
            /*System.out.println("please enter a file location to decrypt");
            Scanner sc = new Scanner(System.in);
            String file = sc.nextLine();*/
            /*System.out.println("please enter the key");
            int key = scan.nextInt();
            FileOp.writeFile(keyPath, String.valueOf(key));*/
            fe.decryptFile(enc, "C:\\enc\\test_decrypted_su.txt", keyPath);
            fem.decryptFile(encm, "C:\\enc\\test_decrypted_sm.txt", keyPathM);
            fex.decryptFile(encx, "C:\\enc\\test_decrypted_xor.txt", keyPathX);
            fexd.decryptFile(encxd, "C:\\enc\\test_decrypted_xord.txt", keyPathXD);
            fe5.decryptFile(enc5, "C:\\enc\\test_decrypted_su5.txt", keyPath5);
            fed.decryptFile(encd, "C:\\enc\\test_decrypted_sud.txt", keyPathD);
            fedd.decryptFile(encdd, "C:\\enc\\test_decrypted_sudd.txt", keyPathDD);
            fe3d.decryptFile(enc3d, "C:\\enc\\test_decrypted_su3d.txt", keyPath3D);
            //newFile = fe.decryptFile(file, keyPath);
            System.out.print("the files got decrypted.");
        }
        //System.out.println(" you can find it in " + newFile);
    }
}
