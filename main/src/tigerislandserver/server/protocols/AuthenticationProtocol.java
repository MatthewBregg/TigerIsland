package tigerislandserver.server.protocols;


import java.util.HashMap;
import tigerislandserver.server.TextFileReader;

import javax.xml.soap.Text;

/**
 * Created by christinemoore on 4/1/17.
 */
public class AuthenticationProtocol {
    private static final int WELCOME = 0;
    private static final int LOGIN = 1;
    private static final int WAIT = 2;
    private HashMap<String, String> usernamesAndPasswords;


    private static String tournamentPassword = "MADMAX";

    public AuthenticationProtocol(String usernamesAndPasswordsFileName){
        TextFileReader tfr = new TextFileReader(usernamesAndPasswordsFileName);
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
    }


    // constructor for if password happens to change per dave's instructions
    public AuthenticationProtocol(String usernamesAndPasswordsFileName, String tournamentPassword) {
        TextFileReader tfr = new TextFileReader(usernamesAndPasswordsFileName);
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
        this.tournamentPassword = tournamentPassword;
    }

    public String sendStartMessageString(){
        return "WELCOME TO ANOTHER ADDITION OF THUNDERDOME!";
    }

    public String sendTourmentLoginConfimation(){
        return "TWO SHALL ENTER, ONE SHALL LEAVE";
    }

    public String tryToLogin(String username, String password){
        int potentialPlayerID =0;

        if (doesPasswordMatchUsername(username, password)){

            return "WAIT FOR THE TOURNAMENT TO BEGIN " + potentialPlayerID;
        }
        return "Passwords Do Not Match";
    }

    private boolean doesPasswordMatchUsername(String username, String password) {

        if (usernamesAndPasswords.containsKey(username) && usernamesAndPasswords.get(username).equals(password)) {
            return true;
        }
        return false;
    }



}
