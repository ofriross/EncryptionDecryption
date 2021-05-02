package logs;

import complexEncryptions.IEncryptionAlgorithm;
import enums.EEventType;
import enums.ELogType;

import java.util.HashMap;
import java.util.Optional;

public class EncryptionLogger {
    private static final HashMap<HashMapKey, EncryptionLogEventArgs> encryptionBegginingsLogEventArgsMap = new HashMap<>();

    public static EncryptionLogEventArgs findEncryptionLogEventArgs(HashMapKey hashMapKey) {
        return encryptionBegginingsLogEventArgsMap.get(hashMapKey);
    }

    public static void addEncryptionLogEvent(EncryptionLogEventArgs encryptionLogEventArgs, IEncryptionAlgorithm encryptionAlgorithm, EEventType eventType, ELogType logType, Optional<String> data) {
        encryptionLogEventArgs.setEventType(eventType);
        if (eventType == EEventType.encryptStart || eventType == EEventType.decryptStart)
            encryptionBegginingsLogEventArgsMap.put(new HashMapKey(encryptionAlgorithm, eventType), encryptionLogEventArgs);
        EncryptionLog4jLogger.writeLog(encryptionLogEventArgs.makeEncryptionLogMessage(data), logType);
    }
}
