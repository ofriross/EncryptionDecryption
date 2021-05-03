package MultiThreading;

import enums.EAction;

public class FileToEncryptDecrypt {
    private String fileName;
    private EAction action;

    public FileToEncryptDecrypt(String fileName, EAction action) {
        this.fileName = fileName;
        this.action = action;
    }

    public String getFileName() {
        return fileName;
    }

    public EAction getAction() {
        return action;
    }
}
