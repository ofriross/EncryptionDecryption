package enums;

public enum EEventType {
    encryptFileStart,
    encryptFileEnd,
    decryptFileStart,
    decryptFileEnd,
    encryptFolderStart,
    encryptFolderEnd,
    decryptFolderStart,
    decryptFolderEnd,
    IOException,
    FileNotFoundException,
    InvalidEncryptionKeyException,
    dataBeforeEncryption,
    dataAfterEncryption,
    dataBeforeDecryption,
    dataAfterDecryption
}
