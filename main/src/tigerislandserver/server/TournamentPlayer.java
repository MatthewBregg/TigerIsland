package tigerislandserver.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class TournamentPlayer implements Runnable{
    private Socket clientSocket;
    private PrintWriter outputToClient;
    private BufferedReader inputFromClient;
    private boolean authenticated;
    private String welcomeMessage = "Welcome. Please enjoy your stay!";
//    private String clientName;

    public TournamentPlayer(Socket newClientSocket){
        clientSocket = newClientSocket;
        authenticated = false;
    }

    public void run(){
        try {
            outputToClient = new PrintWriter(clientSocket.getOutputStream());
            inputFromClient = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Error reading from connection");
        }

        authenticate();
        outputToClient.println(welcomeMessage); // TODO: Get correct welcome message from server welcome protocol
    }

    private void authenticate() {
        // TODO: call authentication protocol
    }
}
