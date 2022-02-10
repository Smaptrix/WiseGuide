/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   27/01/2022
    Last Updated:   10/02/2022
 */
package client;

import java.awt.*;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Dictionary;
import java.util.Hashtable;


public class Client {


    private Socket clientSocket;
    //Only need a printWriter as we won't be sending files back to the server, just text requests
    private PrintWriter outText;
    //InputStream to read files
    private InputStream inputStream;


    //Each client will keep a dictionary containing where all there files are located
    public Dictionary<String, File> fileLocations = new Hashtable<>();


    //Connects to the port
    public void startConnection(String ip, int port) throws IOException {

        try {
            clientSocket = new Socket(ip, port);
            outText = new PrintWriter(clientSocket.getOutputStream(), true);
            inputStream = clientSocket.getInputStream();
        } catch (ConnectException e) {
            System.out.println("Failed to connect/Server Offline");
        }

    }

    //Closes the clients connections
    public void closeConnection() throws IOException {

            outText.println("Close Connection");
            inputStream.close();
            outText.close();
            clientSocket.close();
            System.out.println("Connection Closed");

    }

    //Sends a test message to the server
    public String sendTestMessage() throws IOException {


            outText.println("ECHO " + "test");

            int stringSize = inputStream.read();

            byte[] data = readBytes(stringSize);


            String result = new String(data, StandardCharsets.UTF_8);

            System.out.println(result);
            return result;
        }




    //Sends an ECHO request to the server and waits for its response
    public String echoMessage(String msg) throws IOException {

        System.out.println("ECHO REQUEST: " + msg);

        outText.println("ECHO " + msg);

        int fileSize = inputStream.read();

        System.out.println("We are receiving: " + fileSize + " bytes");
        byte[] data = readBytes(fileSize);


        String result = new String(data, StandardCharsets.UTF_8);

        System.out.println(result);

        return result;

    }

    //Requests a file from the server
    //The client should request specific files from the server, so we should know the name of the files
    //The filenames should be stored in the XML
    public File requestFile(String fileName) throws IOException {

        System.out.println("GET REQUEST: " + fileName);

        outText.println("GET " + fileName);

        //Tells us how many bytes are telling us how big the file is
        int numOfFileSizeBytes = inputStream.read();

        System.out.println("We have " + numOfFileSizeBytes + " file size bytes to read");

        //Reads the next set amount of bytes to decode the file size
        byte[] bytesToReadBytes = new byte[numOfFileSizeBytes];

        for(int i = 0; i<numOfFileSizeBytes; i++){
            bytesToReadBytes[i] = (byte) inputStream.read();
        }

        int bytesToRead = ByteBuffer.wrap(bytesToReadBytes).getInt();


        //Magic number 3 - because we know that the file extension is only going to be three letters
        byte[] DataTypeBytes = new byte[3];

        for(int i = 0; i < 3; i++){
            DataTypeBytes[i] = (byte) inputStream.read();
        }

        String dataType = new String(DataTypeBytes, StandardCharsets.UTF_8);

        System.out.println(dataType);


        byte[] data = readBytes(bytesToRead);


        System.out.println("The file is a : " + dataType + " file and it is : " + bytesToRead + " long.");

        //Once we have the array of bytes, we then reconstruct that into the actual file.
       File currFile = BytesToFile(data, fileName, dataType);

       return currFile;
    }

    //Reads the bytes for the file from the inputStream
    public byte[] readBytes(int bytesToRead) throws IOException {

        //Initialises a new byte array of size predetermined by our network protocol
        byte[] data = new byte[bytesToRead];

        boolean end = false;
        int bytesRead = 0;


        //Reads bytes up until the count has been reached
        while (!end) {

            data[bytesRead] = (byte) inputStream.read();

            //Increment Byte count
            bytesRead += 1;
            if (bytesRead == bytesToRead) {
                System.out.println("We have read: " + bytesRead);
                end = true;
            }

        }
        return data;
    }

    //Turns the raw bytes into a file and saves it in the default temp folder on the system
    public File BytesToFile(byte[] data, String fileName, String fileType) throws IOException {

        //Creates a new temp file - Identifiable by custom prefix
        File currFile = new File(String.valueOf(Files.createTempFile("WG_", "." + fileType)));


        //Creates a temp file out of the data received, so that when the program closes the data isn't saved
        FileOutputStream os = new FileOutputStream(currFile);

        os.write(data);

        fileLocations.put(fileName, currFile);

        //Schedules the file to be deleted when the application closes
        currFile.deleteOnExit();

        os.close();

        //Saves file in temp position
        System.out.println("File saved at: " + currFile);

        return currFile;

    }



    //Opens a file in the system - MIGHT BE REDUNDANT SOON
    public void openFile(File file) throws IOException {

        Desktop.getDesktop().open(file);

    }




}
