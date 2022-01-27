package client;

import java.net.*;
import java.io.*;

public class ClientConnect {
    private Socket clientSocket;
    private PrintWriter outBuff;
    private BufferedReader inBuff;

    //Connects to the port
    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip,port);
        outBuff = new PrintWriter(clientSocket.getOutputStream(), true);
        inBuff = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }


    public void stopConnection() throws IOException {
        inBuff.close();
        outBuff.close();
        clientSocket.close();
        System.out.println("Connection Closed");
    }

    public String sendTestMessage() throws IOException {

            outBuff.println("test");

            return inBuff.readLine();

    }


    public String sendMessage(String msg) throws IOException {
        outBuff.println(msg);

        String Response = inBuff.readLine();

        System.out.println(Response);
        return Response;
    }




}
