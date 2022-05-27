package tests;

import client.Client;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * <p>
 *     Tests for the Server/Client File Transfer
 * </p>
 */
public class FileTransferTests extends ApplicationTest {

    @Test
    //Test to confirm the server can recognise a file. (Windows)
    public void serverFileDetectTestWindows() throws IOException {
        String filePath = System.getProperty("user.dir") + "\\text.txt";
        System.out.println(filePath);

        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        Assert.assertEquals("File found",client.requestServerFileCheck());
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
