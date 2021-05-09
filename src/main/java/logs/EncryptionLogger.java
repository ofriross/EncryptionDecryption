package logs;

import complexEncryptions.IEncryptionAlgorithm;
import enums.EActionEncryptOrDecrypt;
import enums.EEventType;
import enums.EInputType;
import enums.EProgress;

import java.util.HashMap;
import java.util.Optional;

public class EncryptionLogger {
    private static final HashMap<HashMapKey, Long> encryptionBeginningsLogEventArgsMap = new HashMap<>();

    public static long findEncryptionLogEventArgs(HashMapKey hashMapKey) {
        return encryptionBeginningsLogEventArgsMap.get(hashMapKey);
    }

    public static void addLog(Optional<String> data, IEncryptionAlgorithm encryptionAlgorithm, String inputFilePath, String outputFilePath, long time,
                              EActionEncryptOrDecrypt actionEncryptOrDecrypt, EInputType inputType, EProgress progress, EEventType eventType) {
        if (progress == EProgress.start && eventType == EEventType.process)
            encryptionBeginningsLogEventArgsMap.put(new HashMapKey(encryptionAlgorithm, inputFilePath, outputFilePath), time);

        String message = eventType.makeEncryptionLogMessage(data, actionEncryptOrDecrypt,
                inputType, progress, encryptionAlgorithm, time, inputFilePath, outputFilePath);
        EncryptionLog4jLogger.writeLog(message, eventType.getLogType());
    }
}
