package basicEncryptions;

import complexEncryptions.IEncryptionAlgorithm;
import enums.EAction;

public interface IBasicEncryption extends IEncryptionAlgorithm {
    int computeChar(int ch, int key, EAction EAction);
}
