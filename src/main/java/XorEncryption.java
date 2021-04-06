public class XorEncryption implements BasicEncryption {

    public String decryptFile(String data, String keys) {
        int i = 0;
        int comma = keys.substring(i).indexOf(',');
        int currentKey;
        while (comma != -1) {
            currentKey = Integer.parseInt(keys.substring(i, comma));
            data = this.encryptDecrypt(data, currentKey);
            i = comma + 1;
            comma = keys.substring(i).indexOf(',');
            if (comma!=-1)
                comma+=i;
        }
        currentKey = Integer.parseInt(keys.substring(i));
        data = this.encryptDecrypt(data, currentKey);
        return data;
    }

    public String encryptFile(String data, Key key) {
        key.makeKey(Key.KeyType.single);
        int keyVal = key.getValue();
        return encryptDecrypt(data, keyVal);
    }

    private String encryptDecrypt(String data, int key) {
        String binaryKey = Integer.toBinaryString(key);
        binaryKey = String.format("%7s", binaryKey).replaceAll(" ", "0");  // 7-bit Integer
        StringBuilder encryption = new StringBuilder(data);
        for (int index = 0; index < data.length(); index++) {
            int currCh = data.charAt(index);
            String binaryCurrCh = Integer.toBinaryString(currCh);
            binaryCurrCh = String.format("%7s", binaryCurrCh).replaceAll(" ", "0");  // 7-bit Integer
            StringBuilder ch = new StringBuilder(binaryCurrCh);
            for (int binIndex = 0; binIndex < 7; binIndex++) {
                if (binaryCurrCh.charAt(binIndex) == binaryKey.charAt(binIndex))
                    ch.setCharAt(binIndex, '0');
                else
                    ch.setCharAt(binIndex, '1');
            }
            encryption.setCharAt(index, (char) Integer.parseInt(ch.toString(), 2));
        }
        return encryption.toString();
    }

    public EncryptionAlgorithm getBase() {
        return this;
    }
}
