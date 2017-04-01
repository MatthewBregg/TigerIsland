package tigerislandserver.server.protocols;

/**
 * Created by christinemoore on 4/1/17.
 */
public class AuthenticationProtocol {
    private final String tournamentPassword = "MADMAX";

    public AuthenticationProtocol() {
    }

    private boolean enteredCorrectTournamentPassword(String password){
        return password.equals(tournamentPassword);
    }

    private boolean doesPasswordMatchUsername(String authenticationString) {
    }


}
