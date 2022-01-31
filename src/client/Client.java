package client;

import java.net.*;
import java.io.*;

public class Client {
    private Socket clientSocket;
    //Only need a printWriter as we won't be sending files back to the server, just text requests
    private PrintWriter outText;
    //BufferedReader to read text
    private BufferedReader inText;
    //InputStream to read files
    private InputStream inFile;

    //Connects to the port
    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip,port);
        outText = new PrintWriter(clientSocket.getOutputStream(), true);
        inText = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
    public void receiveFile(String fileName) throws IOException {
        inFile = new FileInputStream(fileName);

        //Reads the inputstream and stores it as an array of bytes
        byte[] data = inFile.readAllBytes();

        inFile.close();

        //Once we have the array of bytes, we then reconstruct that into the actual file.

        // TODO - Convert the bytes into a file and then save locally



    }


}
