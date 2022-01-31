package server;

import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private OutputStream outFile;
    private PrintWriter outText;
    private BufferedReader inText;
    private int port;

    //Starts the server
    public String startup(int port) throws IOException {

        this.port = port;

        System.out.println("Creating new Server Socket at " + port);

        //Server formed
        serverSocket = new ServerSocket(port);

        System.out.println("Port Created\n");

        clientSocket = serverSocket.accept();

        System.out.println("After accept\n");


        outFile = clientSocket.getOutputStream();

        //Writes text to the buffer
        outText = new PrintWriter(clientSocket.getOutputStream(), true);


        //Reads text from the buffer
        inText = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        //Used for Unit Testing
        return "Server now accepting connections";
    }


    //Closes the server down
    public void stopConnections() throws IOException {
        inText.close();
        outText.close();
        clientSocket.close();
        serverSocket.close();
    }


    //Contentiously listens to the buffer
    public void bufferListen() throws IOException {

        String inputLine;

        try{

            while ((inputLine = inText.readLine()) != null) {
                System.out.println("Listening...");
                if ("Close Connection".equals(inputLine)) {
                    outText.println("Connection Closed");
                    stopConnections();
                    break;
                } else {
                    System.out.println("Request Recived: " + inputLine);

                    //For now just echos the recieved message, will be passed through another function when it is written
                    outText.println(inputLine);

            }

        }

        }catch (SocketException e){
            System.out.println("Socket Closed (Client may have closed!)");
        }
    }


    // ToDo - Test this actually works!!!! Need to make an example file then reconstruct client side

    //Sends a file across the socket (after it has been broken down into its bytes)
    public void sendFile(Path filepath) throws IOException {

        //Copies the file into the outputStream
        Files.copy(filepath, outFile);
        //Clears the outputStream of any excess data
        outFile.flush();


    }







}