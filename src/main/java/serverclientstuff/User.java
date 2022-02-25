package serverclientstuff;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


//TODO - Only Hash once - Stop Hashing every time new user is made

//Object regarding User functions and User creation

public class User {

    private String username;
    private String password;


    //Creates the user object
    public User(String username, String password) throws NoSuchAlgorithmException {

        this.username = username;
        this.password = password;


    }

    //Hashes user data
    public void hashUserInfo() throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashUsername = digest.digest(username.getBytes(StandardCharsets.UTF_8));
        byte[] hashPass = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        this.username = Utils.bytesToHex(hashUsername);
        this.password = Utils.bytesToHex(hashPass);

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