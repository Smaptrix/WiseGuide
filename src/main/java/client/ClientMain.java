//
// CURRENTLY NOT IN USE!!!
//
package client;

import java.io.IOException;
import java.net.SocketException;


public class ClientMain {
    //For now the server is hosted locally
    String host = "127.0.0.1";
    static Client client;

    //Entry point of client-side application
    public static void main() throws IOException {


            System.out.println("Client Starting");
            //Generates an instance of Client Main
            new ClientMain();

    }

    //Connects the client to the server and requests a few test files
    public ClientMain() throws IOException {

      try {
          client = new Client();
          client.startConnection(host, 5555);
          client.echoMessage("Hey");
          client.echoMessage("again");



          client.requestFile("test.txt");

          //client.openFile(client.fileLocations.get("test.txt"));

          client.requestFile("partypopper.jpg");

          //client.openFile(client.fileLocations.get("partypopper.jpg"));

          client.requestFile("Applause.mp3");

         //client.openFile(client.fileLocations.get("Applause.mp3"));

          client.requestFile("clapping.mp4");

          //client.openFile(client.fileLocations.get("clapping.mp4"));

          //client.closeConnection();


      }catch(SocketException e){
          System.out.println("Connection lost/Closed");
      }

    }

    //Connects the client to the server and requests a few test files
    public static void closeClient() throws IOException {
        try {
            client.closeConnection();
        } catch (SocketException e) {
            System.out.println("Connect failed to close.");
        }
    }

}
