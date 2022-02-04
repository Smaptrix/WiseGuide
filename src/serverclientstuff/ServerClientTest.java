package serverclientstuff;

import client.Client;
import org.junit.Test;



import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ServerClientTest {

    @Test
    //Launch Server first, then test
    public void clientConnectToServer_RxTxTest() throws IOException {

        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        assertEquals(client.sendTestMessage(), "Error 404: Text Request Code Not Found");
        assertEquals(client.sendMessage("Text romanRaidMarsRoute"), "Courtyard,Lowther,Stone Roses,Yates,Salvation");
    }

}
