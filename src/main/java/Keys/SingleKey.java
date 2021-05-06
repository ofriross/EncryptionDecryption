package Keys;

import General.Constants;

import java.util.Random;

public class SingleKey extends Key {
    private int value;
    private final String encryptionType;

    public int getValue() {
        return value;
    }

    public SingleKey(String encryptionType) {
        this.encryptionType = encryptionType;
        Random r = new Random();
        value = r.nextInt(Constants.MAX_ASCII_VALUE) + 1;
    }

    public String toString() {
        return String.valueOf(value);
    }

    protected Object clone()
            throws CloneNotSupportedException {
        return super.clone();
    }

    public int getKeyStrength() {
        int keyValue = value;
        int strength = 1;
        while (keyValue / 10 >= 10) {
            strength++;
            keyValue /= 10;
        }
        return strength;
    }

    public String getType() {
        return encryptionType;
    }

    public void getNextKey() {
        value++;
        if (value == Constants.MAX_ASCII_VALUE + 1)
            value = 0;
    }
}
