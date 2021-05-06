package EncriptionsTests;

import Keys.RepeatKey;
import complexEncryptions.IEncryptionAlgorithm;
import complexEncryptions.RepeatEncryption;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class RepeatEncryptionTest {
    @Test
    public void performEncryption() {
        IEncryptionAlgorithm encryptionAlgorithmMock = Mockito.mock(IEncryptionAlgorithm.class);
        RepeatKey repeatKey = (RepeatKey) (new RepeatEncryption(encryptionAlgorithmMock, 4)).initKey();

        when(encryptionAlgorithmMock.performEncryption("dataAfter0Encryptions", repeatKey.getRepeatedKey())).thenReturn("dataAfter1Encryptions");
        when(encryptionAlgorithmMock.performEncryption("dataAfter1Encryptions", repeatKey.getRepeatedKey())).thenReturn("dataAfter2Encryptions");
        when(encryptionAlgorithmMock.performEncryption("dataAfter2Encryptions", repeatKey.getRepeatedKey())).thenReturn("dataAfter3Encryptions");
        when(encryptionAlgorithmMock.performEncryption("dataAfter3Encryptions", repeatKey.getRepeatedKey())).thenReturn("dataAfter4Encryptions");
        RepeatEncryption repeatEncryption = new RepeatEncryption(encryptionAlgorithmMock, 4);
        String actualResult = repeatEncryption.performEncryption("dataAfter0Encryptions", repeatKey);

        assertEquals("dataAfter4Encryptions", actualResult);
    }

    @Test
    public void performDecryption() {
        IEncryptionAlgorithm encryptionAlgorithmMock = Mockito.mock(IEncryptionAlgorithm.class);
        ArrayList<Integer> keys = new ArrayList<>();
        //DoubleEncryption doubleEncryptionMock = new DoubleEncryption(encryptionAlgorithmMock);

        when(encryptionAlgorithmMock.performDecryption("dataBeforeDecryption", keys)).thenReturn("dataAfterDecryption");

        RepeatEncryption repeatEncryption = new RepeatEncryption(encryptionAlgorithmMock, 111);
        String actualResult = repeatEncryption.performDecryption("dataBeforeDecryption", keys);

        assertEquals("dataAfterDecryption", actualResult);
    }
}
