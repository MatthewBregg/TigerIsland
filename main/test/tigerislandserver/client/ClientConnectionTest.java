package tigerislandserver.client;

/**
 * Created by christinemoore on 3/23/17.
 */

import org.junit.*;
import tigerislandserver.client.ClientConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientConnectionTest {
    private static ClientConnection clientConnection;

    @BeforeClass
    public static void makeClientConnection(){
        int portNumber = 555;
        String hostMachine = "hi";

        clientConnection = new ClientConnection(portNumber, hostMachine);
    }

    @Test
    public void clientConnectionObjectCreationSuccessful(){
        Assert.assertTrue((clientConnection.getPortNumber() == 555) && (clientConnection.getHostMachine().equals("hi")));
    }

}
