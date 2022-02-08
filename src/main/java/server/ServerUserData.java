package server;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//This is a class which takes in user data and access/modifies the user database file
public class ServerUserData {

        private String username;
        //Gets hashed straight away
        private byte[] encodedpass;


        //Construct a UserData object and instantly hash the password for security.
        public ServerUserData(String username, String password) throws NoSuchAlgorithmException {
            this.username = username;

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            this.encodedpass = digest.digest(password.getBytes(StandardCharsets.UTF_8));
    }



}
