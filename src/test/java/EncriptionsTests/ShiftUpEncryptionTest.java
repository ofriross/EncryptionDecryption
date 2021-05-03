package EncriptionsTests;

import General.Constants;
import basicEncryptions.ShiftUpEncryption;
import enums.EActionEncryptOrDecrypt;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ShiftUpEncryptionTest {
    @Test
    public void encryptFileBasicCase() {
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> expectedEncryption = new ArrayList<>();
        data.add("abc");
        expectedEncryption.add("def");
        BasicEncryptionFunctions.testerEncryptFile(3, data, expectedEncryption, new ShiftUpEncryption());
    }

    @Test
    public void encryptFileRoundTrip() {
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> expectedEncryption = new ArrayList<>();
        data.add("bcd");
        expectedEncryption.add("abc");
        BasicEncryptionFunctions.testerEncryptFile(Constants.MAX_ASCII_VALUE, data, expectedEncryption, new ShiftUpEncryption());
    }

    @Test
    public void computeCharDecryptBasicCase() {
        int actualResult = (new ShiftUpEncryption()).computeChar(40, 3, EActionEncryptOrDecrypt.decrypt);
        assertEquals(37, actualResult);
    }

    @Test
    public void computeCharEncryptBasicCase() {
        int actualResult = (new ShiftUpEncryption()).computeChar(40, 3, EActionEncryptOrDecrypt.encrypt);
        assertEquals(43, actualResult);
    }
}
