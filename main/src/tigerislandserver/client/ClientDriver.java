package tigerislandserver.client;

/**
 * Created by christinemoore on 3/23/17.
 */

public class ClientDriver {

    public static void main(String args[]){
        // params will need to be changed according to Dave's specs
        ClientConnection clientConnection = new ClientConnection(1234, "localhost");

        try{
            clientConnection.waitToStart();
            clientConnection.startGameplay();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
