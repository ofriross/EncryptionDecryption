package logs;

import enums.ELogType;
import org.apache.log4j.Logger;

public class EncryptionLog4jLogger {
    private static final Logger log = Logger.getLogger(EncryptionLog4jLogger.class.getName());

    public static void writeLog(String message, ELogType eLogType) {
        eLogType.writeLog(message, log);
    }
}
