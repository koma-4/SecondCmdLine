package main.java;

import java.io.*;

public class Encryptor {

    private final String key;

    public Encryptor(String key) {
        this.key = key;
    }

    /* одинаковый алгоритм для шифрования и дешифрования */
    public int encrypt(InputStream in, OutputStream out) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(in)) {
            try (OutputStreamWriter writer = new OutputStreamWriter(out)) {
                int keyToInt = Integer.decode(key);
                int sym = reader.read();
                int count = 0;
                while (sym != -1) {
                    writer.write(sym ^ keyToInt);
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
