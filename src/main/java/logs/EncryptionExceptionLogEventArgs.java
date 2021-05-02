package logs;

import complexEncryptions.IEncryptionAlgorithm;
import enums.EEventType;
import enums.ELogType;

import java.util.Optional;

public class EncryptionExceptionLogEventArgs extends EncryptionLogEventArgs {
    public EncryptionExceptionLogEventArgs(IEncryptionAlgorithm encryptionAlgorithm, String fileIn, String fileOut, long time, EEventType eventType) {
        super(encryptionAlgorithm, fileIn, fileOut, time, eventType);
        EncryptionLogger.addEncryptionLogEvent(this, encryptionAlgorithm, eventType, ELogType.error, Optional.empty());
    }

    public String makeEncryptionLogMessage(Optional<String> data) {
        String encryptDecrypt = "encrypt";
        if (eventType == EEventType.decryptEnd || eventType == EEventType.decryptStart)
            encryptDecrypt = "decrypt";
        return "The " + encryptDecrypt + "ion for file '" + inSource + "' with algorithm " +
                encryptionAlgorithm.getType() + " failed due to " + eventType + " in time: " + time + "(milliseconds).";
    }
}
