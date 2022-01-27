package client;

import java.io.IOException;


public class ClientMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Client Starting\n");
        new ClientMain();
    }

    public ClientMain() throws IOException, InterruptedException {

        ClientConnect clientConnect = new ClientConnect();
        clientConnect.startConnection("127.0.0.1", 5555);
        clientConnect.sendTestMessage();
        clientConnect.sendMessage("Heyyyyy x");
        //clientConnect.sendMessage("Close Connection");


    }



}
