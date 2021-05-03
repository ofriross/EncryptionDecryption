package MultiThreading;

import enums.EActionEncryptOrDecrypt;

public class FileToEncryptDecrypt {
    private String fileName;
    private EActionEncryptOrDecrypt action;

    public FileToEncryptDecrypt(String fileName, EActionEncryptOrDecrypt action) {
        this.fileName = fileName;
        this.action = action;
    }

    public String getFileName() {
        return fileName;
    }

    public EActionEncryptOrDecrypt getAction() {
        return action;
    }
}
