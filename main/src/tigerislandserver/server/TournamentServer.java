package tigerislandserver.server;

import tigerisland.datalogger.CompositeLogger;
import tigerisland.datalogger.ConsoleLogger;
import tigerisland.datalogger.LoggerFactory;
import tigerisland.datalogger.SQLiteLogger;
import tigerisland.player.PlayerID;
import tigerislandserver.JavaFXScoreboardPOC.RoundInfo;
import tigerislandserver.adapter.OutputAdapter;
import tigerislandserver.gameplay.Challenge;
import tigerislandserver.gameplay.TournamentScoreManager;
import tigerislandserver.gameplay.TournamentScoreboard;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TournamentServer {
    private ServerSocket serverSocket;
    private ArrayList<TournamentPlayer> clientConnections = new ArrayList<>();
    private boolean currentlyAcceptingConnections;
    private TournamentScoreManager tournamentScoreManager;
    Map<Integer, String> playersIdToUserName;


    public RoundInfo getTrackRoundInfo() {
        return trackRoundInfo;
    }

    private RoundInfo trackRoundInfo;

    public TournamentServer(int port) {
        currentlyAcceptingConnections = false;
        tournamentScoreManager = new TournamentScoreManager(clientConnections);
        trackRoundInfo = RoundInfo.getInstance();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Exception while listening for a connection on port " + getPort());
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<TournamentPlayer> getTourneyPlayers(){
        return clientConnections;
    }

    public int getPort() {
        int portNumber = -1;
        if (serverSocket != null)
            portNumber = serverSocket.getLocalPort();
        return portNumber;
    }

    public void acceptConnections(int connectionTimeout_s) {
        // Running accept() on separate thread should allow socket.close() to be called from this thread
        // timeout argument is in seconds, converted to milliseconds
        if (currentlyAcceptingConnections)
            return;

        currentlyAcceptingConnections = true;

        int connectionTimeout_ms = connectionTimeout_s * 1000;
        long socketCloseTime = System.currentTimeMillis() + connectionTimeout_ms;

        Thread connectionThread = new Thread(new ConnectionAcceptor());
        connectionThread.start();

        System.out.println("Server socket will close in: " + connectionTimeout_s + " seconds");
        long currentCounter = connectionTimeout_s;
        while (System.currentTimeMillis() < socketCloseTime) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long timeLeft = (socketCloseTime - System.currentTimeMillis())/1000;
            if((timeLeft % 5) == 0 && timeLeft != currentCounter){
                currentCounter = timeLeft;
                System.out.println(timeLeft + " seconds remaining");
            }
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(connectionThread.isAlive()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void startTournament(int numberOfChallenges) {
        tournamentScoreManager.initializeOverallTournamentScores();

        registerPlayerIdsToUerNames();
        trackRoundInfo.setEndChallenge(numberOfChallenges-1);
        for(int i=0; i < numberOfChallenges; i++)
        {
            if(i != 0){
                OutputAdapter.sendWaitForChallengeMessage(clientConnections);
            }



            Challenge challenge = new Challenge(clientConnections, i, trackRoundInfo);

            OutputAdapter.sendNewChallengeMessage(clientConnections, i, challenge.getTotalChallengeRounds());
            challenge.play();

            // at this point that specific challenge object should be over
            TournamentScoreboard scoreboard = challenge.getScoreboard();
            tournamentScoreManager.setEndedChallengeScores(scoreboard.getPlayerMap());
            tournamentScoreManager.addToAllPlayersScore();
        }

        storeTournamentScores();

        OutputAdapter.sendEndOfChallengesMessage(clientConnections);
    }

    private void registerPlayerIdsToUerNames() {
        playersIdToUserName = new HashMap<>();
        for(int i = 0; i < clientConnections.size(); i++) {
            TournamentPlayer tPlayer = clientConnections.get(i);
            int defaultPID = tPlayer.getID().getId();
            playersIdToUserName.put(defaultPID, tPlayer.getUsername());
        }
    }

/*
    private void updatePlayerTournamentScores(){
        for(TournamentPlayer player : clientConnections){
            TournamentScore playerScore = player.getTournamentScore();
            playerScore.addChallengeScoreToTournament();
        }
    }

*/
    private void storeTournamentScores() {

        SQLiteLogger sqLiteLogger = LoggerFactory.getSQLLogger('0',0,0, playersIdToUserName);
        ConsoleLogger consoleLogger = new ConsoleLogger(0,'0',0, playersIdToUserName);
        CompositeLogger compositeLogger = new CompositeLogger(sqLiteLogger, consoleLogger);

        Map<PlayerID, Integer> scores = tournamentScoreManager.getOverallScores();
        for(int j = 0; j < clientConnections.size(); j++) {
            TournamentPlayer tp = clientConnections.get(j);
            PlayerID pID = tp.getID();
            int score = scores.get(pID);
            compositeLogger.writeToTournamentScore(pID, score);
        }
    }

    public void finalize() {
        // TODO: close all connections
    }

    public int getPlayerCount() {
        synchronized (clientConnections){
            return clientConnections.size();
        }
    }

    class ConnectionAcceptor implements Runnable {
        ConnectionAcceptor() {
        }

        @Override
        public void run() {
            boolean listening = true;
            while (listening) {
                try {
                    Socket newClient = serverSocket.accept();
                    TournamentPlayer client =
                            new TournamentPlayer(newClient);

                    if (client.canEnterTournament()) {
                        synchronized (clientConnections) {
                            clientConnections.add(client);
                        }
                    }
                    Thread configureClient = new Thread(client);
                    configureClient.start();
                } catch (SocketException e) {
                    System.out.println("Server socket closed!");
                    listening = false;
                } catch (IOException e) {
                    System.err.println("Exception caught or problem listening on port " + getPort());
                    System.exit(-1);
                }
            }
            System.out.println("Before checking auth: " + clientConnections.size() + " players");

            currentlyAcceptingConnections = false;

            synchronized (clientConnections) {
                for (int i = 0; i < clientConnections.size(); i++) {
                    if (!clientConnections.get(i).isAuthenticated()) {
                        clientConnections.remove(i--);
                    }
                }
            }

            System.out.println("After checking auth: " + clientConnections.size() + " players");

        }

    }
}

