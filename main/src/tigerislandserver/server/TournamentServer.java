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

        try
        {
            serverSocket = new ServerSocket(port);
        } catch (IOException e)
        {
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
        // timeout argument is in milliseconds, converted to milliseconds
        if (currentlyAcceptingConnections)
            return;

        currentlyAcceptingConnections = true;

        int connectionTimeout_ms = connectionTimeout_s * 1000;

        new ConnectionAcceptor(connectionTimeout_ms).getClients();
    }

    public void startTournament(int numberOfChallenges)
    {
        for(int i=0; i<numberOfChallenges; i++)
        {
            if(i != 0){
                OutputAdapter.sendWaitForChallengeMessage(clientConnections);
            }

            Challenge challenge=new Challenge(clientConnections);
            OutputAdapter.sendNewChallengeMessage(clientConnections, i, challenge.getTotalChallengeRounds());
            challenge.play();
        }

        OutputAdapter.sendEndOfChallengesMessage(clientConnections);
    }

    public void finalize() {
        // TODO: close all connections
    }

    class ConnectionAcceptor {
        int connectionAcceptanceTimeout;

        ConnectionAcceptor(int connectionTimeout) {
            connectionAcceptanceTimeout = connectionTimeout;
        }

        public void getClients() {
            try
            {
                serverSocket.setSoTimeout(connectionAcceptanceTimeout);
            } catch (SocketException e)
            {
                System.out.println("Connection protocol error on port " + getPort());
            }

            boolean listening = true;
            while (listening)
            {
                try
                {
                    Socket newClient = serverSocket.accept();
                    TournamentPlayer client =
                            new TournamentPlayer(newClient);

                    if (client.canEnterTournament())
                    {
                        synchronized (clientConnections)
                        {
                            clientConnections.add(client);
                        }
                    }
                    Thread configureClient = new Thread(client);
                    configureClient.start();
                }
                catch (SocketTimeoutException e)
                {
                    System.out.println("END LISTENING");
                    listening=false;
                }
                catch (IOException e)
                {
                    System.err.println("Exception caught or problem listening on port " + getPort());
                    System.exit(-1);
                }
            }

            currentlyAcceptingConnections = false;

            synchronized (clientConnections)
            {
                for (int i = 0; i < clientConnections.size(); i++)
                {
                    if (!clientConnections.get(i).isAuthenticated())
                    {
                        clientConnections.remove(i--);
                    }
                }
            }
        }
    }
}

