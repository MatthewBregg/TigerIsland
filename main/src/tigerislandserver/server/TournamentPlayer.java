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


public class TournamentPlayer implements Runnable {
    private final Object lock = new Object();
    private Socket clientSocket;
    private PrintWriter outputToClient;
    private BufferedReader inputFromClient;
    private boolean canEnterTournament;
    private boolean authenticated;
    private PlayerID pID;
    private String username;

    public TournamentPlayer(Socket newClientSocket) {
        clientSocket = newClientSocket;
        authenticated = false;
        pID = new PlayerID();
        username = "Unknown/Unauthenticated";

        try
        {
            outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
            inputFromClient = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e)
        {
            System.out.println("Error reading from connection");
        }

        OutputAdapter.sendWelcomeMessage(this);

        try
        {
            for (int i = 0; i < 32; i++)
            {
                if (!inputFromClient.ready())
                {
                    Thread.sleep(50); //1.6s allowed to authenticate
                }
            }


            if (inputFromClient.ready())
            {
                String message = inputFromClient.readLine();
                canEnterTournament = InputAdapter.canEnterTournament(message);
            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            System.out.println("Error reading from connection");
        }
    }

    public void run() {
        authenticate();
    }

    private void authenticate() {
        OutputAdapter.requestAuthentication(this);

        try
        {
            for (int i = 0; i < 32; i++)
            {
                if (!inputFromClient.ready())
                {
                    Thread.sleep(50); //1.6s allowed to authenticate
                }
            }


            if (inputFromClient.ready())
            {
                if (InputAdapter.authenticate(this, inputFromClient.readLine()))
                {
                    authenticated = true;
                    OutputAdapter.sendWaitForTournamentMessage(this);
                }
            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            System.out.println("Error reading from connection");
        }
    }

    public synchronized void requestMove(GameThread game, char gid, int moveNumber, Tile tile) {
        OutputAdapter.sendMoveRequestMessage(this, gid, moveNumber, tile);

        try
        {
            for (int i = 0; i < 32; i++)
            {
                if (!inputFromClient.ready())
                {
                    Thread.sleep(50); //1.6s allowed to authenticate
                }
            }

            if (!inputFromClient.ready())
            {
                OutputAdapter.sendLaterTimeoutMessage(game.getPlayersInGame(), this, new String[]{"GAME", "" + gid, "MOVE", "" + moveNumber});
                game.timeout(this);
                return;
            }
            else
            {
                GameInputAdapter.makeMove(game, this, inputFromClient.readLine(), gid, moveNumber, tile);
            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            System.out.println("Error reading from connection");
        }
    }

    public void sendMessage(String message) {
        synchronized (lock)
        {
            System.out.println("SENDING MESSAGE TO " + getID().getId() + ": \"" + message + "\"");
            outputToClient.println(message);
        }
    }

    public PlayerID getID() {
        return pID;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public boolean canEnterTournament() {
        return canEnterTournament;
    }
}
