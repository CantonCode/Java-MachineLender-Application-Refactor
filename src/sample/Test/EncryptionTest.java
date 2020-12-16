package sample.Test;

import org.junit.jupiter.api.Test;
import sample.Authentication.Model.Encryption;
import sample.Authentication.Model.Decryption;
import static org.junit.jupiter.api.Assertions.*;

class EncryptionTest {
    private Encryption encryptionTest = new Encryption();
    private Decryption decryptionTest = new Decryption();
    private final String passwordTest = "Test123";
    private final String expected = "Test123";

    /*
        Tests if layer one encryption works as expected by encrypting and decrypting a test password
     */
    @Test
    public void passwordSecurityTest(){
        String encrypted = encryptionTest.layerOne(passwordTest);
        String actual = decryptionTest.layerOne(encrypted);
        assertEquals(expected, actual);
    }

    /*
        Tests if layer two encryption works as expected by encrypting and encrypting a test password
     */
    @Test
    public void passwordSecurityTest1(){
        String encrypted1 = encryptionTest.layerTwo(passwordTest);
        String actual1 = decryptionTest.layerTwo(encrypted1);
        assertEquals(expected, actual1);
    }
}