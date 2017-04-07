package tigerislandserver.server;

import tigerisland.player.PlayerID;
import tigerisland.tile.Tile;
import tigerislandserver.adapter.GameInputAdapter;
import tigerislandserver.adapter.OutputAdapter;
import tigerislandserver.gameplay.GameThread;

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
    private PlayerID pID;

    public TournamentPlayer(Socket newClientSocket){
        clientSocket = newClientSocket;
        authenticated = false;
        pID = new PlayerID();
    }

    public PlayerID getPlayerID(){
        return pID;
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

    public synchronized void requestMove(GameThread game, char gid, int moveNumber, Tile tile)
    {
        OutputAdapter.sendMoveRequestMessage(this, gid, moveNumber, tile);

        int timeInMilliseconds = 0;
        while(timeInMilliseconds < 1800)
        {
            try {
                if(inputFromClient.ready())
                {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            timeInMilliseconds+=100;
        }

        try {
            if(!inputFromClient.ready())
            {
                game.timeout(this);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            GameInputAdapter.makeMove(game, this, inputFromClient.readLine(), gid, moveNumber, tile);
        } catch (IOException e) {
            e.printStackTrace();
            game.timeout(this);
        }
    }

    public synchronized void sendMessage(String message)
    {
        outputToClient.println(message);
    }

    public PlayerID getID()
    {
        return pID;
    }
}
