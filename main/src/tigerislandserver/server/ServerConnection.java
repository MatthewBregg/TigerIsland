/**
 * Created by christine on 03/16/2017.
 * http://stackoverflow.com/questions/2187626/how-to-create-a-basic-java-server
 * heavily used the above link for inspiration
 */

package tigerislandserver.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class ServerConnection {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;

    // there can be 4 different ways we'll need to make a connection
    // 1st way: given both the serverSocket and clientSocket
    public ServerConnection(ServerSocket serverSocket, Socket clientSocket){
        this.serverSocket =  serverSocket;
        this.clientSocket = clientSocket;
    }

    // 2nd way: given just the serverSocket
    public ServerConnection(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    // 3rd way: given just the clientSocket
    public ServerConnection(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    // 4th way: given the port number
    public ServerConnection(int portNumber){
        ServerSocket potentialSocket = null;
        try{
            potentialSocket = new ServerSocket(portNumber);
        }
        catch(IOException e){
            System.err.println("Could not connect to port " + portNumber);
            System.exit(1);
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void acceptClientSocket() {
        try{
            clientSocket = serverSocket.accept();
        }
        catch(IOException e){
            System.err.println("Failed to accept Client.");
            System.exit(1);
        }
    }

    public void closeConnection(){
        try{
            clientSocket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
