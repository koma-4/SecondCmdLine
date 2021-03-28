package main.java;

import org.kohsuke.args4j.*;

import java.io.IOException;
public class EncryptorLauncher {
    @Option(name = "-c", metaVar = "Encryption", usage = "Key to encrypt")
    private String keyToEncrypt;

    @Option(name = "-d", metaVar = "Decryption", usage = "Key to decrypt")
    private String keyToDecrypt;

    @Option(name = "-o", metaVar = "OutputName", required = true, usage = "Output file name")
    private String outputFileName;

    @Argument(required = true, metaVar = "InputName", usage = "Input file name")
    private String inputFileName;


    public static void main(String[] args) {
        if (args == null) throw new IllegalArgumentException("Illegal arguments");
        new EncryptorLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        String key;
        try {
            parser.parseArgument(args);
            if ((keyToEncrypt == null && keyToDecrypt == null)
                    || (keyToEncrypt != null && keyToDecrypt != null) ||
                    (keyToDecrypt != null && !keyToDecrypt.matches("^0[xX][0-9a-fA-F]+")) ||
                    (keyToEncrypt != null && !keyToEncrypt.matches("^0[xX][0-9a-fA-F]+"))) {
                System.err.println("Error entering arguments");
                throw new IllegalArgumentException("Illegal arguments");
            }
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java - jar ciphxor -c Encryption InputName -o OutputName " +
                    "or java - jar ciphxor -d Decryption InputName -o OutputName");
            parser.printUsage(System.err);
            return;
        }
        if (keyToDecrypt == null) key = keyToEncrypt;
        else key = keyToDecrypt;
        Encryptor encryptor = new Encryptor(key);
        try {
            encryptor.encrypt(inputFileName, outputFileName);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
