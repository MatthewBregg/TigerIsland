package tigerislandserver.adapter;

import tigerisland.player.PlayerID;
import tigerisland.tile.Tile;
import tigerislandserver.gameplay.GameThread;
import tigerislandserver.server.TournamentPlayer;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TournamentPlayerMock extends TournamentPlayer {

    private final Object lock = new Object();
    private Socket clientSocket;
    private PrintWriter outputToClient;
    private BufferedReader inputFromClient;
    private boolean canEnterTournament;
    private boolean authenticated;
    private PlayerID pID;
    private String username;

    TournamentPlayerMock(Socket newSocket) {

        clientSocket = newSocket;
        authenticated = false;
        pID = new PlayerID();
        username ="Unknown/Unauthenticated";

    }

    public void run()
    {
        authenticate();
    }

    private void authenticate()
    {

    }

    public synchronized void requestMove(GameThread game, char gid, int moveNumber, Tile tile)
    {

    }

    public void sendMessage(String message)
    {

    }

    public PlayerID getID()
    {
        return pID;
    }

    public boolean isAuthenticated()
    {
        return authenticated;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }

    public boolean canEnterTournament()
    {
        return canEnterTournament;
    }
}




