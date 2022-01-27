package serverclientstuff;

import client.ClientConnect;
import org.junit.Test;

import java.io.IOException;
// Does this work?
public class ServerClientTest {

    @Test
    public void clientConnectToServer() throws IOException {
        ClientConnect client = new ClientConnect();
        client.startConnection("127.0.0.1", 5555);

    }

}
