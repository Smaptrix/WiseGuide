package client;

import java.io.IOException;
import java.net.SocketException;


public class ClientMain {

    //For now the server is hosted locally
    String host = "127.0.0.1";


    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Client Starting");
        new ClientMain();
    }

    public ClientMain() throws IOException, InterruptedException {

      try {
          Client clientConnect = new Client();
          clientConnect.startConnection(host, 5555);
          clientConnect.sendTestMessage();
          clientConnect.echoMessage("Heyyyyy");
          clientConnect.echoMessage("again");
          //clientConnect.sendMessage("Close Connection");

          clientConnect.receiveFile("test.txt");
      }catch(SocketException e){
          System.out.println("Connection lost/Closed");
      }


    }



}
