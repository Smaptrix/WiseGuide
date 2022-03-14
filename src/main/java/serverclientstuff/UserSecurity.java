package serverclientstuff;


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


//TODO - Password based key derivation ?
//TODO - Salt to slow down dictionary attacks


//A class that provides the encryption/decryption tools required by the client to make sure that the users data
//is secure before it is sent to the server
public class UserSecurity {

    User currUser;



    public UserSecurity(User currUser){

        this.currUser = currUser;

    }


    /**
     * Encrypts a string
      * @param string The string you want to encrypt
     * @return the hex value of the encrypted string
     */
    public void  encrypt() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {

        /*
        //Turns the string into bytes for the encryption
        byte[] input = string.getBytes(StandardCharsets.UTF_8);


        //Create the cipher
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");

        //Bytes to store the key and the IV
        byte[] keyBytes = new byte[256];
        byte[] ivBytes= new byte[cipher.getBlockSize()];


        //Create a key generator
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);


        //Generate the key and initialisation vector
        SecretKey key = keyGen.generateKey();
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        //Pass the key and iv to the cipher
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        byte[] encrypted = new byte[cipher.getOutputSize(input.length)];

        int enc_len = cipher.update(input, 0, input.length, encrypted, 0);

        enc_len += cipher.doFinal(encrypted, enc_len);


        */







        String encryptedString = Utils.bytesToHex(encrypted);

        System.out.println(encryptedString);

        return encryptedString;

    }






}
