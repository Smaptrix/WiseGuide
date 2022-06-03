/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   27/01/2022
    Last Updated:   03/06/2022
 */

package server;


import java.io.IOException;

public class ServerMain {

    /**
     * Entry point for the server half of the application
     * @param args the arguments for the program if run in the command line
     * @throws IOException if the server cannot connect to the client
     */
    public static void main(String[] args) throws IOException {
            System.out.println("Server Start\n");
            new ServerMain();
    }


    /**
     * Creates the server object, and attaches it to a port
     * The default port is 5555
     */
    public ServerMain() {
       //Restarts the server after a bit of time
            try {
                while(true) {
                Server server = new Server();

                server.startup(5555);

                server.bufferListen();
                }
            } catch (Exception e) {
                System.out.println("Socket exception - Lost connection with client");
                e.printStackTrace();

            }
    }

}


