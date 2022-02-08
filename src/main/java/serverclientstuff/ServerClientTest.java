package serverclientstuff;

import client.Client;
import org.junit.Test;


import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

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



    @Test
    public void userUsernameSetupTest() throws NoSuchAlgorithmException {
        User test = new User("test", "12345");
        assertEquals(test.getUsername(), "test");
    }

    @Test
    //Test to make sure hashing works correctly
    public void userPasswordHashingTest() throws NoSuchAlgorithmException {
        User test = new User("test", "12345");

        //Password is '12345' pre-hash
        assertEquals(test.getEncodedPass(), "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5");

    }

}
