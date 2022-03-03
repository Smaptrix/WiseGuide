/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   27/01/2022
    Last Updated:   10/02/2022
 */

package client;

import serverclientstuff.User;

import java.io.IOException;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;


public class ClientMain {

    //For now the server is hosted locally
    String host = "127.0.0.1";

    //Entry point of client-side application
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {


            System.out.println("Client Starting");
            //Generates an instance of Client Main
            new ClientMain();

    }


    //NOTE - REMEMBER TO HASH DATA AFTER CREATING NEW USER
    //     - DO NOT HASH ON THE SERVER :)  - JOE



    //Connects the client to the server and requests a few test files
    public ClientMain() throws IOException, NoSuchAlgorithmException {



        //Creates User then hashes there data
        User testUser = new User("test", "12345");
        testUser.hashUserInfo();


      try {
          Client client = new Client();
          client.startConnection(host, 5555);

          System.out.println(client.verifyUser(testUser));


          client.requestLogin(testUser);


          client.requestLogout();

          /*
          client.echoMessage("Hey");
          client.echoMessage("again");


          client.requestFile("test.txt");

          //Utils.openFile(client.fileLocations.get("test.txt"));

          client.requestFile("partypopper.jpg");

          //Utils.openFile(client.fileLocations.get("partypopper.jpg"));

          client.requestFile("Applause.mp3");

         //Utils.openFile(client.fileLocations.get("Applause.mp3"));

          client.requestFile("clapping.mp4");

          //Utils.openFile(client.fileLocations.get("clapping.mp4"));

          */

          client.closeConnection();


      }catch(SocketException e){
          System.out.println("Connection lost/Closed");
      }

    }



}
