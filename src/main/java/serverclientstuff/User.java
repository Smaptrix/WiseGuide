package serverclientstuff;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


//Object regarding User functions and User creation

public class User {

    private String encodedUsername;
    private String encodedPass;


    //Creates the user object - stores all information as a hash
    public User(String username, String password) throws NoSuchAlgorithmException {


        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashUsername = digest.digest(username.getBytes(StandardCharsets.UTF_8));
        byte[] hashPass = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        this.encodedUsername = Utils.bytesToHex(hashUsername);
        this.encodedPass = Utils.bytesToHex(hashPass);
    }

    public String getEncodedUsername() {
        return encodedUsername;
    }

    public String getEncodedPass() {
        return encodedPass;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + encodedUsername + '\'' +
                ", encodedPass='" + encodedPass + '\'' +
                '}';
    }
}
