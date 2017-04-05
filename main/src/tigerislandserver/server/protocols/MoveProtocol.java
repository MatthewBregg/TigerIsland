package tigerislandserver.server.protocols;

/**
 * Created by christinemoore on 4/1/17.
 */
public class MoveProtocol {
    private int gameID;
    private int seconds;
    private int timeOut;

    public MoveProtocol(){

    }

    public String formateWordSecond(){
        if (timeOut > 1){
            return "SECONDS";
        }
        else return "SECOND";
    }


}
