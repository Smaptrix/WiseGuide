/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   08/02/2022
    Last Updated:   18/02/2022
 */

package serverclientstuff;


//TODO - Only Hash once - Stop Hashing every time new user is made

//Object regarding User functions and User creation

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

    private String username;
    private String password;
    //This is safe because we can assume that only the user has access to the client at any one time
    //If the user is logged into the client, that means they know there password.
    private String unencryptedPass;


    //Creates the user object
    public User(String username, String password)  {

        this.username = username;
        this.password = password;
        unencryptedPass = password;


    }








    //Hashes user data
    public void encryptUserInfo()  {

/*
        //Encrypt username
        try {

            UserSecurity userSecurity =  new UserSecurity(this);

            this.username = userSecurity.encryptUsername();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to encrypt username");
        }

 */



        //Hash the password because we never want to undo it
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert digest != null;

        byte[] hashPass = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        this.password = Utils.bytesToHex(hashPass);







    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", encodedPass='" + password + '\'' +
                '}';
    }


    public void clear(){
        username = "";
        password = "";

    }
}