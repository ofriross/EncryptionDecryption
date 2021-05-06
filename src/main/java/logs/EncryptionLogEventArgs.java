package logs;

import Events.EventType;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EActionEncryptOrDecrypt;
import enums.EInputType;
import enums.EProgress;

import java.util.Objects;
import java.util.Optional;

public abstract class EncryptionLogEventArgs {
    protected final IEncryptionAlgorithm encryptionAlgorithm;
    protected final String outSource;
    protected final String inSource;
    protected final EActionEncryptOrDecrypt actionEncryptOrDecrypt;
    protected final EProgress progress;
    protected final EInputType inputType;
    protected final long time;

    public EncryptionLogEventArgs(IEncryptionAlgorithm encryptionAlgorithm, String inSource, String outSource, long time,
                                  EActionEncryptOrDecrypt actionEncryptOrDecrypt, EInputType inputType, EProgress progress) {
        this.actionEncryptOrDecrypt = actionEncryptOrDecrypt;
        this.progress = progress;
        this.inputType = inputType;
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.outSource = outSource;
        this.inSource = inSource;
        this.time = time;
    }

    /*public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }*/

    public EProgress getProgress() {
        return progress;
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
