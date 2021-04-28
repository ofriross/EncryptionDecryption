package FileManaging;

import java.util.Objects;

public class FileNameAndContent {
    private String fileName;
    private String fileContent;

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public FileNameAndContent(String fileName, String fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileNameAndContent that = (FileNameAndContent) o;
        return fileName.equals(that.fileName) && fileContent.equals(that.fileContent);
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileContent() {
        return fileContent;
    }
}
