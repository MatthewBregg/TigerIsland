package tigerislandserver.gameplay;

import tigerisland.player.Player;
import tigerisland.player.PlayerID;
import tigerisland.score.ScoreManager;
import tigerislandserver.server.TournamentPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TournamentScoreboard {
    // we can get rid of all of this,
    // more just for me to remember what all we hae to do
    public static final int USEDALLOFTWO = 200;
    public static final int HIGHESTSCORE = 50;
    public static final int WONTIEBREAKER = 50;
    public static final int TIE = 10;
    public static final int COULDNOTBUILD = 2;
    public static final int FORFEIT = 1;
    public static final int LOSTBECAUSEUSEDALLTWO = -1;
    public static final int OPPONENTWONTIEBREAKER = -2;
    public static final int OPPONENTHADHIGHERSCORE = -5;
    public static final int COULDNTBUILDANDHADTIGERSORTOTOROSELFT = -10;
    public static final int COULDNTBUILDANDPLAYEDNOSPECIALS = -200;
    public static final int ILLEGALORTIMEDOUTLOSS = -500;

    // ** NOTE for all the data needed for this part, phil suggested maybe pull from database
    // if pulling from the database becomes too cumbersome we may just want to write to a file


    // may need this to figure out the final score of a game
    // but the game thread will end sooooo?
    public static ScoreManager scoreManager;


    public static Challenge tourneyRepresentation;

    // my thought with tis is that the touranment scoreboard or some kind of
    // tournament object can also keep track of how many matches there are
    // and we can pull the match we care about based off PID?
    public static ArrayList<Match> allMatches;

    // similar to how ScoreManager is set up but in the scope of the tournament
    public static Map<PlayerID, Integer> tournamentPlayerScores;




    public TournamentScoreboard(){
        tournamentPlayerScores = new HashMap<>();

    }

    public int getPlayerScore(PlayerID pID){
        if (tournamentPlayerScores.get(pID)!=null) {
            return tournamentPlayerScores.get(pID);
        }

        return 0;
    }

    // kind of like the main driver method
    public boolean updateScoresAfterRoundEnded(int roundNumberThatEnded){
        ArrayList<ArrayList<TournamentPlayer>> allPlayerMatchups = getPlayerMatchups(roundNumberThatEnded);

        // grabs each individual player matchup
        for(int i = 0; i < allPlayerMatchups.size(); i++){
            ArrayList<TournamentPlayer> playerMatchup = allPlayerMatchups.get(i);
            TournamentPlayer player1 = playerMatchup.get(0);
            TournamentPlayer player2 = playerMatchup.get(1);


        }
        return false;
    }

    // need to get the match object
    public ArrayList<ArrayList<TournamentPlayer>> getPlayerMatchups(int roundNumber){

        ArrayList<ArrayList<TournamentPlayer>> playerMatchups = tourneyRepresentation.getPlayerMatchups(roundNumber);

        return playerMatchups;
    }


    public void addScore(PlayerID id, int numToAdd) {

        int score = this.getPlayerScore(id);

        if (score != 0) {
            score += numToAdd;
            tournamentPlayerScores.put(id, score);
        }
        else
            tournamentPlayerScores.put(id, numToAdd);
    }

    public boolean didPlayerUseAllOfTwo(PlayerID pID){
        //TODO where do i pull the overall player from,
        // from where is the pID being passed?
        // a player object could also be passed in, for the sake of code i used
        // the pID instead of passing in a player
        // also because the list of player scores here is linked to playerID
        // i felt like it made more sense

        // but for the sake of getting code to run, i just made
        // a dummy player so that I could at least get the logic written
        Player player = new Player();
        int villagersLeft = player.getVillagerCount();
        int totorosLeft = player.getTotoroCount();
        int tigersLeft = player.getTigerCount();

        if ((villagersLeft == 0) && (totorosLeft == 0)){
            int existingPoints = tournamentPlayerScores.get(pID);

            existingPoints += USEDALLOFTWO;

            tournamentPlayerScores.put(pID, existingPoints);
            return true;
        }
        else if ((villagersLeft == 0) && (tigersLeft == 0)){
            int existingPoints = tournamentPlayerScores.get(pID);

            existingPoints += USEDALLOFTWO;

            tournamentPlayerScores.put(pID, existingPoints);
            return true;
        }

        else if ((totorosLeft == 0) && (tigersLeft == 0)){
            int existingPoints = tournamentPlayerScores.get(pID);

            existingPoints += USEDALLOFTWO;

            tournamentPlayerScores.put(pID, existingPoints);
            return true;
        }

        return false;
    }

    public void didPlayerHaveTheHighestScore(int roundNumber){

        ArrayList<ArrayList<TournamentPlayer>> playerMatchupsPerRound = tourneyRepresentation.getPlayerMatchups(roundNumber);

        //get each tourney player




    }

    public void didPlayerWinTieBreaker(int roundNumber){

        ArrayList<ArrayList<TournamentPlayer>> playerMatchupsPerRound = tourneyRepresentation.getPlayerMatchups(roundNumber);

        //get each tourney player




    }

    public void didPlayersTie(int roundNumber){

        ArrayList<ArrayList<TournamentPlayer>> playerMatchupsPerRound = tourneyRepresentation.getPlayerMatchups(roundNumber);

        //get each tourney player




    }

    //TODO this method is supposed to get failiure message
    // needs to find out like if it was timeout, invalid move, or there were no more build moves left
    public boolean reasonForLosing(){
        return false;
    }

    public int[] determinePieceCount(PlayerID pID){
        Player player = new Player();
        int numVillagers = player.getVillagerCount();
        int numTotoros = player.getTotoroCount();
        int numTigers = player.getTigerCount();

        int [] pieceCount = {numVillagers, numTotoros, numTigers};
        return pieceCount;
    }

}
