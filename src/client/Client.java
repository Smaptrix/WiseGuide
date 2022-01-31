package client;

import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Dictionary;
import java.util.Hashtable;

public class Client {
    private Socket clientSocket;
    //Only need a printWriter as we won't be sending files back to the server, just text requests
    private PrintWriter outText;
    //BufferedReader to read text
    private BufferedReader inText;
    //InputStream to read files
    private InputStream inFile;

    //TODO CREATE DICITONARY HOLDING FILENAMES AND LOCATIONS
    Dictionary fileLocs = new Hashtable();


    //Connects to the port
    public void startConnection(String ip, int port) throws IOException {

       try {
           clientSocket = new Socket(ip, port);
           outText = new PrintWriter(clientSocket.getOutputStream(), true);
           inText = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
       }catch(ConnectException e){
           System.out.println("Failed to connect/Server Offline");
       }

        //Finds the temp directory on the system - No prefix because we will save
        Path tmpFolderDir = Files.createTempDirectory(null);


    }


    public void stopConnection() throws IOException {
        inText.close();
        outText.close();
        clientSocket.close();
        System.out.println("Connection Closed");
    }

    public String sendTestMessage() throws IOException {

            outText.println("test");

            return inText.readLine();

    }


    public String sendMessage(String msg) throws IOException {
        outText.println(msg);

        String Response = inText.readLine();

        System.out.println(Response);
        return Response;
    }


    //The client should request specific files from the server so we should know the name of the files
    //The filenames should be stored in the XML
    public void receiveFile(String fileName) throws IOException {

        outText.println("GET: " + fileName);

        inFile = new DataInputStream(clientSocket.getInputStream());


        //Reads the inputstream and stores it as an array of bytes
        byte[] data = inFile.readAllBytes();

        inFile.close();

        //Once we have the array of bytes, we then reconstruct that into the actual file.
        BytesToFile(data, fileName);

    }


    //
    public File BytesToFile(byte[] data, String fileName) throws IOException {

        //Creates a new temp file - Identifiable by custom prefix
        File currFile = new File(String.valueOf(Files.createTempFile("WG_", null)));

         //Creates a temp file out of the data recieved, so that when the program closes the data isnt saved
         FileOutputStream os = new FileOutputStream(currFile);


         fileLocs.put(fileName, currFile);


         os.close();

         return currFile;

    }


}
