package logs;

import complexEncryptions.IEncryptionAlgorithm;
import enums.EEventType;

import java.util.Objects;

public class HashMapKey {
    private final IEncryptionAlgorithm encryptionAlgorithm;
    private final EEventType eventType;

    public HashMapKey(IEncryptionAlgorithm encryptionAlgorithm, EEventType eventType) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.eventType = eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashMapKey that = (HashMapKey) o;
        return encryptionAlgorithm.equals(that.encryptionAlgorithm) && eventType == that.eventType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(encryptionAlgorithm, eventType);
    }
}
