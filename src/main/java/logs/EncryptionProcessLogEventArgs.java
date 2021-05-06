package logs;

import Events.EventType;
import Events.EventTypeProcess;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EActionEncryptOrDecrypt;
import enums.EInputType;
import enums.ELogType;
import enums.EProgress;

import java.util.Optional;

public class EncryptionProcessLogEventArgs extends EncryptionLogEventArgs {

    public EncryptionProcessLogEventArgs(IEncryptionAlgorithm encryptionAlgorithm, String inputFilePath, String outputFilePath, long time,
                                         EActionEncryptOrDecrypt actionEncryptOrDecrypt, EInputType inputType, EProgress progress) {
        super(encryptionAlgorithm, inputFilePath, outputFilePath, time, actionEncryptOrDecrypt, inputType, progress);
        EncryptionLogger.addEncryptionLogEvent(this, encryptionAlgorithm, ELogType.info, Optional.empty());
    }

    public String makeEncryptionLogMessage(Optional<String> data) {
        String encryptOrDecrypt = actionEncryptOrDecrypt.toString();
        String fileOrFolder = inputType.toString();
        String message = "";
        if (progress == EProgress.start) {
            message = "The " + encryptOrDecrypt + "ion for " + fileOrFolder + " '" + inSource + "' with algorithm " +
                    encryptionAlgorithm.getType() + " started in time: " + time + "(milliseconds).";
        } else {
            EventTypeProcess myStartsType = new EventTypeProcess(actionEncryptOrDecrypt, inputType, EProgress.start);
            EncryptionLogEventArgs myStart = EncryptionLogger.findEncryptionLogEventArgs(encryptionAlgorithm);
            return "The " + encryptOrDecrypt + "ion for " + fileOrFolder + " '" + inSource + "' with algorithm " +
                    encryptionAlgorithm.getType() + " ended in time: " + time + "(milliseconds). It took" +
                    " in total " + (time - myStart.time) + " (milliseconds).";
        }
        message += "The " + encryptOrDecrypt + "ed " + fileOrFolder + " is located in '" + outSource + "'.";
        return message;
    }
}
