package EncriptionsTests;

import basicEncryptions.ShiftMultiplyEncryption;
import basicEncryptions.XorEncryption;
import org.junit.Test;

import java.util.ArrayList;

public class XorEncryptionTest {
    @Test
    public void encryptFileBasicCase() {
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> expectedEncryption = new ArrayList<>();
        data.add("bcd");
        expectedEncryption.add("cbe");
        BasicEncryptionFunctions.testerEncryptFile(1, data, expectedEncryption, new XorEncryption());
    }
}
