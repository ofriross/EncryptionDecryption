package logs;

import Events.EventTypeProcess;
import complexEncryptions.IEncryptionAlgorithm;

import java.util.Objects;

public class HashMapKey {
    private final IEncryptionAlgorithm encryptionAlgorithm;
    private final String inputFilePath;
    private final String outputFilePath;

    public HashMapKey(IEncryptionAlgorithm encryptionAlgorithm, String inputFilePath, String outputFilePath) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashMapKey that = (HashMapKey) o;
        return encryptionAlgorithm.equals(that.encryptionAlgorithm) && inputFilePath.equals(that.inputFilePath) && outputFilePath.equals(that.outputFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(encryptionAlgorithm, inputFilePath, outputFilePath);
    }
}
