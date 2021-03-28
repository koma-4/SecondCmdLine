package main.java;

import java.io.*;
import java.math.BigInteger;

public class Encryptor {

    private final String key;

    public Encryptor(String key) {
        this.key = key;
    }

    /* одинаковый алгоритм для шифрования и дешифрования */
    private int encrypt(InputStream in, OutputStream out) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(in)) {
            try (OutputStreamWriter writer = new OutputStreamWriter(out)) {
                BigInteger keyToInt = new BigInteger(key);
                byte[] keyToBytes = keyToInt.toByteArray();
                int sym = reader.read();
                int extraSym = 0;
                int k = 0;
                int count = 0;
                while (sym != -1) {
                    if (sym>255) {
                        sym = sym / 256;
                        extraSym = sym % 256;
                        k++;
                    }
                    writer.write(sym ^ keyToBytes[count % keyToBytes.length]);
                    count++;
                    if (k == 1) {
                        writer.write(extraSym ^ keyToBytes[count % keyToBytes.length]);
                        k = 0;
                        count++;
                    }
                    sym = reader.read();
                }
                return count;

            }
        }
    }

    public int encrypt(String inputName, String outputName) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(inputName)) {
            try (FileOutputStream outputStream = new FileOutputStream(outputName)) {
                return encrypt(inputStream, outputStream);
            }
        }
    }

    public int decrypt(String inputName, String outputName) throws IOException {
        return encrypt(inputName, outputName);
    }
}
