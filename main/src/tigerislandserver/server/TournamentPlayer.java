package tigerislandserver.server;

import tigerisland.player.PlayerID;
import tigerisland.tile.Tile;
import tigerislandserver.adapter.GameInputAdapter;
import tigerislandserver.adapter.InputAdapter;
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
        OutputAdapter.sendWaitForTournamentMessage(this);
    }

    private void authenticate() {
        OutputAdapter.requestAuthentication(this);

        int timeInMilliseconds = 0;
        while(timeInMilliseconds < 30000) //3 minutes allowed to authenticate
        {
            try {
                if(inputFromClient.ready())
                {
                    if(InputAdapter.authenticate(inputFromClient.readLine()))
                    {
                        authenticated = true;
                        break;
                    }
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
                OutputAdapter.sendTimeoutMessage(game.getPlayersInGame(), this, new String[]{"GAME", ""+gid, "MOVE", ""+moveNumber});
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
            OutputAdapter.sendTimeoutMessage(game.getPlayersInGame(), this, new String[]{"GAME", ""+gid, "MOVE", ""+moveNumber});
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
