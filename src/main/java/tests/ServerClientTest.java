/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   20/01/2022
    Last Updated:   24/02/2022
 */

package tests;

import client.Client;
import org.junit.Assert;
import org.junit.Test;
import server.Server;
import server.ServerMain;
import server.ServerUserHandler;
import serverclientstuff.User;


import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;


import static org.junit.Assert.*;

/**
 * <p>
 *     Tests for the Server/Client Connection
 * </p>
 */
public class ServerClientTest {

    //TODO - change tests and current saved user info

    //Unit Test | Confirm server is online. Requires the server to be online.
    @Test
    public void serverOnlineTest(){

        boolean connected = false;

        try {
            Socket s = new Socket("127.0.0.1",5555);
            connected = true;
        } catch (IOException ex){
            connected = false;
        }
        assertTrue(connected);
    }

    //Unit Test | Confirm server is online. Requires the server to NOT be online.
    @Test
    public void serverOfflineTest(){

        boolean connected = false;

        try {
            Socket s = new Socket("127.0.0.1",5555);
            connected = true;
        } catch (IOException ex){
            connected = false;
        }
        assertFalse(connected);
    }

    @Test
    //Integration Test | Confirm the server and client can connect to one another by sending a test message after connecting.
    public void clientConnectToServer_RxTxTest() throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        assertEquals(client.sendTestMessage(), "test");
    }

    @Test
    //Integration Test | Confirm an unsuccessful connection does not cause the program to crash. Requires the server to NOT be running.
    public void unsuccessfulConnectionTest() throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        assertFalse(client.isConnected());
    }

    //Unit Test | Confirm the connection is closed correctly.
    @Test
    public void connectionClosedTest() throws IOException {
        boolean connected = false;
        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        client.closeConnection();
        try {
            client.sendTestMessage();
            connected = true;
        } catch (SocketException ex) {
            connected = false;
        }
        Assert.assertFalse(connected);
    }

    @Test
    //Tests to see if the servers echo function works correctly
    public void serverEchoFunctionTest() throws IOException {

        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        assertEquals(client.echoMessage("echos"), "echos");

    }

    @Test
    public void userUsernameSetupTest() {
        User test = new User("test", "12345");
        test.encryptUserInfo();
        assertEquals(test.getUsername(), "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08");
    }

    @Test
    //Test to make sure hashing works correctly
    public void userPasswordHashingTest() {
        User test = new User("test", "12345");
        test.encryptUserInfo();

        //Password is '12345' pre-hash
        assertEquals(test.getPassword(), "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5");

    }


    @Test
    //Test to see if users exists in the user database
    public void userExistsTest() throws IOException {
        User test = new User("test", "12345");
        test.encryptUserInfo();

        ServerUserHandler testUser = new ServerUserHandler(test, true);
        assertTrue(testUser.userExistState);
    }

    @Test
    //Test to make sure a user doesnt exist
    public void userDoesntExistTest() throws IOException {
        User test = new User("ImNotReal", "089-2341980-324");
        test.encryptUserInfo();

        ServerUserHandler nonExistantTestUser = new ServerUserHandler(test, true);
        assertFalse(nonExistantTestUser.userExistState);

    }

    @Test
    //Test to create a new user and add them to the text file (only works once unless you delete this user again)
    public void userCreationTest() throws IOException {

        User test = new User("makeme!", "password");
        test.encryptUserInfo();

        ServerUserHandler toBeCreatedUser = new ServerUserHandler(test, true);
        //First check they dont exist
        assertFalse(toBeCreatedUser.userExistState);
        //Make user
        toBeCreatedUser.createUser();
        //Check the user now exists
        assertTrue(toBeCreatedUser.userExistState);


    }


    @Test
    //Test to check whether a password cna be detected as correct or not
    public void passwordCheckTest() throws IOException {

        User test = new User("test", "54321");
        test.encryptUserInfo();

        //Create user with incorrect passowrd
        ServerUserHandler passCheckUser = new ServerUserHandler(test, true);

        //Check password has been detected as incorrect
        assertFalse(passCheckUser.passVerified);


        test = new User("test", "12345");
        test.encryptUserInfo();

        //Change password to the correct one
        passCheckUser = new ServerUserHandler(test, true);

        //Check password has been detected as correct
        assertTrue(passCheckUser.passVerified);
    }


/* - Kind of defunct test
    @Test
    //Remote verification test - Requires server launch
    public void remoteUserVerificationTest() throws IOException {

        User test = new User("test", "12345");
        test.encryptUserInfo();

        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        assertEquals("GOODPASS", client.verifyUser(test));


    }



    @Test
    //Remote verification test to prove a user doesn't exist- Requires server launch
    public void failRemoteUserVerificationTest() throws IOException {

        User test = new User("IdOntExist", "12345");
        test.encryptUserInfo();

        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        assertEquals("USERNOTFOUND", client.verifyUser(test));

    }
 */


    @Test
    //Remote login test- Requires server launch
    public void remoteLoginTest() throws IOException {

        User test = new User("test", "12345");
        test.encryptUserInfo();

        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        assertEquals("GOODLOGIN", client.requestLogin(test));

    }

    @Test
    //Remote failed login test- Requires server launch
    public void remoteFailedLoginTest() throws IOException {

        User test = new User("ICANTLOGIN", "12345");
        test.encryptUserInfo();

        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        assertEquals("BADLOGIN", client.requestLogin(test));

    }



}
