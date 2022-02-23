/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   27/01/2022
    Last Updated:   10/02/2022
 */

package server;


import java.io.IOException;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;

public class ServerMain {

    //Entry point for server application
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {


            System.out.println("Server Start\n");
            new ServerMain();

    }

    //TODO - ASk the client to login to the server


    //Sets up the initial server
    public ServerMain() throws IOException, NoSuchAlgorithmException {


        try {
            Server server = new Server();

            server.startup(5555);


            server.bufferListen();
        }catch(SocketException e){
            System.out.println("Socket exception - Lost connection with client");
        }




    }

}


