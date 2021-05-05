package logs;

import Events.EventTypeProcess;
import complexEncryptions.IEncryptionAlgorithm;

import java.util.Objects;

public class HashMapKey {
    private final IEncryptionAlgorithm encryptionAlgorithm;
    private final EventTypeProcess eventTypeProcess;

    public HashMapKey(IEncryptionAlgorithm encryptionAlgorithm, EventTypeProcess eventTypeProcess) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.eventTypeProcess = eventTypeProcess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashMapKey that = (HashMapKey) o;
        return Objects.equals(encryptionAlgorithm, that.encryptionAlgorithm) && Objects.equals(eventTypeProcess, that.eventTypeProcess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(encryptionAlgorithm, eventTypeProcess);
    }
}
