/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   08/02/2022
    Last Updated:   04/06/2022
 */



package ServerClientUtility;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * <p>
 *     This class provides generic utility tools that can be used by either the server or the client
 *     Most of these can be accessed statically because a lot of them don't relate to eachother
 *     This is a miscellaneous tool set
 * </p>
 */
public class Utils {


    /**
     * Opens the given file on the system
     * @param file the file to be opened
     * @throws IOException if the file cannot be accessed
     */
    public static void openFile(File file) throws IOException {

        Desktop.getDesktop().open(file);

    }


    /**
     * <p>Converts a given byte array into hex format</p>
     * @param byteArray the byte array to be transformed
     * @return The hex string
     */
    public static String bytesToHex(byte[] byteArray){
        StringBuilder hexString = new StringBuilder( 2 * byteArray.length);
       //Goes through the bytes and converts it to a hex
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
