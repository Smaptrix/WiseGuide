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

}
