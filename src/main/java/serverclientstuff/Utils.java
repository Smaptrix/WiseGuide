package serverclientstuff;

import java.awt.*;
import java.io.File;
import java.io.IOException;

//A class containing some utility stuff
public class Utils {



    //Opens a file in the system - MIGHT BE REDUNDANT SOON
    public static void openFile(File file) throws IOException {

        Desktop.getDesktop().open(file);

    }


    //Converts a hash (SHA-256) into hex for password storage
    public static String bytesToHex(byte[] hash){
        StringBuilder hexString = new StringBuilder( 2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }



}
