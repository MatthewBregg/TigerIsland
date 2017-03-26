package tigerislandserver.server;

import java.net.ServerSocket;

public class TournamentClientConnector implements Runnable{
    ServerSocket serverSocket = null;

    TournamentClientConnector(ServerSocket svrSocket){
        serverSocket = svrSocket;
    }

    public void run(){
        // TODO
    }
}
