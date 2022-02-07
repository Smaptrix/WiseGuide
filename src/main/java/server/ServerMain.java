package server;


import java.io.IOException;
import java.net.SocketException;

public class ServerMain {

    //Entry point for server application
    public static void main(String[] args) throws IOException {


            System.out.println("Server Start\n");
            new ServerMain();

    }

    //Sets up the initial server
    public ServerMain() throws IOException {

        try {
            Server server = new Server();

            server.startup(5555);

            server.bufferListen();
        }catch(SocketException e){
            System.out.println("Socket exception - Lost connection with client");
        }


    }

}


