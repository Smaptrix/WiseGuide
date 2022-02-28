/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   08/02/2022
    Last Updated:   18/02/2022
 */

package serverclientstuff;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


//TODO - Encrypt the users data using a different method, let the serverhandler decrypt it


//Object regarding User functions and User creation

public class User {

    private String username;
    private String password;



    //Creates the user object - encrypts data, and hashes the password
    public User(String username, String password) throws NoSuchAlgorithmException {

        try {
            encryptUsername(username);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }


        hashUserPass(password);



    }

    //Hashes user password - as we dont want this to be readable by anyone
    public void hashUserPass(String password) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashPass = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        this.password = Utils.bytesToHex(hashPass);

    }

    //Encrypts Users username - INCREDIBLY INSECURE JUST A PLACE HOLDER WHILE WE DO OTHER BITS


    public void encryptUsername(String username) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        //This is very poor practice - never hardcode your key
        //Only doing it now just for proof of concept
        byte[] keyBytes = new byte[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        String algorithm = "Thereisnowayanyonewillguessthiskey";
        SecretKeySpec key = new SecretKeySpec(keyBytes, algorithm);

        //Sets the cipher to encrypt mode
        cipher.init(Cipher.ENCRYPT_MODE, key);

        //Turns the strings into bytes and then encrypts them
        byte[] userPlainText = username.getBytes(StandardCharsets.UTF_8);

        byte[] userEncrypted = cipher.doFinal(userPlainText);


        //Turns the encrypted bytes into hex strings so that they can be decoded etc.
        this.username = Utils.bytesToHex(userEncrypted);




    }













    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", encodedPass='" + password + '\'' +
                '}';
    }
}
