package serverclientstuff;

import client.Client;
import org.junit.Test;



import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ServerClientTest {

    @Test
    //Launch Server first, then test
    //Tests to see if the server and client can connect to one another
    //Then tests to see if the test message is snet and recieved
    public void clientConnectToServer_RxTxTest() throws IOException {

        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        assertEquals(client.sendTestMessage(), "test");
        client.closeConnection();
    }

    @Test
    //Tests to see if the servers echo function works correctly
    public void serverEchoFunctionTest() throws IOException {

        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        assertEquals(client.echoMessage("echos"), "echos");

    }

}
