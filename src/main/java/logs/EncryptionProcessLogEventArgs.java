package logs;

import complexEncryptions.IEncryptionAlgorithm;
import enums.EEventType;
import enums.ELogType;

import java.util.Optional;

public class EncryptionProcessLogEventArgs extends EncryptionLogEventArgs {

    public EncryptionProcessLogEventArgs(IEncryptionAlgorithm encryptionAlgorithm, String inputFilePath, String outputFilePath, long time, EEventType eventType) {
        super(encryptionAlgorithm, inputFilePath, outputFilePath, time, eventType);
        EncryptionLogger.addEncryptionLogEvent(this, encryptionAlgorithm, eventType, ELogType.info, Optional.empty());
    }

    public String makeEncryptionLogMessage(Optional<String> data) {
        String encryptOrDecrypt = "encrypt", fileOrFolder = "file";
        if (eventType == EEventType.decryptFileEnd || eventType == EEventType.decryptFileStart ||
                eventType == EEventType.decryptFolderEnd || eventType == EEventType.decryptFolderStart)
            encryptOrDecrypt = "decrypt";
        if (eventType == EEventType.encryptFolderEnd || eventType == EEventType.decryptFolderEnd ||
                eventType == EEventType.encryptFolderStart || eventType == EEventType.decryptFolderStart)
            fileOrFolder = "folder";
        String message;
        if (isStart()) {
            message = "The " + encryptOrDecrypt + "ion for " + fileOrFolder + " '" + inSource + "' with algorithm " +
                    encryptionAlgorithm.getType() + " started in time: " + time + "(milliseconds).";
        } else {
            EncryptionLogEventArgs myStart = EncryptionLogger.findEncryptionLogEventArgs(new HashMapKey(encryptionAlgorithm, getMyStartType()));
            return "The " + encryptOrDecrypt + "ion for " + fileOrFolder + " '" + inSource + "' with algorithm " +
                    encryptionAlgorithm.getType() + " ended in time: " + time + "(milliseconds). It took" +
                    " in total " + (time - myStart.time) + " milliseconds.";
        }
        if (fileOrFolder.equals("file"))
            message += "The " + encryptOrDecrypt + "ed file is located in '" + outSource + "'.";
        else
            message += "The " + encryptOrDecrypt + "ed files are located in folder '" + outSource + "'.";
        return message;
    }

    private boolean isStart() {
        return eventType == EEventType.encryptFileStart || eventType == EEventType.decryptFileStart ||
                eventType == EEventType.decryptFolderStart || eventType == EEventType.encryptFolderStart;
    }

    private EEventType getMyStartType() {
        switch (eventType) {
            case encryptFileEnd:
                return EEventType.encryptFileStart;
            case encryptFolderEnd:
                return EEventType.encryptFolderStart;
            case decryptFileEnd:
                return EEventType.decryptFileStart;
            case decryptFolderEnd:
                return EEventType.decryptFolderStart;
            default:
                return null;
        }
    }
}
