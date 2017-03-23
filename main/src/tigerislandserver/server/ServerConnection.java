/**
 * Created by christine on 03/16/2017.
 * http://stackoverflow.com/questions/2187626/how-to-create-a-basic-java-server
 * heavily used the above link for inspiration
 */

package tigerislandserver.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.io.IOException;
import java.net.SocketException;


public class ServerConnection {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    BufferedReader inputReader;
    PrintWriter outputWriter;
    int connectionPortNumber;

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

    public int getConnectionPortNumber(){
        return connectionPortNumber;
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

    public void initializeInputChannel(){
        try {
            inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void initializeOutputChannel(){
        try {
            outputWriter = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessageThroughOutputChannel(String message){
        outputWriter.println(message);
    }

    public String recieveMessageFromInputChannel() throws IOException{
        try{
            String messageFromPlayer = inputReader.readLine();

            return messageFromPlayer;

        } catch (SocketException e){
            return "There was an issue receiving message from player";
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
