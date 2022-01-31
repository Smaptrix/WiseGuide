package client;

import java.io.IOException;


public class ClientMain {

    //For now the server is hosted locally
    String host = "127.0.0.1";


    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Client Starting");
        new ClientMain();
    }

    public ClientMain() throws IOException, InterruptedException {

        Client clientConnect = new Client();
        clientConnect.startConnection(host, 5555);
        clientConnect.sendTestMessage();
        clientConnect.sendMessage("Heyyyyy x");
        //clientConnect.sendMessage("Close Connection");

        clientConnect.receiveFile("test");


    }



}
