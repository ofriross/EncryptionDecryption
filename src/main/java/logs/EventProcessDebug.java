package logs;

import Events.EventTypeProcess;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EActionEncryptOrDecrypt;
import enums.EInputType;
import enums.ELogType;
import enums.EProgress;

import java.util.Optional;

public class EventProcessDebug {
    public static String makeEncryptionLogMessage(Optional<String> data, EActionEncryptOrDecrypt actionEncryptOrDecrypt,
                                                  EInputType inputType, EProgress progress, IEncryptionAlgorithm encryptionAlgorithm,
                                                  long time, String inSource, String outSource) {
        return "debug later";
        /*String encryptDecrypt = actionEncryptOrDecrypt.toString();
        String dataString = data.toString().substring(9, data.toString().length() - 1);
        if (progress == EProgress.start)
            return "The " + encryptDecrypt + "ion for file '" + inSource + "' with algorithm " +
                    encryptionAlgorithm.getType() + ", received the data '" + dataString + "' in time: " +
                    time + "(milliseconds).";
        return "The " + encryptDecrypt + "ion for file '" + inSource + "' with algorithm " +
                encryptionAlgorithm.getType() + ", will write the " + encryptDecrypt + "ed data:'" +
                dataString + "' to file '" + outSource + "' in time: " + time + "(milliseconds).";*/
    }
}
