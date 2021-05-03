package EncriptionsTests;

import Exceptions.ProblematicCharInEncryption;
import basicEncryptions.ShiftMultiplyEncryption;
import enums.EActionEncryptOrDecrypt;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ShiftMultiplyEncryptionTest {
    @Test
    public void encryptFileBasicCase() {

        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> expectedEncryption = new ArrayList<>();
        data.add("abc");
        expectedEncryption.add("abc");
        BasicEncryptionFunctions.testerEncryptFile(1, data, expectedEncryption, new ShiftMultiplyEncryption());
    }

    @Test
    public void encryptFileRoundTrip() {
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> expectedEncryption = new ArrayList<>();
        data.add("abc");
        expectedEncryption.add("#&)");
        BasicEncryptionFunctions.testerEncryptFile(3, data, expectedEncryption, new ShiftMultiplyEncryption());
    }

    @Test
    public void computeCharEncryptBasicCase() {
        int actualResult = 0;
        try {
            actualResult = (new ShiftMultiplyEncryption()).computeChar(40, 3, EActionEncryptOrDecrypt.encrypt);
        } catch (ProblematicCharInEncryption problematicCharInEncryption) {
            problematicCharInEncryption.printStackTrace();
        }
        int expectedResult = 40 * 3;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void computeCharRoundUpKey() {
        int actualResult = 0;
        try {
            actualResult = (new ShiftMultiplyEncryption()).computeChar(40, 2, EActionEncryptOrDecrypt.encrypt);
        } catch (ProblematicCharInEncryption problematicCharInEncryption) {
            problematicCharInEncryption.printStackTrace();
        }
        int expectedResult = 40 * (2 + 1);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void computeCharDecryptBasicCase() {
        int actualResult = 0;
        try {
            actualResult = (new ShiftMultiplyEncryption()).computeChar(120, 3, EActionEncryptOrDecrypt.decrypt);
        } catch (ProblematicCharInEncryption problematicCharInEncryption) {
            problematicCharInEncryption.printStackTrace();
        }
        assertEquals(40, actualResult);
    }

    @Test
    public void computeCharDecryptRoundTrip() {
        int actualResult = 0;
        try {
            actualResult = (new ShiftMultiplyEncryption()).computeChar(184, 10, EActionEncryptOrDecrypt.decrypt);
        } catch (ProblematicCharInEncryption problematicCharInEncryption) {
            problematicCharInEncryption.printStackTrace();
        }
        assertEquals(40, actualResult);
    }
}