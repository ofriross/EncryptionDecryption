package logs;

import Events.EventType;
import Events.EventTypeProcess;
import complexEncryptions.IEncryptionAlgorithm;
import enums.ELogType;
import enums.EProgress;

import java.util.Optional;

public class EncryptionProcessLogEventArgs extends EncryptionLogEventArgs {

    public EncryptionProcessLogEventArgs(IEncryptionAlgorithm encryptionAlgorithm, String inputFilePath, String outputFilePath, long time, EventType eventType) {
        super(encryptionAlgorithm, inputFilePath, outputFilePath, time, eventType);
        EncryptionLogger.addEncryptionLogEvent(this, encryptionAlgorithm, eventType, ELogType.info, Optional.empty());
    }

    public String makeEncryptionLogMessage(Optional<String> data) {
        String encryptOrDecrypt = ((EventTypeProcess) eventType).getEncryptOrDecrypt().toString();
        String fileOrFolder = ((EventTypeProcess) eventType).getInputType().toString();
        String message = "";
        if (((EventTypeProcess) eventType).getProgress() == EProgress.start) {
            message = "The " + encryptOrDecrypt + "ion for " + fileOrFolder + " '" + inSource + "' with algorithm " +
                    encryptionAlgorithm.getType() + " started in time: " + time + "(milliseconds).";
        } else {
            EventTypeProcess myStartsType = new EventTypeProcess(((EventTypeProcess) eventType).getEncryptOrDecrypt(),
                    ((EventTypeProcess) eventType).getInputType(), EProgress.start);
            EncryptionLogEventArgs myStart = EncryptionLogger.findEncryptionLogEventArgs(new HashMapKey(encryptionAlgorithm, myStartsType));
            return "The " + encryptOrDecrypt + "ion for " + fileOrFolder + " '" + inSource + "' with algorithm " +
                    encryptionAlgorithm.getType() + " ended in time: " + time + "(milliseconds). It took" +
                    " in total " + (time - myStart.time) + " (milliseconds).";
        }
        message += "The " + encryptOrDecrypt + "ed " + fileOrFolder + " is located in '" + outSource + "'.";
        return message;
    }
}
