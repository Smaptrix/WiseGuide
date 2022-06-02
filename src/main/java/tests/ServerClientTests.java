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


import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;


import static org.junit.Assert.*;

/**
 * <p>
 *     Tests for the Server/Client Connection
 * </p>
 */
public class ServerClientTests {

    //TODO - change tests and current saved user info

    //Unit Test | Confirm server is online. Requires the server to be online.
    @Test
    public void serverOnlineTest(){

        boolean connected;

        try {
            Socket s = new Socket("127.0.0.1",5555);
            connected = true;
        } catch (IOException ex){
            connected = false;
        }
        assertTrue(connected);
    }

    //Unit Test | Confirm server is online. Requires the server to NOT be online.
    //Run this test again after a connection has been opened and closed to confirm connections are closed correctly.
    @Test
    public void serverOfflineTest(){

        boolean connected;

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
        boolean connected;
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

}
