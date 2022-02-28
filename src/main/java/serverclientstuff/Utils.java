/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   08/02/2022
    Last Updated:   10/02/2022
 */



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


    //Converts a bytes into a hex string
    public static String bytesToHex(byte[] byteArray){
        StringBuilder hexString = new StringBuilder( 2 * byteArray.length);
        for (byte b : byteArray) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }





}
