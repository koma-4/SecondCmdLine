package main.java;

import java.io.*;
import java.math.BigInteger;

public class Encryptor {

    private final String key;

    public Encryptor(String key) {
        this.key = key;
    }

    /* одинаковый алгоритм для шифрования и дешифрования */
    public int encrypt(InputStream in, OutputStream out) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(in)) {
            try (OutputStreamWriter writer = new OutputStreamWriter(out)) {
                BigInteger keyToInt = BigInteger.valueOf(Integer.decode(key));
                byte[] keyToBytes = keyToInt.toByteArray();
                int sym = reader.read();
                int count = 0;
                while (sym != -1) {
                    writer.write(sym ^ keyToBytes[count % keyToBytes.length]);
                    count++;
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
}
