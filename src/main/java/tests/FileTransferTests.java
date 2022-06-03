package tests;

import client.Client;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 *     Tests for the Server/Client File Transfer
 * </p>
 */
public class FileTransferTests extends ApplicationTest {

    @Test
    //Test to confirm the server can recognise a file.
    public void serverFileDetectTest() throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        String testResult = client.requestServerTest("fileDetectTest");
        Assert.assertEquals("File not found",testResult);
    }

    @Test
    //Confirm the operating system is correct. (Windows)
    public void osDetectTestWindows() throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        String testResult = client.requestServerTest("osDetectTest");
        Assert.assertEquals("\\",testResult);
    }

    @Test
    //Confirm the operating system is correct. (Mac)
    public void osDetectTestMac() throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        String testResult = client.requestServerTest("osDetectTest");
        Assert.assertEquals("/",testResult);
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
    //Confirm the server can recognise a previously file.
    public void clientFileDetectTest() throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        client.requestFile("test.txt");
        assertTrue(client.isFileDownloaded("test.txt"));
    }

}
