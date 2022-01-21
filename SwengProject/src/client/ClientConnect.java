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

    public void sendTestMessage(){
        outBuff.println("test");
    }


    public String sendMessage(String msg) throws IOException {
        outBuff.println(msg);
        return inBuff.readLine();
    }




}
