package client;

import java.awt.*;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
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


    public Dictionary fileLocs = new Hashtable();


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

            outText.println("ECHO " + "test");

            return inText.readLine();

    }


    public String echoMessage(String msg) throws IOException {
        outText.println("ECHO " + msg);

        String Response = inText.readLine();

        outText.flush();

        System.out.println(Response);
        return Response;
    }





    //The client should request specific files from the server so we should know the name of the files
    //The filenames should be stored in the XML
    public void requestFile(String fileName) throws IOException {

        outText.println("GET " + fileName);

        inFile = clientSocket.getInputStream();


        int bytesToRead = inFile.read();
        System.out.println("We have " + bytesToRead + " bytes to read");

        String dataType = inText.readLine();
        System.out.println("The file we are receiving is a: " + dataType + " file");

        boolean end = false;
        int bytesRead = 0;

        //Initialises a new byte array of size predetermined by our network protocol
        byte[] data = new byte[bytesToRead];

        /*TODO Determine why we are getting ÿ in our text file
                Where is the issue? (Client side or Server Side)
                Are we just reading the bytes in the text file rather than the actual text?
                IT WORKS SOMETIMES?? CONFUSING
          */


        //Reads bytes up until the count has been reached
        while(!end) {

            data[bytesRead] = (byte) inFile.read();
            System.out.println(data[bytesRead]);
            //Increment Byte count
            bytesRead += 1;
            if(bytesRead == bytesToRead){
                System.out.println("We have read: " + bytesRead);
                end = true;
            }
        }

        //Closes the inputStream
        inFile.close();

        //Once we have the array of bytes, we then reconstruct that into the actual file.
        BytesToFile(data, fileName, dataType);


    }


    //
    public File BytesToFile(byte[] data, String fileName, String fileType) throws IOException {

        //Creates a new temp file - Identifiable by custom prefix
        File currFile = new File(String.valueOf(Files.createTempFile("WG_", "." + fileType)));


         //Creates a temp file out of the data received, so that when the program closes the data isnt saved
         FileOutputStream os = new FileOutputStream(currFile);

         os.write(data);

         fileLocs.put(fileName, currFile);


         os.close();

         //Saves file in temp positition
         System.out.println("File saved at: " + currFile);


         return currFile;

    }



    public void openFile(File file) throws IOException {


        Desktop.getDesktop().open(file);

    }


}
