package main.java;

import java.io.*;
import java.math.BigInteger;




public class Encryptor {

    private final String key;

    public Encryptor(String key) {
        this.key = key;
    }

    /* одинаковый алгоритм для шифрования и дешифрования. Заданная по умолчанию кодировка UTF-8. */
    private void encrypt(InputStream in, OutputStream out) throws IOException {
        try (DataInputStream input = new DataInputStream(in)) {
            try (DataOutputStream output = new DataOutputStream(out)) {
                BigInteger keyToInt = new BigInteger(key, 16);
                byte[] keyToBytes = keyToInt.toByteArray();
                int sym = input.readByte();
                int count = 0;
                while (sym != -1) {
                    output.writeByte(sym ^ keyToBytes[count % keyToBytes.length]);
                    count++;
                    if (input.available() > 0) sym = input.readByte();
                    else break;
                }
            }
        }
    }


    public void encrypt(String inputName, String outputName) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(inputName)) {
            try (FileOutputStream outputStream = new FileOutputStream(outputName)) {
                encrypt(inputStream,outputStream);
            }
        }
    }



    public void decrypt(String inputName, String outputName) throws IOException {
        encrypt(inputName,outputName);
    }
}