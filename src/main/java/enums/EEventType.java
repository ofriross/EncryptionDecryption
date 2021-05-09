package enums;

import complexEncryptions.IEncryptionAlgorithm;
import logs.EventException;
import logs.EventProcessDebug;
import logs.EventProcess;

import java.util.Optional;

public enum EEventType {
    process {
        public ELogType getLogType() {
            return ELogType.info;
        }

        public String makeEncryptionLogMessage(Optional<String> data, EActionEncryptOrDecrypt actionEncryptOrDecrypt,
                                               EInputType inputType, EProgress progress, IEncryptionAlgorithm encryptionAlgorithm,
                                               long time, String inSource, String outSource) {
            return EventProcess.makeEncryptionLogMessage(data, actionEncryptOrDecrypt,
                    inputType, progress, encryptionAlgorithm,
                    time, inSource, outSource);
        }
    },
    processDebug {
        public ELogType getLogType() {
            return ELogType.debug;
        }

        public String makeEncryptionLogMessage(Optional<String> data, EActionEncryptOrDecrypt actionEncryptOrDecrypt,
                                               EInputType inputType, EProgress progress, IEncryptionAlgorithm encryptionAlgorithm,
                                               long time, String inSource, String outSource) {
            return EventProcessDebug.makeEncryptionLogMessage(data, actionEncryptOrDecrypt,
                    inputType, progress, encryptionAlgorithm,
                    time, inSource, outSource);
        }
    },
    exception {
        public ELogType getLogType() {
            return ELogType.info;
        }

        public String makeEncryptionLogMessage(Optional<String> data, EActionEncryptOrDecrypt actionEncryptOrDecrypt,
                                               EInputType inputType, EProgress progress, IEncryptionAlgorithm encryptionAlgorithm,
                                               long time, String inSource, String outSource) {
            return EventException.makeEncryptionLogMessage(data, actionEncryptOrDecrypt,
                    inputType, progress, encryptionAlgorithm,
                    time, inSource, outSource);
        }
    };

    public abstract ELogType getLogType();

    public abstract String makeEncryptionLogMessage(Optional<String> data, EActionEncryptOrDecrypt actionEncryptOrDecrypt,
                                                    EInputType inputType, EProgress progress, IEncryptionAlgorithm encryptionAlgorithm,
                                                    long time, String inSource, String outSource);
}
