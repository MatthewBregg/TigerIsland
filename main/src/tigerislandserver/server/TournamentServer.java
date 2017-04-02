package tigerislandserver.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by pgallen on 3/26/2017.
 */
public class TournamentServer {
    private ServerSocket serverSocket;
    private ArrayList<TournamentPlayer> clientConnections = new ArrayList<>();
    private boolean currentlyAcceptingConnections;

    public TournamentServer(){
        currentlyAcceptingConnections = false;

        try{
            serverSocket = new ServerSocket(0);
        } catch(IOException e){
            System.out.println("Exception while listening for a connection on port " + getPort());
            System.out.println(e.getMessage());
        }
    }

    public int getPort(){
        int portNumber = -1;
        if(serverSocket != null)
            portNumber = serverSocket.getLocalPort();
        return portNumber;
    }

    public void acceptConnections(int connectionTimeout_s){
        // Running accept() on separate thread should allow socket.close() to be called from this thread
        // timeout argument is in milliseconds, converted to milliseconds
        if(currentlyAcceptingConnections)
            return;

        currentlyAcceptingConnections = true;

        connectionTimeout_s *= 1000;

        new Thread(new ConnectionAcceptor(connectionTimeout_s)).start();
    }

    public void finalize(){
        // TODO: close all connections
    }

    class ConnectionAcceptor implements Runnable{
        int connectionAcceptanceTimeout;

        ConnectionAcceptor(int connectionTimeout){
            connectionAcceptanceTimeout = connectionTimeout;
        }

        public void run(){
            try{
                serverSocket.setSoTimeout(connectionAcceptanceTimeout);
            }catch(SocketException e){
                System.out.println("Connection protocol error on port " + getPort());
            }

            boolean listening = true;
            while(listening){
                try{
                    Socket newClient = serverSocket.accept();
                    TournamentPlayer client =
                            new TournamentPlayer(newClient);

                    synchronized (clientConnections){
                        clientConnections.add(client);
                    }

                    Thread configureClient = new Thread(client);
                    configureClient.start();
                } catch(IOException e){
                    System.out.println("Exception caught or problem listening on port " + getPort());
                }
            }

            currentlyAcceptingConnections = false;
        }
    }
}

