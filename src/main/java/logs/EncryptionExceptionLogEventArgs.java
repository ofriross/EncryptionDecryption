package logs;

import Events.EventType;
import Events.EventTypeProcess;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EActionEncryptOrDecrypt;
import enums.EInputType;
import enums.ELogType;
import enums.EProgress;

import java.util.Optional;

public class EncryptionExceptionLogEventArgs extends EncryptionLogEventArgs {
    public EncryptionExceptionLogEventArgs(IEncryptionAlgorithm encryptionAlgorithm, String inputFilePath, String outputFilePath, long time,
                                           EActionEncryptOrDecrypt actionEncryptOrDecrypt, EInputType inputType, EProgress progress) {
        super(encryptionAlgorithm, inputFilePath, outputFilePath, time, actionEncryptOrDecrypt, inputType, progress);
    }

    public String makeEncryptionLogMessage(Optional<String> data) {
        String encryptOrDecrypt = actionEncryptOrDecrypt.toString();
        String fileOrFolder = inputType.toString();
        return "The " + encryptOrDecrypt + "ion for " + fileOrFolder + " '" + inSource + "' with algorithm " +
                encryptionAlgorithm.getType() + " failed due to " +
                data.toString().substring(9, data.toString().length() - 1)
                + " in time: " + time + "(milliseconds).";
    }
}
