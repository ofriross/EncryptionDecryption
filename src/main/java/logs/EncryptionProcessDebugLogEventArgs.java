package logs;

import complexEncryptions.IEncryptionAlgorithm;
import enums.EEventType;

import java.util.Optional;

public class EncryptionProcessDebugLogEventArgs extends EncryptionLogEventArgs {

    public EncryptionProcessDebugLogEventArgs(IEncryptionAlgorithm encryptionAlgorithm, String fileIn, String fileOut, long time, EEventType eventType) {
        super(encryptionAlgorithm, fileIn, fileOut, time, eventType);
    }

    public String makeEncryptionLogMessage(Optional<String> data) {
        String encryptDecrypt;
        if (eventType == EEventType.dataAfterEncryption || eventType == EEventType.dataBeforeEncryption)
            encryptDecrypt = "encrypt";
        else
            encryptDecrypt = "decrypt";
        if (eventType == EEventType.dataBeforeEncryption || eventType == EEventType.dataBeforeDecryption)
            return "The " + encryptDecrypt + "ion for file '" + inSource + "' with algorithm " +
                    encryptionAlgorithm.getType() + ", received the data '" + data + "' in time: " +
                    time + "(milliseconds).";
        return "The " + encryptDecrypt + "ion for file '" + inSource + "' with algorithm " +
                encryptionAlgorithm.getType() + ", will write the " + encryptDecrypt + "ed data:'" +
                data + "' to file '" + outSource + "' in time: " + time + "(milliseconds).";
    }
}
