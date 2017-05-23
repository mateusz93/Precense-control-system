package util;

import neo.dmcs.util.Encryptor;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import static org.junit.Assert.assertTrue;


/**
 * @Author Mateusz Wieczorek, 03.04.16.
 */
public class EncryptionTest {

    @Test
    public void shouldReturnTrueIfPasswordIsCorrectEncrypted() {
        String password1 = "Hello World";
        String password2 = "Hello World";
        assertTrue(Encryptor.encryption(password1).equals(Encryptor.encryption(password2)));
    }
}
