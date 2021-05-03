package enums;

import logs.EncryptionLog4jLogger;
import org.apache.log4j.Logger;

public enum ELogType {
    info {
        @Override
        public void writeLog(String message, Logger log) {
            log.info(message);
        }
    },
    fatal {
        @Override
        public void writeLog(String message, Logger log) {
            log.fatal(message);
        }
    },
    error {
        @Override
        public void writeLog(String message, Logger log) {
            log.error(message);
        }
    },
    debug {
        @Override
        public void writeLog(String message, Logger log) {
            log.debug(message);
        }
    },
    warn {
        @Override
        public void writeLog(String message, Logger log) {
            log.warn(message);
        }
    },
    trace {
        @Override
        public void writeLog(String message, Logger log) {
            log.trace(message);
        }
    };

    public abstract void writeLog(String message, Logger log);
}
