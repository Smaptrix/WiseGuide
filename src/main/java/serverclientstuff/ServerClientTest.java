/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham, Ben Alexander
    Date Created:   27/01/2022
    Last Updated:   10/02/2022
 */

package serverclientstuff;

import client.Client;
import org.junit.Test;


import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    //Test to detect whether a file has been downloaded
    public void clientServerTransferFileTest() throws IOException {

        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        File testFile = client.requestFile("test.txt");
        assertTrue(testFile.exists());

    }

}
