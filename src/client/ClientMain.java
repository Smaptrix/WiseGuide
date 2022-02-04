package client;

import java.io.File;
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
          Client client = new Client();
          client.startConnection(host, 5555);
          client.echoMessage("Heyyyyy");
          client.echoMessage("again");
          //client.sendMessage("Close Connection");
          client.requestFile("test.txt");

          client.openFile((File) client.fileLocs.get("test.txt"));

          client.requestFile("partypopper.jpg");

          client.openFile((File) client.fileLocs.get("partypopper.jpg"));



      }catch(SocketException e){
          System.out.println("Connection lost/Closed");
      }


    }



}
