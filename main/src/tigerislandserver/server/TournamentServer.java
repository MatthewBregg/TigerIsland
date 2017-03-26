package tigerislandserver.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Created by pgallen on 3/26/2017.
 */
public class TournamentServer {
    private ServerSocket serverSocket = null;
    ArrayList<Socket> clientConnections = new ArrayList<>();

    public TournamentServer(){
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

    public void getConnections(){
        // TODO: threaded client connection implementation
    }

    public void finalize(){
        // TODO: close all connections
    }
}
