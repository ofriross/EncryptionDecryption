import org.junit.Test;
import java.io.File;
import static org.junit.Assert.assertEquals;

public class FileEncryptorTest {
    @Test
    public void encryptDecryptTest(){
        String resED=FileEncryptor.encryptDecrypt("abcdefg",5, FileEncryptor.BaseEnc.shiftUp);
        assertEquals("fghijkl",resED);
    }
}
