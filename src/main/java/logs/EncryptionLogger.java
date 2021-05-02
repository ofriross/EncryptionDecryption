package logs;

import complexEncryptions.IEncryptionAlgorithm;
import enums.EEventType;
import enums.ELogType;

import java.util.HashMap;
import java.util.Optional;

public class EncryptionLogger {
    private static final HashMap<HashMapKey, EncryptionLogEventArgs> encryptionBeginningsLogEventArgsMap = new HashMap<>();

    public static EncryptionLogEventArgs findEncryptionLogEventArgs(HashMapKey hashMapKey) {
        return encryptionBeginningsLogEventArgsMap.get(hashMapKey);
    }

    public static void addEncryptionLogEvent(EncryptionLogEventArgs encryptionLogEventArgs, IEncryptionAlgorithm encryptionAlgorithm, EEventType eventType, ELogType logType, Optional<String> data) {
        encryptionLogEventArgs.setEventType(eventType);
        if (eventType == EEventType.encryptFileStart || eventType == EEventType.decryptFileStart || eventType == EEventType.decryptFolderStart || eventType == EEventType.encryptFolderStart)
            encryptionBeginningsLogEventArgsMap.put(new HashMapKey(encryptionAlgorithm, eventType), encryptionLogEventArgs);
        EncryptionLog4jLogger.writeLog(encryptionLogEventArgs.makeEncryptionLogMessage(data), logType);
    }
}
