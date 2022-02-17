package serverclientstuff;

import client.Client;
import org.junit.Test;
import server.ServerUser;


import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class ServerClientTest {


    //TODO - change tests and current saved user info


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
        assertEquals(test.getEncodedUsername(), "test");
    }

    @Test
    //Test to make sure hashing works correctly
    public void userPasswordHashingTest() throws NoSuchAlgorithmException {
        User test = new User("test", "12345");

        //Password is '12345' pre-hash
        assertEquals(test.getEncodedPass(), "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5");

    }


    @Test
    //Test to see if users exists in the user database
    public void userExistsTest() throws NoSuchAlgorithmException, IOException {
        ServerUser testUser = new ServerUser(new User("test", "12345"));
        assertTrue(testUser.userExistState);
    }

    @Test
    //Test to make sure a user doesnt exist
    public void userDoesntExistTest() throws NoSuchAlgorithmException, IOException {
        ServerUser nonExistantTestUser = new ServerUser(new User("ImNotReal", "089-2341980-324"));
        assertFalse(nonExistantTestUser.userExistState);

    }

    @Test
    //Test to create a new user and add them to the text file (only works once unless you delete this user again)
    public void userCreationTest() throws NoSuchAlgorithmException, IOException {
        ServerUser toBeCreatedUser = new ServerUser(new User("makeme!", "password"));
        //First check they dont exist
        assertFalse(toBeCreatedUser.userExistState);
        //Make user
        toBeCreatedUser.createUser();
        //Check the user now exists
        assertTrue(toBeCreatedUser.userExistState);


    }




}
