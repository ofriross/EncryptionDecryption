package EncriptionsTests;

import basicEncryptions.ShiftUpEncryption;
import enums.EActionEncryptOrDecrypt;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShiftUpEncryptionTest {

    @Test
    public void computeCharEncrypt() {
        int actualResult = (new ShiftUpEncryption()).computeChar(40, 3, EActionEncryptOrDecrypt.encrypt);
        assertEquals(43, actualResult);
    }

    @Test
    public void computeCharDecrypt() {
        int actualResult = (new ShiftUpEncryption()).computeChar(40, 3, EActionEncryptOrDecrypt.decrypt);
        assertEquals(37, actualResult);
    }
}
