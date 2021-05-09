package logs;

import Events.EventTypeProcess;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EActionEncryptOrDecrypt;
import enums.EInputType;
import enums.ELogType;
import enums.EProgress;

import java.util.Optional;

public class EventException {

    public static String makeEncryptionLogMessage(Optional<String> exception, EActionEncryptOrDecrypt actionEncryptOrDecrypt,
                                                  EInputType inputType, EProgress progress, IEncryptionAlgorithm encryptionAlgorithm,
                                                  long time, String inSource, String outSource) {
        String encryptOrDecrypt = actionEncryptOrDecrypt.toString();
        String fileOrFolder = inputType.toString();
        return "The " + encryptOrDecrypt + "ion for " + fileOrFolder + " '" + inSource + "' with algorithm " +
                encryptionAlgorithm.getType() + " failed due to " +
                exception.toString().substring(9, exception.toString().length() - 1)
                + " in time: " + time + "(milliseconds).";
    }
}
