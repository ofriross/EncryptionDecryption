package logs;

import complexEncryptions.IEncryptionAlgorithm;
import enums.EEventType;
import enums.ELogType;

import java.util.HashMap;

public class EncryptionLogger {
    private static final HashMap<HashMapKey, EncryptionLogEventArgs> encryptionLogEventArgsMap = new HashMap<>();

    public static EncryptionLogEventArgs findEncryptionLogEventArgs(HashMapKey hashMapKey) {
        return encryptionLogEventArgsMap.get(hashMapKey);
    }

    public static void addEncryptionLogEvent(EncryptionLogEventArgs encryptionLogEventArgs, IEncryptionAlgorithm encryptionAlgorithm, EEventType eventType, ELogType logType) {
        encryptionLogEventArgsMap.put(new HashMapKey(encryptionAlgorithm, eventType), encryptionLogEventArgs);
        EncryptionLog4jLogger.writeLog(encryptionLogEventArgs.makeEncryptionLogMessage(), logType);
    }
}
