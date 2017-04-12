package tigerislandserver.server;

import tigerislandserver.adapter.OutputAdapter;
import tigerislandserver.gameplay.Challenge;

import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class TournamentServer {
    private ServerSocket serverSocket;
    private ArrayList<TournamentPlayer> clientConnections = new ArrayList<>();
    private boolean currentlyAcceptingConnections;

    public TournamentServer(int port) {
        currentlyAcceptingConnections = false;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Exception while listening for a connection on port " + getPort());
            System.out.println(e.getMessage());
        }
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

        while(System.currentTimeMillis() < socketCloseTime){
            try {
                wait(100);
            } catch (InterruptedException e) {
                System.err.println("Server connection error");
                e.printStackTrace();
            }
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startTournament(int numberOfChallenges) {
        for (int i = 0; i < numberOfChallenges; i++) {
            if (i != 0) {
                OutputAdapter.sendWaitForChallengeMessage(clientConnections);
            }

            Challenge challenge = new Challenge(clientConnections, i);
            OutputAdapter.sendNewChallengeMessage(clientConnections, i, challenge.getTotalChallengeRounds());
            challenge.play();
        }

        OutputAdapter.sendEndOfChallengesMessage(clientConnections);
    }

    public void finalize() {
        // TODO: close all connections
    }

    class ConnectionAcceptor implements Runnable {
        ConnectionAcceptor() {}

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
                } catch (SocketException e){
                    System.out.println("Server socket closed!");
                } catch (IOException e) {
                    System.err.println("Exception caught or problem listening on port " + getPort());
                    System.exit(-1);
                }
            }

            currentlyAcceptingConnections = false;

            synchronized (clientConnections) {
                for (int i = 0; i < clientConnections.size(); i++) {
                    if (!clientConnections.get(i).isAuthenticated()) {
                        clientConnections.remove(i--);
                    }
                }
            }
        }

    }
}

