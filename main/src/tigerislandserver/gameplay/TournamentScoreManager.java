package tigerislandserver.gameplay;

import tigerisland.player.PlayerID;
import tigerislandserver.server.TournamentPlayer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by christinemoore on 4/11/17.
 */
public class TournamentScoreManager {
    public static Map<PlayerID, Integer> currentChallengeScores;
    public static Map<PlayerID, Integer> overallTournamentScores;
    public static ArrayList<TournamentPlayer> listOfPlayers;


    public TournamentScoreManager(ArrayList<TournamentPlayer> players){
        listOfPlayers = players;
        overallTournamentScores = new HashMap<>();
    }

    public Map<PlayerID, Integer> getOverallScores(){
        return overallTournamentScores;
    }

    public void setEndedChallengeScores(Map<PlayerID, Integer> inputScores){
        currentChallengeScores = inputScores;
    }

    public void initializeOverallTournamentScores(){
        if (listOfPlayers.size() == 0) {
            listOfPlayers = new ArrayList<>();
        }
        else{
            for(int i = 0; i < listOfPlayers.size(); i++){
                PlayerID pID = listOfPlayers.get(i).getID();
                overallTournamentScores.put(pID, 0);
            }
        }
    }

    public void addToAllPlayersScore(){
        int currentChallengeScoresSize = currentChallengeScores.size();
        int overallTournamentScoresSize = overallTournamentScores.size();
        int howManyPlayers = listOfPlayers.size();

        int smallerSize = Math.min(howManyPlayers, Math.min(currentChallengeScoresSize, overallTournamentScoresSize));

        boolean sameSize = currentChallengeScoresSize == overallTournamentScoresSize;
        boolean sameSize2 = overallTournamentScoresSize == howManyPlayers;
        boolean sameSize3 = currentChallengeScoresSize == howManyPlayers;

        if (sameSize == sameSize2 == sameSize3){
            for (int i = 0; i < currentChallengeScoresSize; i++ ){
                PlayerID playerID = listOfPlayers.get(i).getID();
                int newScoreToAdd = currentChallengeScores.get(playerID);
                int currentTourneyScore = overallTournamentScores.get(playerID);

                int cumulativeScore = newScoreToAdd + currentTourneyScore;

                overallTournamentScores.put(playerID, cumulativeScore);
            }
        }
        else{
            for (int i = 0; i < smallerSize; i++ ){
                PlayerID playerID = listOfPlayers.get(i).getID();
                int newScoreToAdd = currentChallengeScores.get(playerID);
                int currentTourneyScore = overallTournamentScores.get(playerID);

                int cumulativeScore = newScoreToAdd + currentTourneyScore;

                overallTournamentScores.put(playerID, cumulativeScore);
            }
        }
    }




}
