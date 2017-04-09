package tigerislandserver;

import tigerislandserver.server.TournamentServer;

public class RunTournamentServer
{
    public static void main(String[] args)
    {
        if(args.length != 6 || !args[0].matches("^\\d+$") || !args[3].matches("^\\d+$") || !args[4].matches("^\\d+$") || !args[5].matches("^\\d+$"))
        {
            System.err.println("Invalid arguments: Please adjust your input to match the following:");
            System.err.println("java runTournamentServer <Number of challenges> <Tournament Password> <File path to file of usernames and passwords> <Seconds permitted to connect to server> <port number> <random seed>");
        }

        TournamentVariables tv = TournamentVariables.getInstance();
        tv.setNumberOfChallenges(Integer.parseInt(args[0]));
        tv.setTournamentPassword(args[1]);
        tv.setUnsernamePasswordFile(args[2]);
        tv.setRandomSeed(Integer.parseInt(args[5]));

        TournamentServer ts = new TournamentServer(Integer.parseInt(args[4]));
        ts.acceptConnections(Integer.parseInt(args[3]));
        ts.startTournament(Integer.parseInt(args[0]));
    }
}
