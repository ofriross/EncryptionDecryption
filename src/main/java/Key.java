import java.util.Random;

public class Key implements Cloneable {
    private int value;
    private Key double1;
    private Key double2;
    private Key repeat;
    private int repeatN;
    private KeyType keyType;

    enum KeyType {
        single,
        doubled,
        repeat,
        unknown
    }

    /*public Key(KeyType keyType) {
        this.keyType = keyType;
        if (keyType == KeyType.single) {
            Random r = new Random();
            value = r.nextInt(3) + 1;
        }
        if (keyType == KeyType.unknown)
            this.keyType = keyType;
        if (keyType == KeyType.doubled || keyType == KeyType.repeat) {
            double1 = new Key(KeyType.unknown);
            double2 = new Key(KeyType.unknown);
            repeat = new Key(KeyType.unknown);
        }
    }*/

    public Key() {
        keyType = KeyType.unknown;
        repeatN = -1;
    }

    public int getRepeatN() {
        return repeatN;
    }

    public void setRepeatN(int repeatN) {
        this.repeatN = repeatN;
    }

    public Key getDouble1() {
        return double1;
    }

    public Key getDouble2() {
        return double2;
    }

    public int getValue() {
        return value;
    }

    protected Object clone()
            throws CloneNotSupportedException {
        return super.clone();
    }

    public void makeKey(KeyType keyType) {
        if (this.keyType == KeyType.unknown) {
            this.keyType = keyType;
            if (keyType == KeyType.single) {
                Random r = new Random();
                value = r.nextInt(2) + 1;
            } else {
                double1 = new Key();
                double2 = new Key();
                repeat = new Key();
            }
        }
    }

    public String toString() {
        if (repeatN != -1) {
            Key key = null;
            try {
                key = (Key) this.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            key.repeatN = -1;
            String total = key.toString();
            String base = key.toString();
            for (int i = 0; i < repeatN - 1; i++)
                total += "," + base;
            return total;
        }
        if (keyType == keyType.single)
            return String.valueOf(value);
        if (keyType == keyType.doubled)
            return double1 + "," + double2;
        return "something went wrong";
    }
}
