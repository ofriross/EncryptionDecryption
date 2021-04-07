import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ShiftUpEncryptionTest {
    @Test
    public void encryptFileTest() {
        Key keyMock = Mockito.mock(Key.class);
        keyMock.makeKey(Key.KeyType.single);

        testerEncryptFile(keyMock, 127, "bcd","abc");
        testerEncryptFile(keyMock, 3, "abc","def");
    }
    private void testerEncryptFile(Key keyMock, int keyValue, String data, String expected) {
        when(keyMock.getValue()).thenReturn(keyValue);

        ShiftUpEncryption shiftUpEncryptionMock = new ShiftUpEncryption();
        String encSU = shiftUpEncryptionMock.encryptFile(data, keyMock);
        assertEquals(expected, encSU);
    }

    @Test
    public void decryptFileTest(){
        testerDecryptFile("def","abc","1,2");
        testerDecryptFile("abc","bcd","127");
    }
    private void testerDecryptFile(String data,String expected, String keys){
        ShiftUpEncryption shiftUpEncryptionMock = new ShiftUpEncryption();
        String encSU2 = shiftUpEncryptionMock.decryptFile(data, keys);
        assertEquals(expected, encSU2);

    }
}
