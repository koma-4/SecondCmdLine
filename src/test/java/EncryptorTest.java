package test.java;

import main.java.Encryptor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class EncryptorTest {
    private void assertFileContent(String name, String expectedContent) throws IOException {
        String content = Files.lines(Path.of(name)).reduce("",String::concat);
        assertEquals(expectedContent, content);
    }

    @Test
    void testEncrypt() throws IOException {
        Encryptor encryptor = new Encryptor("0x1F");
        encryptor.encrypt("src/files/text1.txt", "src/files/text1.enc.txt");
        assertFileContent("src/files/text1.enc.txt","^]\\~}|-,");
    }

    @Test
    void testDecrypt() throws IOException {
        Encryptor encryptor = new Encryptor("0x1F");
        encryptor.encrypt("src/files/text1.enc.txt", "src/files/text1.txt");
        assertFileContent("src/files/text1.txt","ABCabc23");
    }
}