package tigerislandserver.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class TournamentClient implements Runnable{
    private Socket clientSocket;
    private PrintWriter outputToClient;
    private BufferedReader inputFromClient;
    private String welcomeMessage = "Welcome. Please enjoy your stay!";
    private int pID;
//    private String clientName;

    public TournamentClient(Socket newClientSocket){
        clientSocket = newClientSocket;
    }

    public void run(){
        try {
            outputToClient = new PrintWriter(clientSocket.getOutputStream());
            inputFromClient = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Error reading from connection");
        }

        outputToClient.println(welcomeMessage);
    }
}
