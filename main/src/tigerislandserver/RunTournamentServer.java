package tigerislandserver;

import tigerisland.datalogger.LoggerFactory;
import tigerislandserver.server.TournamentServer;
import ui.swing.TournamentUI;

import java.sql.Connection;

import static java.lang.Thread.sleep;

public class RunTournamentServer
{
    public static void main(String[] args)
    {
        LoggerFactory.clearTables();
        Connection connection = LoggerFactory.getDbConnection();

        if(args.length != 6 || !args[0].matches("^\\d+$") || !args[3].matches("^\\d+$") || !args[4].matches("^\\d+$") || !args[5].matches("^\\d+$"))
        {
            System.err.println("Invalid arguments: Please adjust your input to match the following:");
            System.err.println("java runTournamentServer <Number of challenges> <Tournament Password> <File path to file of usernames and passwords> <Seconds permitted to connect to server> <port number> <random seed>");
        }

        int numberOfTournamentToPlay = Integer.parseInt(args[1]);

        new Thread(new Runnable() {
            @Override
            public void run() {
                TournamentUI.startScoreBoard(connection, numberOfTournamentToPlay);
            }
        }).start();

                TournamentVariables tv = TournamentVariables.getInstance();
        tv.setNumberOfChallenges(Integer.parseInt(args[0]));
        tv.setTournamentPassword(args[1]);
        tv.setUnsernamePasswordFile(args[2]);
        tv.setRandomSeed(Integer.parseInt(args[5]));

        System.out.println("Input Arguments were : Seed: " + tv.getRandomSeed() + " Challege Num: " + tv.getNumberOfChallenges());

        TournamentServer ts = new TournamentServer(Integer.parseInt(args[4]));
        ts.acceptConnections(Integer.parseInt(args[3]));
        if(ts.getPlayerCount() > 1) {
            ts.startTournament(Integer.parseInt(args[0]));
        } else {
            System.out.println("Not enough players!");
        }
    }

    private static void waitForAllClientToConnect(int delay) throws InterruptedException {
        sleep(delay * 1000);
    }
}
