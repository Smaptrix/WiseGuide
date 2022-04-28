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

/**
 * Legacy Class used for testing pre-GUI
 */
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
    public ClientMain() throws IOException {

        //Creates User then hashes there data
        User testUser = new User("test", "12345");
        testUser.encryptUserInfo();

      try {
          Client client = new Client();
          client.startConnection(host, 5555);

          client.requestLogin(testUser);
          client.requestLogout();
          client.closeConnection();
      } catch(SocketException e){
          System.out.println("Connection lost/Closed");
      }
    }
}