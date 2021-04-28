package logs;

import complexEncryptions.IEncryptionAlgorithm;
import enums.EEventType;
import enums.ELogType;

public class EncryptionProcessLogEventArgs extends EncryptionLogEventArgs {
    public EncryptionProcessLogEventArgs(IEncryptionAlgorithm encryptionAlgorithm, String fileIn, String fileOut, long time, EEventType eventType) {
        super(encryptionAlgorithm, fileIn, fileOut, time, eventType);
        EncryptionLogger.addEncryptionLogEvent(this, encryptionAlgorithm, eventType, ELogType.info);
    }

    public String makeEncryptionLogMessage() {
        String encryptDecrypt = "encrypt";
        if (eventType == EEventType.decryptEnd || eventType == EEventType.decryptStart)
            encryptDecrypt = "decrypt";
        if (isStart())
            return "The " + encryptDecrypt + "ion for folder '" + inSource + "' with algorithm " +
                    encryptionAlgorithm.getType() + " started in time: " + time + "(milliseconds). The " +
                    encryptDecrypt + "ed files will be located in folder '" + outSource + "'.";
        else {
            EncryptionLogEventArgs myStart = EncryptionLogger.findEncryptionLogEventArgs(new HashMapKey(encryptionAlgorithm, getMyStartType()));
            return "The " + encryptDecrypt + "ion for folder '" + inSource + "' with algorithm " +
                    encryptionAlgorithm.getType() + " ended in time: " + time + "(milliseconds). It took" +
                    " in total " + (time - myStart.time) + " milliseconds. The " + encryptDecrypt + "ed files" +
                    " are located in folder '" + outSource + "'.";
        }
    }

    private boolean isStart() {
        return eventType == EEventType.encryptStart || eventType == EEventType.decryptStart;
    }

    private EEventType getMyStartType() {
        if (eventType == EEventType.encryptEnd)
            return EEventType.encryptStart;
        else
            return EEventType.decryptStart;
    }
}
