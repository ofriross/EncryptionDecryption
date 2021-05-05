package Events;

import enums.EActionEncryptOrDecrypt;
import enums.EInputType;
import enums.EProgress;

import java.util.Objects;

public class EventTypeProcess extends EventType {
    EActionEncryptOrDecrypt encryptOrDecrypt;
    EInputType inputType;
    EProgress progress;

    public EventTypeProcess(EActionEncryptOrDecrypt encryptOrDecrypt, EInputType inputType, EProgress progress) {
        this.encryptOrDecrypt = encryptOrDecrypt;
        this.inputType = inputType;
        this.progress = progress;
    }

    public EActionEncryptOrDecrypt getEncryptOrDecrypt() {
        return encryptOrDecrypt;
    }

    public EInputType getInputType() {
        return inputType;
    }

    public EProgress getProgress() {
        return progress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventTypeProcess that = (EventTypeProcess) o;
        return encryptOrDecrypt == that.encryptOrDecrypt && inputType == that.inputType && progress == that.progress;
    }

    @Override
    public int hashCode() {
        return Objects.hash(encryptOrDecrypt, inputType, progress);
    }
}
