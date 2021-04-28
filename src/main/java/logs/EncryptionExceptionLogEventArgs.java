package logs;

import complexEncryptions.IEncryptionAlgorithm;
import enums.EEventType;
import enums.ELogType;

public class EncryptionExceptionLogEventArgs extends EncryptionLogEventArgs {
    public EncryptionExceptionLogEventArgs(IEncryptionAlgorithm encryptionAlgorithm, String fileIn, String fileOut, long time, EEventType eventType) {
        super(encryptionAlgorithm, fileIn, fileOut, time, eventType);
        EncryptionLogger.addEncryptionLogEvent(this, encryptionAlgorithm, eventType, ELogType.error);
    }

    public String makeEncryptionLogMessage() {
        String encryptDecrypt = "encrypt";
        if (eventType == EEventType.decryptEnd || eventType == EEventType.decryptStart)
            encryptDecrypt = "decrypt";
        return "The " + encryptDecrypt + "ion for file '" + inSource + "' with algorithm " +
                encryptionAlgorithm.getType() + " failed due to " + eventType + " in time: " + time + "(milliseconds).";
    }
}
