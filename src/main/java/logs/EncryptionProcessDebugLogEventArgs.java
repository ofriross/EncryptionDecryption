package logs;

import complexEncryptions.IEncryptionAlgorithm;
import enums.EEventType;

import java.util.Optional;

public class EncryptionProcessDebugLogEventArgs extends EncryptionLogEventArgs {

    public EncryptionProcessDebugLogEventArgs(IEncryptionAlgorithm encryptionAlgorithm, String inputFilePath, String outputFilePath, long time, EEventType eventType) {
        super(encryptionAlgorithm, inputFilePath, outputFilePath, time, eventType);
    }

    public String makeEncryptionLogMessage(Optional<String> data) {
        return "debuglater";
        /*String encryptDecrypt;
        if (eventType == EEventType.dataAfterEncryption || eventType == EEventType.dataBeforeEncryption)
            encryptDecrypt = "encrypt";
        else
            encryptDecrypt = "decrypt";
        String dataString = data.toString().substring(9, data.toString().length() - 1);
        if (eventType == EEventType.dataBeforeEncryption || eventType == EEventType.dataBeforeDecryption)
            return "The " + encryptDecrypt + "ion for file '" + inSource + "' with algorithm " +
                    encryptionAlgorithm.getType() + ", received the data '" + dataString + "' in time: " +
                    time + "(milliseconds).";
        return "The " + encryptDecrypt + "ion for file '" + inSource + "' with algorithm " +
                encryptionAlgorithm.getType() + ", will write the " + encryptDecrypt + "ed data:'" +
                dataString + "' to file '" + outSource + "' in time: " + time + "(milliseconds).";*/
    }
}
