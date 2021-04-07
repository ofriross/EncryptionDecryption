import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class DoubleEncryptionTest {
    @Test
    public void encryptFileTest() {
        Key keyDMock = Mockito.mock(Key.class);
        Key keyMock1 = Mockito.mock(Key.class);
        Key keyMock2 = Mockito.mock(Key.class);

        tester(keyDMock, keyMock1, keyMock2, 1, 2,"abc","def");
        tester(keyDMock, keyMock1, keyMock2, 2, 3,"abc","fgh");
    }

    private void tester(Key keyDMock, Key keyMock1, Key keyMock2, int val1, int val2, String data, String expected) {
        when(keyMock1.getValue()).thenReturn(val1);
        when(keyMock2.getValue()).thenReturn(val2);
        keyDMock.makeKey(Key.KeyType.doubled);

        when(keyDMock.getDouble1()).thenReturn(keyMock1);
        when(keyDMock.getDouble2()).thenReturn(keyMock2);

        ShiftUpEncryption shiftUpEncryptionMock = new ShiftUpEncryption();
        DoubleEncryption doubleEncryptionSUMock = new DoubleEncryption(shiftUpEncryptionMock);

        String encDSU1 = doubleEncryptionSUMock.encryptFile(data, keyDMock);
        assertEquals(expected, encDSU1);
    }
}
