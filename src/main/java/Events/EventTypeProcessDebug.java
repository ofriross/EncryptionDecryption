package Events;

import enums.EActionEncryptOrDecrypt;
import enums.EInputType;
import enums.EProgress;

public class EventTypeProcessDebug extends EventType {
    EActionEncryptOrDecrypt encryptOrDecrypt;
    EInputType inputType;
    EProgress progress;

    public EventTypeProcessDebug(EActionEncryptOrDecrypt encryptOrDecrypt, EInputType inputType, EProgress progress) {
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
}
