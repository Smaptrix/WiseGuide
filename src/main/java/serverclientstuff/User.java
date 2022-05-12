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
    //The users salt that is added to there password
    private String salt;

    private String[] faveVenues;

    //Creates the user object
    public User(String username, String password)  {

        this.username = username;
        this.password = password;



    }








    //Hashes user data
    public void encryptUserInfo()  {


        UserSecurity userSecurity =  new UserSecurity(this);

/*
        //Encrypt username
        try {
            this.username = userSecurity.encryptUsername();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to encrypt username");
        }
        */




        this.password = userSecurity.hashPassword();







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

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSalt(){
        return salt;
    }

    public void setFaveVenues(String[] faveVenues) {
        this.faveVenues = faveVenues;
    }

    public String[] getFaveVenues() {
        return faveVenues;
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