package tigerislandserver.client;

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

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection {

    Socket clientSocket;
    int portNumber;
    String hostMachine;
    PrintWriter outputWriter;
    BufferedReader inputReader;

    public ClientConnection(int portNumber, String hostMachine) {
        this.portNumber = portNumber;
        this.hostMachine = hostMachine;

        try {
            clientSocket = new Socket(this.hostMachine, this.portNumber);

            InputStreamReader clientInputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            inputReader = new BufferedReader(clientInputStreamReader);
            // now we have a way to view the input stream from the
            // client socket
            // this input stream contains information like: 

            outputWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            // this provides a way to send string messages on this connection
        } catch (IOException e) {
            System.out.println("Issue reading in the socket streams");
        }
    }

    public int getPortNumber(){
        return portNumber;
    }

    public String getHostMachine(){
        return hostMachine;
    }

    public void waitToStart(){
        String serverStartMessage;

        try {
            while ((serverStartMessage = inputReader.readLine()) != null) {
                System.out.println(serverStartMessage);

                if (serverStartMessage.equalsIgnoreCase("BYE PLAYER")) {
                    System.out.println("Server is terminating game");
                    break;
                }

                if (serverStartMessage.equalsIgnoreCase("START YOUR GAME")) {
                    break;
                }
            }
        }
        catch (UnknownHostException e) {
                System.err.println(hostMachine + " is an unknown host");
                System.exit(1);
        }
        catch (IOException e){
            System.err.println("The I/O connection with host " + hostMachine + " failed");
            System.exit(1);
        }
    }

    public void startGameplay(){
        String serverPlayMessage;

        try{
            while((serverPlayMessage = inputReader.readLine()) != null){
                System.out.println(serverPlayMessage);

                if (serverPlayMessage.equalsIgnoreCase("BYE PLAYER")) {
                    System.out.println("Server is terminating game");
                    break;
                }

                if(serverPlayMessage.equalsIgnoreCase("MAKE YOUR MOVE PLAYER")){
                    System.out.println("GAMEPLAY LOGIC GOES IN THIS IF");
                }
            }
        }
        catch (UnknownHostException e) {
            System.err.println(hostMachine + " is an unknown host");
            System.exit(1);
        }
        catch (IOException e){
            System.err.println("The I/O connection with host " + hostMachine + " failed");
            System.exit(1);
        }
    }
}
