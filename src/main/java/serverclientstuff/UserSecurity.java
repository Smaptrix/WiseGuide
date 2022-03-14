package serverclientstuff;


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;


//TODO - Password based key derivation ?
//TODO - Salt to slow down dictionary attacks


//A class that provides the encryption/decryption tools required by the client to make sure that the users data
//is secure before it is sent to the server
public class UserSecurity {

    User currUser;

    //Store the password based keyspec
    PBEKeySpec keySpec;



    public UserSecurity(User currUser){

        this.currUser = currUser;

        //Turns the password into a character array

        int passLength = currUser.getPassword().length();

        char[] passinch = new char[passLength];

        for (int i = 0; i < passLength; i++){
            passinch[i] = currUser.getPassword().charAt(i);
        }


        keySpec = new PBEKeySpec(passinch);

        System.out.println("Keyspec: " + keySpec.getPassword());

    }


    /**
     * Encrypts the users username
     * @return the hex value of the encrypted string
     */
    public String encryptUsername() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {

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

        //Creating a salt
        Random r = new SecureRandom();
        byte[] salt = new byte[20];
        r.nextBytes(salt);




        //Determining the method required for the encryption
        String method = "PBEWithMD5AndTripleDES";
        //Create a keyfactory with the method
        SecretKeyFactory kf = SecretKeyFactory.getInstance(method);

        SecretKey key = kf.generateSecret(keySpec);

        Cipher cipher = Cipher.getInstance(method);

        cipher.init(Cipher.ENCRYPT_MODE, key);





        byte[] encrypted = cipher.doFinal(currUser.getUsername().getBytes(StandardCharsets.UTF_8));


        String encryptedString = Utils.bytesToHex(encrypted);

        System.out.println(encryptedString);

        return encryptedString;

    }






}
