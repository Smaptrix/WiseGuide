package serverclientstuff;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


//Object regarding User functions and User creation

public class User {

    private String username;
    private String encodedPass;


    public User(String username, String password) throws NoSuchAlgorithmException {
        this.username = username;

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        this.encodedPass = Utils.bytesToHex(hash);
    }

    public String getUsername() {
        return username;
    }

    public String getEncodedPass() {
        return encodedPass;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", encodedPass='" + encodedPass + '\'' +
                '}';
    }
}
