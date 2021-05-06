package logs;

import complexEncryptions.IEncryptionAlgorithm;
import enums.ELogType;
import enums.EProgress;

import java.util.HashMap;
import java.util.Optional;

public class EncryptionLogger {
    private static final HashMap<IEncryptionAlgorithm, EncryptionLogEventArgs> encryptionBeginningsLogEventArgsMap = new HashMap<>();

    public static EncryptionLogEventArgs findEncryptionLogEventArgs(IEncryptionAlgorithm encryptionAlgorithm) {
        return encryptionBeginningsLogEventArgsMap.get(encryptionAlgorithm);
    }

    public static synchronized void addEncryptionLogEvent(EncryptionLogEventArgs encryptionLogEventArgs, IEncryptionAlgorithm encryptionAlgorithm, ELogType logType, Optional<String> data) {
        //encryptionLogEventArgs.setEventType(eventType);
        if (encryptionLogEventArgs instanceof EncryptionProcessLogEventArgs)
            if (encryptionLogEventArgs.getProgress() == EProgress.start)
                encryptionBeginningsLogEventArgsMap.put(encryptionAlgorithm, encryptionLogEventArgs);
        EncryptionLog4jLogger.writeLog(encryptionLogEventArgs.makeEncryptionLogMessage(data), logType);
    }
}
