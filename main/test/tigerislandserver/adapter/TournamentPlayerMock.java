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
        clientSocket = newClientSocket;
        authenticated = false;
        pID = new PlayerID();
        username ="Unknown/Unauthenticated";

        try {
            outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
            inputFromClient = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            System.out.println("Error reading from connection");
        }

        OutputAdapter.sendWelcomeMessage(this);

        try {
            Thread.sleep(1800); //1.8s allowed to authenticate
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {

            if (inputFromClient.ready()) {
                String message= inputFromClient.readLine();
                canEnterTournament = InputAdapter.canEnterTournament(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        authenticate();
    }

    private void authenticate()
    {
        OutputAdapter.requestAuthentication(this);

        try {
            Thread.sleep(1800); //1.8s allowed to authenticate
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            if (inputFromClient.ready()) {
                if (InputAdapter.authenticate(this, inputFromClient.readLine())) {
                    authenticated = true;
                    OutputAdapter.sendWaitForTournamentMessage(this);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void requestMove(GameThread game, char gid, int moveNumber, Tile tile)
    {
        OutputAdapter.sendMoveRequestMessage(this, gid, moveNumber, tile);

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            if (!inputFromClient.ready()) {
                OutputAdapter.sendTimeoutMessage(game.getPlayersInGame(), this, new String[]{"GAME", "" + gid, "MOVE", "" + moveNumber});
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
            OutputAdapter.sendTimeoutMessage(game.getPlayersInGame(), this, new String[]{"GAME", "" + gid, "MOVE", "" + moveNumber});
            game.timeout(this);
        }
    }

    public void sendMessage(String message)
    {
        synchronized (lock)
        {
            System.out.println("SENDING MESSAGE TO " + getID().getId() + ": \"" + message + "\"");
            outputToClient.println(message);
        }
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
        clientSocket = newSocket;
        authenticated = false;
        pID = new PlayerID();
        username = "Unknown/Unauthenticated";

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






