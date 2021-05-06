package EncriptionsTests;

import Keys.DoubleKey;
import complexEncryptions.DoubleEncryption;
import complexEncryptions.IEncryptionAlgorithm;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class DoubleEncryptionTest {
    @Test
    public void performEncryption() {
        IEncryptionAlgorithm encryptionAlgorithmMock = Mockito.mock(IEncryptionAlgorithm.class);
        DoubleKey doubleKey = (DoubleKey) (new DoubleEncryption(encryptionAlgorithmMock)).initKey();

        when(encryptionAlgorithmMock.performEncryption("dataBeforeEncryption", doubleKey.getDouble1())).thenReturn("dataAfterFirstEncryption");
        when(encryptionAlgorithmMock.performEncryption("dataAfterFirstEncryption", doubleKey.getDouble1())).thenReturn("dataAfterBothEncryption");

        DoubleEncryption doubleEncryption = new DoubleEncryption(encryptionAlgorithmMock);
        String actualResult = doubleEncryption.performEncryption("dataBeforeEncryption", doubleKey);

        assertEquals("dataAfterBothEncryption", actualResult);
    }

    @Test
    public void performDecryption() {
        IEncryptionAlgorithm encryptionAlgorithmMock = Mockito.mock(IEncryptionAlgorithm.class);
        DoubleKey doubleKey = (DoubleKey) (new DoubleEncryption(encryptionAlgorithmMock)).initKey();
        ArrayList<Integer> keys = new ArrayList<>();
        //DoubleEncryption doubleEncryptionMock = new DoubleEncryption(encryptionAlgorithmMock);

        when(encryptionAlgorithmMock.performDecryption("dataBeforeDecryption", keys)).thenReturn("dataAfterDecryption");

        DoubleEncryption doubleEncryption = new DoubleEncryption(encryptionAlgorithmMock);
        String actualResult = doubleEncryption.performDecryption("dataBeforeDecryption", keys);

        assertEquals("dataAfterDecryption", actualResult);
    }
}
