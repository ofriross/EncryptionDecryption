package basicEncryptions;

import complexEncryptions.IEncryptionAlgorithm;
import enums.EActionEncryptOrDecrypt;

public interface IBasicEncryption extends IEncryptionAlgorithm {
    int computeChar(int currentChar, int key, EActionEncryptOrDecrypt EActionEncryptOrDecrypt);
}
