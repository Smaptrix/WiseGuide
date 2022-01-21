package client;

public class ClientMain {

    public static void main(String[] args) {
        System.out.println("Client Starting\n");
        new ClientMain();
    }

    public ClientMain(){

        ClientConnect clientConnect = new ClientConnect();
        clientConnect.startConnection("127.0.0.1", 5555);

    }



}
