package logs;

import complexEncryptions.IEncryptionAlgorithm;
import enums.EEventType;
import enums.ELogType;

import java.util.Optional;

public class EncryptionExceptionLogEventArgs extends EncryptionLogEventArgs {
    public EncryptionExceptionLogEventArgs(IEncryptionAlgorithm encryptionAlgorithm, String inputFilePath, String outputFilePath, long time, EEventType eventType) {
        super(encryptionAlgorithm, inputFilePath, outputFilePath, time, eventType);
        EncryptionLogger.addEncryptionLogEvent(this, encryptionAlgorithm, eventType, ELogType.error, Optional.empty());
    }

    public String makeEncryptionLogMessage(Optional<String> data) {
        String encryptDecrypt = "encrypt";
        if (eventType == EEventType.decryptFileEnd || eventType == EEventType.decryptFileStart)
            encryptDecrypt = "decrypt";
        return "The " + encryptDecrypt + "ion for file '" + inSource + "' with algorithm " +
                encryptionAlgorithm.getType() + " failed due to " + eventType + " in time: " + time + "(milliseconds).";
    }
}
