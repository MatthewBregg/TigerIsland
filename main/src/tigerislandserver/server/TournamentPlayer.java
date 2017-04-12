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
import java.util.concurrent.ConcurrentLinkedQueue;


public class TournamentPlayer implements Runnable
{
    private final Object lock = new Object();
    private Socket clientSocket;
    private PrintWriter outputToClient;
    private BufferedReader inputFromClientFoo;
    private ConcurrentLinkedQueue<String> gameA;
    private ConcurrentLinkedQueue<String> gameB;
    private boolean canEnterTournament;
    private boolean authenticated;
    private PlayerID pID;
    private String username;

    private boolean gameAReady() {
        processInputFromClientIntoGameQueues();
        return gameA.size() > 0;
    }

    private boolean gameBReady() {
        processInputFromClientIntoGameQueues();
        return gameB.size() > 0;
    }

    private String popGameAMessage() {
        processInputFromClientIntoGameQueues();
        return gameA.poll();
    }

    private String popGameBMessage() {
        processInputFromClientIntoGameQueues();
        return gameB.poll();
    }

    private void processInputFromClientIntoGameQueues() {
        try {
            if(inputFromClientFoo.ready()){
                String input = inputFromClientFoo.readLine();
                pushMessageToGameQueues(input);
            }
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Error reading from input!");
        }
    }

    private void pushMessageToGameQueues(String input){
        String[] tokens = input.split("\\s+");

        if (tokens[1].contentEquals("A")) {
            gameA.add(input);
        } else if (tokens[1].contentEquals("B")) {
            gameB.add(input);
        } else {
            System.err.println("Invalid GAME ID from this.readline()");
        }
    }


    public TournamentPlayer(Socket newClientSocket)
    {
        gameA = new ConcurrentLinkedQueue<>();
        gameB = new ConcurrentLinkedQueue<>();
        clientSocket = newClientSocket;
        authenticated = false;
        pID = new PlayerID();
        username ="Unknown/Unauthenticated";

        try {
            outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
            inputFromClientFoo = new BufferedReader(
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

            if (inputFromClientFoo.ready()) {
                String message= inputFromClientFoo.readLine();
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
            if (inputFromClientFoo.ready()) {
                if (InputAdapter.authenticate(this, inputFromClientFoo.readLine())) {
                    authenticated = true;
                    OutputAdapter.sendWaitForTournamentMessage(this);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean inputFromClientReady(char gameid) {
        if ( gameid == 'A') {
            return gameAReady();
        } else if ( gameid == 'B') {
            return gameBReady();
        } else {
            System.err.println("Invalid GAME ID in inputFromClientReady()");
            return false;
        }
    }

    private String readLineFromGameInput(char gameid) {
        if ( gameid == 'A') {
            return popGameAMessage();
        } else if ( gameid == 'B') {
            return popGameBMessage();
        } else {
            System.err.println("Invalid GAME ID in readLineFromGameInput()");
            return null;
        }
    }

    public void clearQueue(char gameId) {
        if ( gameId == 'A') {
            gameA.clear();
        } else if ( gameId == 'B') {
            gameB.clear();;
        } else {
            System.err.println("Invalid GAME ID in clearQueue()");
        }
    }

    public synchronized void requestMove(GameThread game, char gid, int moveNumber, Tile tile)
    {
        OutputAdapter.sendMoveRequestMessage(this, gid, moveNumber, tile);

        // Replace sleep 1800 with busy loop on turn is ready
        int timeoutCounter = 0;

        int timeOutMaxIncrements = 16;
        long sleepDuration = 100;
        // Max timeout = sleepDuration * timeoutMaxIncrements in ms.
        // If we want to be super strict on timeout, then set a bool timeout reached here, but I don't think we should.
        while(!inputFromClientReady(gid) && timeoutCounter < timeOutMaxIncrements) {
            try {
                game.sleep(sleepDuration);
                ++timeoutCounter;
                processInputFromClientIntoGameQueues();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        game.enableTurnWaiting();
        while(game.hasTurnWaiting()) {
            try {
                game.sleep(sleepDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        if (!inputFromClientReady(gid)) {
            OutputAdapter.sendTimeoutMessage(game.getPlayersInGame(), this, new String[]{"GAME", "" + gid, "MOVE", "" + moveNumber});
            game.timeout(this);
            return;
        }


            GameInputAdapter.makeMove(game, this, readLineFromGameInput(gid), gid, moveNumber, tile);
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
