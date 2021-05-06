package logs;

import Events.EventTypeProcess;
import complexEncryptions.IEncryptionAlgorithm;
import enums.EActionEncryptOrDecrypt;
import enums.EInputType;
import enums.EProgress;

import java.util.Optional;

public class EncryptionProcessDebugLogEventArgs extends EncryptionLogEventArgs {

    public EncryptionProcessDebugLogEventArgs(IEncryptionAlgorithm encryptionAlgorithm, String inputFilePath, String outputFilePath, long time,
                                              EActionEncryptOrDecrypt actionEncryptOrDecrypt, EInputType inputType, EProgress progress) {
        super(encryptionAlgorithm, inputFilePath, outputFilePath, time, actionEncryptOrDecrypt, inputType, progress);
    }

    public String makeEncryptionLogMessage(Optional<String> data) {
        return "debug later";
        /*String encryptDecrypt = ((EventTypeProcessDebug) eventType).getEncryptOrDecrypt().toString();
        String dataString = data.toString().substring(9, data.toString().length() - 1);
        if (((EventTypeProcessDebug) eventType).getProgress() == EProgress.start)
            return "The " + encryptDecrypt + "ion for file '" + inSource + "' with algorithm " +
                    encryptionAlgorithm.getType() + ", received the data '" + dataString + "' in time: " +
                    time + "(milliseconds).";
        return "The " + encryptDecrypt + "ion for file '" + inSource + "' with algorithm " +
                encryptionAlgorithm.getType() + ", will write the " + encryptDecrypt + "ed data:'" +
                dataString + "' to file '" + outSource + "' in time: " + time + "(milliseconds).";*/
    }
}
