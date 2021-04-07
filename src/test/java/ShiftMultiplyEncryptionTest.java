import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ShiftMultiplyEncryptionTest {
    @Test
    public void encryptFileTest() {
        Key keyMock = Mockito.mock(Key.class);
        keyMock.makeKey(Key.KeyType.single);

        testerEncryptFile(keyMock, 1, "abc","abc");
        testerEncryptFile(keyMock, 3, "abc","#&)");
    }
    private void testerEncryptFile(Key keyMock, int keyValue, String data, String expected) {
        when(keyMock.getValue()).thenReturn(keyValue);

        ShiftMultiplyEncryption shiftMultiplyEncryptionMock = new ShiftMultiplyEncryption();
        String encM = shiftMultiplyEncryptionMock.encryptFile(data, keyMock);
        assertEquals(expected, encM);
    }

    @Test
    public void decryptFileTest(){
        testerDecryptFile("1,1,1","abc","abc");
        testerDecryptFile("1,3,1","#&)","abc");
    }
    private void testerDecryptFile(String keyValue, String data, String expected) {
        ShiftMultiplyEncryption shiftMultiplyEncryptionMock = new ShiftMultiplyEncryption();
        String encSU = shiftMultiplyEncryptionMock.decryptFile(data, keyValue);
        assertEquals(expected, encSU);
    }
}