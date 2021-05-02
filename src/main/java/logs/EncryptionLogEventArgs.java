package logs;

import complexEncryptions.IEncryptionAlgorithm;
import enums.EEventType;

import java.util.Objects;
import java.util.Optional;

public abstract class EncryptionLogEventArgs {
    protected final IEncryptionAlgorithm encryptionAlgorithm;
    protected final String outSource;
    protected final String inSource;
    protected EEventType eventType;
    protected final long time;

    public EncryptionLogEventArgs(IEncryptionAlgorithm encryptionAlgorithm, String inSource, String outSource, long time, EEventType eventType) {
        this.eventType = eventType;
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.outSource = outSource;
        this.inSource = inSource;
        this.time = time;
    }

    public void setEventType(EEventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EncryptionLogEventArgs that = (EncryptionLogEventArgs) o;
        return time == that.time && encryptionAlgorithm.equals(that.encryptionAlgorithm) && outSource.equals(that.outSource) && inSource.equals(that.inSource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(encryptionAlgorithm, outSource, inSource, time);
    }

    public abstract String makeEncryptionLogMessage(Optional<String> data);
}
