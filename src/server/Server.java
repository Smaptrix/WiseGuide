package server;

import java.net.*;
import java.io.*;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter outBuff;
    private BufferedReader inBuff;
    private int port;


    //Hardcoded routes in array
    private String[] romanRaidMarsRoute = {"Courtyard", "Lowther", "Stone Roses", "Yates", "Salvation"};
    private String[] romanRaidVenusRoute = {"The Vanbrugh Arms", "Revolution", "Yates", "Flares", "Salvation"};


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
    As soon as the connection from the client terminates, error!!!
    TODO - HANDLE IOEXCEPTION
 */

        try{

            while ((inputLine = inBuff.readLine()) != null) {
                System.out.println("Listening...");
                if ("Close Connection".equals(inputLine)) {
                    outBuff.println("Connection Closed");
                    stopConnections();
                    break;
                } else {
                    System.out.println("Request Received: " + inputLine);

                    requestParser(inputLine);

                    //For now just echos the received message, will be passed through another function when it is written
                    //outBuff.println(inputLine);

            }

        }

        }catch (SocketException e){
            System.out.println("Socket Closed (Client may have closed!)");
        }
    }

    //Requests in form "Request Code Type" + " " + "Request Information"
    public void requestParser(String requestIn) {
        String[] requestSplit = requestIn.split(" ");
        switch(requestSplit[0]) {
            case "Text":
                textRequest(requestSplit[1]);
                break;
            default:
                outBuff.println("Error 404: Text Request Code Not Found");
                break;
        }
    }

    //Gathers and sends the requested information to the client
    public void textRequest(String requestInfo) {
        switch(requestInfo) {
            case "romanRaidMarsRoute":
                outBuff.println(romanRaidMarsRoute.toString());
                break;
            case "romanRaidVenusRoute":
                outBuff.println(romanRaidVenusRoute.toString());
                break;
            default:
                outBuff.println("Error 404: Text Request Information Not Found");
        }
    }

}