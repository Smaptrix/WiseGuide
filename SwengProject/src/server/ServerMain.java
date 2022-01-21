package server;


import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        System.out.println("Server Start\n");
        new ServerMain();
    }


    public ServerMain() throws IOException {

        Server server = new Server();

        server.startup(5555);

        server.bufferListen();

    }

}
