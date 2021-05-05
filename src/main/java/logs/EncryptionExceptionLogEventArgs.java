package logs;

import Events.EventType;
import Events.EventTypeProcess;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EActionEncryptOrDecrypt;
import enums.ELogType;

import java.util.Optional;

public class EncryptionExceptionLogEventArgs extends EncryptionLogEventArgs {
    public EncryptionExceptionLogEventArgs(IEncryptionAlgorithm encryptionAlgorithm, String inputFilePath, String outputFilePath, long time, EventType eventType) {
        super(encryptionAlgorithm, inputFilePath, outputFilePath, time, eventType);
        EncryptionLogger.addEncryptionLogEvent(this, encryptionAlgorithm, eventType, ELogType.error, Optional.empty());
    }

    public String makeEncryptionLogMessage(Optional<String> data) {
        String encryptOrDecrypt;
        if (((EventTypeProcess) eventType).getEncryptOrDecrypt() == EActionEncryptOrDecrypt.encrypt)
            encryptOrDecrypt = "encrypt";
        else
            encryptOrDecrypt = "decrypt";
        return "The " + encryptOrDecrypt + "ion for file '" + inSource + "' with algorithm " +
                encryptionAlgorithm.getType() + " failed due to " + eventType + " in time: " + time + "(milliseconds).";
    }
}
