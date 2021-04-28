import complexEncryptions.IEncryptionAlgorithm;

import java.util.Comparator;

public class SortByKeyStrength implements Comparator<IEncryptionAlgorithm> {

    public int compare(IEncryptionAlgorithm encryptionAlgorithm1, IEncryptionAlgorithm encryptionAlgorithm2) {
        return encryptionAlgorithm1.getKeyStrength() - encryptionAlgorithm2.getKeyStrength();
    }
}
