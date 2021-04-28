package logs;

import complexEncryptions.IEncryptionAlgorithm;
import enums.EEventType;

import java.util.Objects;

public abstract class EncryptionLogEventArgs {
    protected final IEncryptionAlgorithm encryptionAlgorithm;
    protected final String outSource;
    protected final String inSource;
    protected final long time;
    protected final EEventType eventType;

    public EncryptionLogEventArgs(IEncryptionAlgorithm encryptionAlgorithm, String inSource, String outSource, long time, EEventType eventType) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.outSource = outSource;
        this.inSource = inSource;
        this.time = time;
        this.eventType = eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EncryptionLogEventArgs that = (EncryptionLogEventArgs) o;
        return time == that.time && encryptionAlgorithm.equals(that.encryptionAlgorithm) && outSource.equals(that.outSource) && inSource.equals(that.inSource) && eventType == that.eventType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(encryptionAlgorithm, outSource, inSource, time, eventType);
    }

    public abstract String makeEncryptionLogMessage();
}
