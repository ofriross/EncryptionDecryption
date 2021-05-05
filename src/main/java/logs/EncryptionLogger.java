package logs;

import Events.EventType;
import Events.EventTypeProcess;
import Events.EventTypeProcessDebug;
import complexEncryptions.IEncryptionAlgorithm;
import enums.ELogType;
import enums.EProgress;

import java.util.HashMap;
import java.util.Optional;

public class EncryptionLogger {
    private static final HashMap<HashMapKey, EncryptionLogEventArgs> encryptionBeginningsLogEventArgsMap = new HashMap<>();

    public static EncryptionLogEventArgs findEncryptionLogEventArgs(HashMapKey hashMapKey) {
        return encryptionBeginningsLogEventArgsMap.get(hashMapKey);
    }

    public static synchronized void addEncryptionLogEvent(EncryptionLogEventArgs encryptionLogEventArgs, IEncryptionAlgorithm encryptionAlgorithm, EventType eventType, ELogType logType, Optional<String> data) {
        if (eventType instanceof EventTypeProcessDebug)
            encryptionLogEventArgs.setEventType(eventType);
        if (eventType instanceof EventTypeProcess)
            if (((EventTypeProcess) eventType).getProgress() == EProgress.start)
                encryptionBeginningsLogEventArgsMap.put(new HashMapKey(encryptionAlgorithm,
                        (EventTypeProcess) eventType), encryptionLogEventArgs);
        EncryptionLog4jLogger.writeLog(encryptionLogEventArgs.makeEncryptionLogMessage(data), logType);
    }
}
