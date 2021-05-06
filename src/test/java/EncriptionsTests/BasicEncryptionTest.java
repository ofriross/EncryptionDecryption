package EncriptionsTests;

import Keys.DoubleKey;
import Keys.SingleKey;
import basicEncryptions.BasicEncryption;
import basicEncryptions.IBasicEncryption;
import complexEncryptions.DoubleEncryption;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EActionEncryptOrDecrypt;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BasicEncryptionTest {
    @Test
    public void performEncryption() {
        BasicEncryption basicEncryptionMock = Mockito.mock(BasicEncryption.class);
        SingleKey singleKey = Mockito.mock(SingleKey.class);

        int keyValue = 5;
        when(singleKey.getValue()).thenReturn(keyValue);
        when(basicEncryptionMock.computeChar('a', keyValue, EActionEncryptOrDecrypt.encrypt)).thenReturn(Integer.valueOf('d'));
        when(basicEncryptionMock.computeChar('b', keyValue, EActionEncryptOrDecrypt.encrypt)).thenReturn(Integer.valueOf('c'));
        when(basicEncryptionMock.computeChar('c', keyValue, EActionEncryptOrDecrypt.encrypt)).thenReturn(Integer.valueOf('b'));
        when(basicEncryptionMock.computeChar('d', keyValue, EActionEncryptOrDecrypt.encrypt)).thenReturn(Integer.valueOf('a'));
        when(basicEncryptionMock.performEncryption(any(), any())).thenCallRealMethod();

        String actualResult = basicEncryptionMock.performEncryption("abcd", singleKey);

        assertEquals("dcba", actualResult);
    }

    @Test
    public void performDecryption() {
        BasicEncryption basicEncryptionMock = Mockito.mock(BasicEncryption.class);
        ArrayList<Integer> keys=new ArrayList<>();
        keys.add(1);
        keys.add(2);
        keys.add(3);
        keys.add(4);

        when(basicEncryptionMock.computeChar('a', 1, EActionEncryptOrDecrypt.decrypt)).thenReturn(Integer.valueOf('b'));
        when(basicEncryptionMock.computeChar('b', 2, EActionEncryptOrDecrypt.decrypt)).thenReturn(Integer.valueOf('t'));
        when(basicEncryptionMock.computeChar('t', 3, EActionEncryptOrDecrypt.decrypt)).thenReturn(Integer.valueOf('y'));
        when(basicEncryptionMock.computeChar('y', 4, EActionEncryptOrDecrypt.decrypt)).thenReturn(Integer.valueOf('x'));

        when(basicEncryptionMock.computeChar('b', 1, EActionEncryptOrDecrypt.decrypt)).thenReturn(Integer.valueOf('y'));
        when(basicEncryptionMock.computeChar('y', 2, EActionEncryptOrDecrypt.decrypt)).thenReturn(Integer.valueOf('m'));
        when(basicEncryptionMock.computeChar('m', 3, EActionEncryptOrDecrypt.decrypt)).thenReturn(Integer.valueOf('n'));
        when(basicEncryptionMock.computeChar('n', 4, EActionEncryptOrDecrypt.decrypt)).thenReturn(Integer.valueOf('l'));

        when(basicEncryptionMock.performDecryption(any(), any())).thenCallRealMethod();

        String actualResult = basicEncryptionMock.performDecryption("ab", keys);

        assertEquals("xl", actualResult);
    }
}
