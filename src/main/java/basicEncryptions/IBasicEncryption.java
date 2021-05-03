package basicEncryptions;

import Exceptions.ProblematicCharInEncryption;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EActionEncryptOrDecrypt;

public interface IBasicEncryption extends IEncryptionAlgorithm {
    int computeChar(int currentChar, int key, EActionEncryptOrDecrypt EActionEncryptOrDecrypt) throws ProblematicCharInEncryption;
}
