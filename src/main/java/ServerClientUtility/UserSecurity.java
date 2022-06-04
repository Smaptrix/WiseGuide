/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   14/03/2022
    Last Updated:   04/06/2022
 */


package ServerClientUtility;


import java.nio.charset.StandardCharsets;
import java.security.*;

/**
 * <p>Provides the user class with tools to encrypt the password</p>
 */
public class UserSecurity {

    /**
     * <p>The currently logged in user</p>
     */
    User currUser;

    /**
     * <p>The constructor for this class</p>
     * @param currUser the currently logged in user
     */
    public UserSecurity(User currUser)  {

        this.currUser = currUser;
    }

    /**
     * <p>Hashes the current users password</p>
      * @return the hashed password
     */
    public String hashPassword(){

        //Hash the password because we never want to undo it
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert digest != null;

        String saltedPass = currUser.getPassword() + currUser.getSalt();


        byte[] hashPass = digest.digest(saltedPass.getBytes(StandardCharsets.UTF_8));


        return Utils.bytesToHex(hashPass);
    }


    /**
     * <p>Generates the salt for the users password</p>
     * @return the generated salt
     */
    public static String generateSalt(){

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return Utils.bytesToHex(salt);

    }


    /**
     * <p>Hashes a given password and salt combined</p>
     * @param pass the users password
     * @param salt the users salt
     * @return the hash result
     */
    public static String hashThis(String pass, String salt) {

        //Hash the password because we never want to undo it
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert digest != null;

        String saltedPass = pass + salt;

        byte[] hashPass = digest.digest(saltedPass.getBytes(StandardCharsets.UTF_8));


        return Utils.bytesToHex(hashPass);
    }

}
