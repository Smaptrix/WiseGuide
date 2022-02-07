package server;


import java.io.IOException;

public class ServerMain {

    //Entry point for server application
    public static void main(String[] args) throws IOException {
        System.out.println("Server Start\n");
        new ServerMain();
    }

    //Sets up the initial server
    public ServerMain() throws IOException {

        Server server = new Server();

        server.startup(5555);

        server.bufferListen();

    }

}
