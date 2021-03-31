package main.java;

import java.io.*;
import java.math.BigInteger;




public class Encryptor {

    private final String key;

    public Encryptor(String key) {
        this.key = key;
    }

    /* одинаковый алгоритм для шифрования и дешифрования. Заданная по умолчанию кодировка UTF-8. */
    private byte[] encrypt(InputStream in, OutputStream out) throws IOException {
        try (DataInputStream input = new DataInputStream(in)) {
            try (DataOutputStream output = new DataOutputStream(out)) {
                BigInteger keyToInt = new BigInteger(key, 16);
                byte[] keyToBytes = keyToInt.toByteArray();
                byte[] txt = input.readAllBytes();
                byte[] res = new byte[txt.length];
                for (int i = 0; i < txt.length; i++) {
                    res[i] = (byte) (txt[i] ^ keyToBytes[i % keyToBytes.length]);
                }
                output.write(res);
                return res;
            }
        }
    }


    public byte[] encrypt(String inputName, String outputName) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(inputName)) {
            try (FileOutputStream outputStream = new FileOutputStream(outputName)) {
                return encrypt(inputStream,outputStream);
            }
        }
    }



    public byte[] decrypt(String inputName, String outputName) throws IOException {
        return encrypt(inputName,outputName);
    }
}