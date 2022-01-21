package server;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ServerTest {



    //Tests that the server initialises its ports
    @Test
    private void TestServerStartup() throws IOException {

        Server server = new Server();

        String startupMsg = "Server now accepting connections";
        //Checks that the server returns the message that displays after all of the ports have been opened
        assertEquals (server.startup(6666), startupMsg);
    }
}
