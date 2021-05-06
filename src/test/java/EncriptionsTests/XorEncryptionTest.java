package EncriptionsTests;

import basicEncryptions.ShiftMultiplyEncryption;
import basicEncryptions.ShiftUpEncryption;
import basicEncryptions.XorEncryption;
import enums.EActionEncryptOrDecrypt;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class XorEncryptionTest {
    @Test
    public void computeCharEncrypt() {
        int actualResult = (new XorEncryption()).computeChar(97, 13, EActionEncryptOrDecrypt.encrypt);
        assertEquals(108, actualResult);
    }

    @Test
    public void computeCharEnDecrypt() {
        int actualResult = (new XorEncryption()).computeChar(108, 13, EActionEncryptOrDecrypt.encrypt);
        assertEquals(97, actualResult);
    }
}
