package logs;

import complexEncryptions.IEncryptionAlgorithm;
import enums.EEventType;
import enums.ELogType;

public class EncryptionDebugLogEventArgs extends EncryptionLogEventArgs {
    private String data;

    public EncryptionDebugLogEventArgs(IEncryptionAlgorithm encryptionAlgorithm, String fileIn, String fileOut, long time, EEventType eventType, String data) {
        super(encryptionAlgorithm, fileIn, fileOut, time, eventType);
        this.data = data;
        EncryptionLogger.addEncryptionLogEvent(this, encryptionAlgorithm, eventType, ELogType.debug);
    }

    public String makeEncryptionLogMessage() {
        String encryptDecrypt = "encrypt";
        if (eventType == EEventType.decryptionOutData || eventType == EEventType.decryptionInData)
            encryptDecrypt = "decrypt";
        if (eventType == EEventType.encryptionInData || eventType == EEventType.decryptionInData)
            return "The " + encryptDecrypt + "ion for file '" + inSource + "' with algorithm " +
                    encryptionAlgorithm.getType() + ", received the data '" + data + "' in time: " +
                    time + "(milliseconds).";
        return "The " + encryptDecrypt + "ion for file '" + inSource + "' with algorithm " +
                encryptionAlgorithm.getType() + ", will write the " + encryptDecrypt + "ed data:'" +
                data + "' to file '" + outSource + "' in time: " + time + "(milliseconds).";
    }
}
