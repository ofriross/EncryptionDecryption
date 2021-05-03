package Keys;

import complexEncryptions.IEncryptionAlgorithm;

public class RepeatKey extends Key {
    private final int repeatN;
    private Key repeatedKey;

    public void setRepeatedKey(Key repeatedKey) {
        this.repeatedKey = repeatedKey;
    }

    public Key getRepeatedKey() {
        return repeatedKey;
    }

    public RepeatKey(int repeatN, Key repeatedKey) {
        this.repeatN = repeatN;
        this.repeatedKey = repeatedKey;
    }

    public String toString() {
        StringBuilder total = new StringBuilder(repeatedKey.toString());
        String base = repeatedKey.toString();
        for (int i = 0; i < repeatN - 1; i++)
            total.append(",").append(base);
        return total.toString();
    }

    public int getKeyStrength() {
        return repeatN * repeatedKey.getKeyStrength();
    }

    public String getType() {
        return "Repeat of " + repeatN + " times of " + repeatedKey.getType();
    }

    public void getNextKey() {
        repeatedKey.getNextKey();
    }
}
