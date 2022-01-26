package server;

import java.net.*;
import java.io.*;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter outBuff;
    private BufferedReader inBuff;
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

        //Writes to the buffer
        outBuff = new PrintWriter(clientSocket.getOutputStream(), true);

        //Reads from the buffer
        inBuff = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String acceptanceMsg = "Server now accepting connections";

        //Used for Unit Testing
        return acceptanceMsg;
    }


    //Closes the server down
    public void stopConnections() throws IOException {
        inBuff.close();
        outBuff.close();
        clientSocket.close();
        serverSocket.close();
    }


    //Contentiously listens to the buffer
    public void bufferListen() throws IOException {



        String inputLine;
/*
    Reads the buffer once then connection reset???? Problem on client end?? or server end??
    who knows...

 */



        while ((inputLine = inBuff.readLine()) != null) {
            System.out.println("Listening...");
            if ("Close Connection".equals(inputLine)) {
                outBuff.println("Connection Closed");
                stopConnections();
                break;
            } else {
                System.out.println("Heard!");

                //For now just echos the recieved message, will be passed through another function when it is written
                outBuff.println(inputLine);

            }


        }
    }
}

//git check ~ ben