package serverclientstuff;


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

//A class that provides the encryption/decryption tools required by the client to make sure that the users data
//is secure before it is sent to the server
public class UserSecturity {

    User currUser;



    public UserSecturity(User currUser){

        this.currUser = currUser;



    }


    /**
     * Encrypts a string
      * @param string The string you want to encrypt
     * @return the hex value of the encrypted string
     */
    public String encrypt(String  string) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {

        //Turns the string into bytes for the encryption
        byte[] input = string.getBytes(StandardCharsets.UTF_8);

        //Bytes to store the key and the IV
        byte[] keyBytes = new byte[0];
        byte[] ivBytes = new byte[0];

        //Create the cipher
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");

        //Generate the key and initialisation vector
        SecretKeySpec key = new SecretKeySpec(keyBytes, "CTR");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        byte[] encrypted = new byte[cipher.getOutputSize(input.length)];

        int enc_len = cipher.update(input, 0, input.length, encrypted, 0);

        enc_len += cipher.doFinal(encrypted, enc_len);






        String encryptedString = Utils.bytesToHex(encrypted);

        return encryptedString;

    }






}
