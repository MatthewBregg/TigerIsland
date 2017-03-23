/**
 * Created by christine on 3/20/2017.
 *
 * Some notes on sockets:
 * to send data to a socket
 * you get an OutputStream object from a socket
 * the, you write to that OutputStream object
 *
 * To read data, you read from the InputStream
 *
 *
 * this goes both ways with server and client
 */

package tigerislandserver.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.InputStream;

import java.net.UnknownHostException;

public class ClientConnection {

    Socket clientSocket;
    int portNumber;
    String hostMachine;
    BufferedReader incomingInputStream;
    InputStream clientInputStream;

    public ClientConnection(int portNumber, String hostMachine) {
        this.portNumber = portNumber;
        this.hostMachine = hostMachine;

        try {
            clientSocket = new Socket(this.hostMachine, this.portNumber);
            InputStream clientInputStream = clientSocket.getInputStream();
            InputStreamReader clientInputStreamReader = new InputStreamReader(clientInputStream);

            // now we have a way to view the input stream from the
            // client socket
            // this input stream contains information like: 

            incomingInputStream = new BufferedReader(clientInputStreamReader);

        } catch (IOException e) {
            System.out.println("Issue reading in the socket streams");
        }
    }

    /* Once Client object instantiation is successful,
     * to pull the information from the server, use the 
     * variable input, which represents our bufferedReader
     */
}