package Keys;

public abstract class Key {

    public abstract String toString();

    protected Object clone()
            throws CloneNotSupportedException {
        return super.clone();
    }

    public abstract int getKeyStrength();

    public abstract String getType();

    public abstract void getNextKey();
}