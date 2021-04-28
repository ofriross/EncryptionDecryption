package Exceptions;

public class InvalidEncryptionKeyException extends Exception {

    private static final String basicMessage = "the key to decrypt with, from the file, isn't proper. ";
    public InvalidEncryptionKeyException(String exceptionReason) {
        super(basicMessage + exceptionReason);
    }
}
