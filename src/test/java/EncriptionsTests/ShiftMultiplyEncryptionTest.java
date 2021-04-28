package EncriptionsTests;

import EncriptionsTests.BasicEncryptionFunctions;
import FileManaging.FileNameAndContent;
import General.Consts;
import basicEncryptions.ShiftMultiplyEncryption;
import enums.EAction;
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
        int actualResult = (new ShiftMultiplyEncryption()).computeChar(40, 3, EAction.encrypt);
        int expectedResult = 40 * 3;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void computeCharRoundUpKey() {
        int actualResult = (new ShiftMultiplyEncryption()).computeChar(40, 2, EAction.encrypt);
        int expectedResult = 40 * (2 + 1);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void computeCharDecryptBasicCase() {
        int actualResult = (new ShiftMultiplyEncryption()).computeChar(120, 3, EAction.decrypt);
        assertEquals(40, actualResult);
    }

    @Test
    public void computeCharDecryptRoundTrip() {
        int actualResult = (new ShiftMultiplyEncryption()).computeChar(184, 10, EAction.decrypt);
        assertEquals(40, actualResult);
    }
}