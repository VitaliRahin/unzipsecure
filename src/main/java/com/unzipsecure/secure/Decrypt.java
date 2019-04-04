package com.unzipsecure.secure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import static com.unzipsecure.errors.ErrorMessages.EXCEPTION_DECRYPT_PASS;
import static com.unzipsecure.utils.DecryptUtils.decryptByteText;
import static com.unzipsecure.utils.DecryptUtils.readPrivateKey;
import static com.unzipsecure.utils.FileUtils.readFileToBytes;


public class Decrypt {
    private static final Logger LOGGER = LoggerFactory.getLogger(Decrypt.class);
    private static Decrypt decrypt;
    private String keyNamePath;
    private PrivateKey privateKey;

    private Decrypt(String keyNamePath){
        this.keyNamePath = keyNamePath;
    }

    public static Decrypt getInstance(String keyNamePath){
        if (decrypt != null && keyNamePath.equals(decrypt.keyNamePath)) {
            return decrypt;
        } else {
            decrypt = new Decrypt(keyNamePath);
            decrypt.readPrivateKeyToInstance();
            return decrypt;
        }
    }

    public char[] decryptPassword(String passPath){
        char[] decryptedPass = null;
        try {
            byte[] secretPass = readFileToBytes(passPath);
            byte[] recovered_message = decryptByteText(privateKey, secretPass);
            decryptedPass = bytesToChars(recovered_message);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | IllegalBlockSizeException | BadPaddingException | IOException e) {
            LOGGER.error(EXCEPTION_DECRYPT_PASS);
            e.printStackTrace();
        }
        return decryptedPass;
    }

    private void readPrivateKeyToInstance(){
        try {
            privateKey = readPrivateKey(keyNamePath);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.error(EXCEPTION_DECRYPT_PASS);
            e.printStackTrace();
        }
    }

    public char[] bytesToChars(byte[] bytes){
        Charset charset = Charset.forName("UTF-8");
        CharBuffer charBuffer = charset.decode(ByteBuffer.wrap(bytes));
        return Arrays.copyOf(charBuffer.array(), charBuffer.limit());
    }
}
