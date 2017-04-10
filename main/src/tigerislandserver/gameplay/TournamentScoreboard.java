package tigerislandserver.gameplay;

import tigerisland.player.Player;
import tigerisland.player.PlayerID;
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
    public static final int TRUETIE = 10;
    public static final int OPPONENTCOULDNOTBUILD = 2;
    public static final int OPPONENTFORFEITED = 1;
    public static final int LOSTBECAUSEUSEDALLTWO = -1;
    public static final int OPPONENTWONTIEBREAKER = -2;
    public static final int OPPONENTHADHIGHERSCORE = -5;
    public static final int COULDNTBUILDBUTPLAYEDSPECIAL = -10;
    public static final int COULDNTBUILDANDPLAYEDNOSPECIALS = -200;
    public static final int YOUFORFEITED = -500;

    public static Challenge tourneyRepresentation;
    public static Map<PlayerID, Integer> tournamentPlayerScores;
    public static ArrayList<TournamentScoreboardData> playersInformation;


    public TournamentScoreboard(){
        tournamentPlayerScores = new HashMap<>();

    }

    public int getPlayerScore(PlayerID pID){
        if (tournamentPlayerScores.get(pID)!=null) {
            return tournamentPlayerScores.get(pID);
        }

        return 0;
    }

    public void addNewPlayer(PlayerID pID){
        tournamentPlayerScores.put(pID, 0);
    }

    public void resetPlayerScore(PlayerID pID){
        tournamentPlayerScores.put(pID, 0);
    }

    public void setTournamentScoreboardData(ArrayList<TournamentScoreboardData> playerData){
        playersInformation = playerData;

    }

    // kind of like the main driver method
    public boolean updateScoresAfterRoundEnded(int roundNumberThatEnded){
        ArrayList<ArrayList<TournamentPlayer>> allPlayerMatchups = getTournamentPlayerMatchups(roundNumberThatEnded);

        // grabs each individual player matchup
        for(int i = 0; i < allPlayerMatchups.size(); i++){
            ArrayList<TournamentPlayer> playerMatchup = allPlayerMatchups.get(i);
            TournamentPlayer player1 = playerMatchup.get(0);
            TournamentPlayer player2 = playerMatchup.get(1);
        }
        return false;
    }

    // need to get the match object
    public ArrayList<ArrayList<TournamentPlayer>> getTournamentPlayerMatchups(int roundNumber){

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

    // main "driver" that determines what we are ading in
    // if this is called they won for a valid reason
    public boolean updateTournamentScoresForValidWin(ArrayList<TournamentScoreboardData> playerData){
        setTournamentScoreboardData(playerData);

        TournamentScoreboardData player1Data = playersInformation.get(0);
        TournamentScoreboardData player2Data = playersInformation.get(1);
        Player player1 = player1Data.getPlayer();
        Player player2 = player2Data.getPlayer();

        // then check if they won by placing all of two pieces
        int playerIndexOfWhoWonByUsingAllOfTwo = didAPlayerWinByUsingAllOfTwo(player1, player2);

        if(playerIndexOfWhoWonByUsingAllOfTwo == 0){
            // add 200 to player 1, take one away from player2
            addScore(player1.getId(), USEDALLOFTWO);
            addScore(player2.getId(), LOSTBECAUSEUSEDALLTWO);
            return true;
        }
        else if(playerIndexOfWhoWonByUsingAllOfTwo == 1){
            addScore(player2.getId(), USEDALLOFTWO);
            addScore(player1.getId(), LOSTBECAUSEUSEDALLTWO);
            return true;
        }


        if (wasThereAScoreTie()){
            if(isItATrueTie()){
                addScore(player1.getId(), TRUETIE);
                addScore(player2.getId(), TRUETIE);
                return true;
            }
            else{
                int indexOfPlayerWhoWonTiebreaker = whichPlayerWonTieBreaker(player1, player2);

                if (indexOfPlayerWhoWonTiebreaker == 0){
                    addScore(player1.getId(), WONTIEBREAKER);
                    addScore(player2.getId(), OPPONENTWONTIEBREAKER);
                    return true;
                }
                else if (indexOfPlayerWhoWonTiebreaker == 1){
                    addScore(player2.getId(), WONTIEBREAKER);
                    addScore(player2.getId(), OPPONENTWONTIEBREAKER);
                    return true;
                }
            }

        }
        else{
            int winningPlayerIndex = whichPlayerHadTheHighestScore();
            if (winningPlayerIndex == 0){
                addScore(player1.getId(), HIGHESTSCORE);
                addScore(player2.getId(), OPPONENTHADHIGHERSCORE);
                return true;
            }
            else if (winningPlayerIndex == 1){
                addScore(player2.getId(), HIGHESTSCORE);
                addScore(player1.getId(), OPPONENTHADHIGHERSCORE);
                return true;
            }
        }
        return false;
    }

    public int didAPlayerWinByUsingAllOfTwo(Player player1, Player player2){
        if(didPlayerUseAllOfTwo(player1)){
            return 0;
        }
        else if(didPlayerUseAllOfTwo(player2)){
            return 1;
        }

        return -1;
    }

    public boolean didPlayerUseAllOfTwo(Player player){
        int villagersLeft = player.getVillagerCount();
        int totorosLeft = player.getTotoroCount();
        int tigersLeft = player.getTigerCount();

        if ((villagersLeft == 0) && (totorosLeft == 0)){
            return true;
        }
        else if ((villagersLeft == 0) && (tigersLeft == 0)){
            return true;
        }

        else if ((totorosLeft == 0) && (tigersLeft == 0)){
            return true;
        }

        return false;
    }

    // -1 will be returned if there is a tie
    public int whichPlayerHadTheHighestScore(){
        TournamentScoreboardData player1Data = playersInformation.get(0);
        TournamentScoreboardData player2Data = playersInformation.get(1);

        int player1Score = player1Data.getFinalScore();
        int player2Score = player2Data.getFinalScore();

        int highestScoringPlayerIndex = -1;
        if(player1Score > player2Score){
            highestScoringPlayerIndex = 0;
        }
        else if(player2Score > player1Score){
            highestScoringPlayerIndex = 1;
        }
        return highestScoringPlayerIndex;
    }

    public boolean wasThereAScoreTie(){
        TournamentScoreboardData player1Data = playersInformation.get(0);
        TournamentScoreboardData player2Data = playersInformation.get(1);

        int player1Score = player1Data.getFinalScore();
        int player2Score = player2Data.getFinalScore();

        if (player1Score == player2Score) {
            return true;
        }

        return false;
    }

    public boolean isItATrueTie(){
        Player player1 = playersInformation.get(0).getPlayer();
        Player player2 = playersInformation.get(1).getPlayer();

        if(wasThereAScoreTie()){
            boolean totoros = player1.getTotoroCount() == player2.getTotoroCount();
            boolean tigers = player1.getTigerCount() == player2.getTigerCount();
            boolean villagers = player1.getVillagerCount() == player2.getVillagerCount();

            if(totoros && tigers && villagers){
                return true;
            }
        }
        return false;
    }

    public int whichPlayerWonTieBreaker(Player player1, Player player2){
        int winningPlayerIndex = -1;


        if (player1.getTotoroCount() < player2.getTotoroCount()){
            winningPlayerIndex = 0;
        }
        else if(player2.getTotoroCount() < player1.getTotoroCount()){
            winningPlayerIndex = 1;
        }
        else {
            // totoros coutns are tied
            if(player1.getTigerCount() < player2.getTigerCount()){
                winningPlayerIndex = 0;
            }
            else if(player2.getTigerCount() < player1.getTigerCount()){
                winningPlayerIndex = 1;
            }
            else{
                //tiger counts are tied
                if (player1.getVillagerCount() < player2.getVillagerCount()){
                    winningPlayerIndex = 0;
                }
                else if(player2.getVillagerCount() < player1.getVillagerCount()){
                    winningPlayerIndex = 1;
                }
            }
        }
        return winningPlayerIndex;
        //ArrayList<ArrayList<TournamentPlayer>> playerMatchupsPerRound = tourneyRepresentation.getPlayerMatchups(roundNumber);
    }

    public boolean didPlayerPlayAtLeastOneTotoroOrTiger(Player player){
        if((player.getTotoroCount() < 3) || (player.getTigerCount() < 2)){
            return true;
        }
        return false;
    }

    public boolean playerMadeInvalidBuild(ArrayList<TournamentPlayer> players){
        PlayerID pIDOfPersonWhoMadeInvalidBuild = players.get(0).getID();
        PlayerID pIDOFPersonWhoWins = players.get(1).getID();


        addScore(pIDOfPersonWhoMadeInvalidBuild, YOUFORFEITED);
        addScore(pIDOFPersonWhoWins, OPPONENTFORFEITED);

        return true ;
    }

    public boolean playerPlacedInvalidTile(ArrayList<TournamentPlayer> players){
        PlayerID pIDOfPersonWhoPlacedInvalidTile = players.get(0).getID();
        PlayerID pIDOFPersonWhoWins = players.get(1).getID();


        addScore(pIDOfPersonWhoPlacedInvalidTile, YOUFORFEITED);
        addScore(pIDOFPersonWhoWins, OPPONENTFORFEITED);

        return true ;
    }

    public boolean playerTimedOut(ArrayList<TournamentPlayer> players){
        PlayerID pIDOfPersonWhoPlacedInvalidTile = players.get(0).getID();
        PlayerID pIDOFPersonWhoWins = players.get(1).getID();


        addScore(pIDOfPersonWhoPlacedInvalidTile, YOUFORFEITED);
        addScore(pIDOFPersonWhoWins, OPPONENTFORFEITED);

        return true ;
    }

    public boolean playerWasUnableToBuildAndPlacedNoSpecialPieces(ArrayList<TournamentPlayer> players){
        PlayerID pIDOfPersonWhoCouldntBuild = players.get(0).getID();
        PlayerID pIDOFPersonWhoWins = players.get(1).getID();


        addScore(pIDOfPersonWhoCouldntBuild, COULDNTBUILDANDPLAYEDNOSPECIALS);
        addScore(pIDOFPersonWhoWins, OPPONENTCOULDNOTBUILD);

        return true ;
    }

    public boolean playerWasUnableToBuildAndPlacedSpecialPiece(ArrayList<TournamentPlayer> players){
        PlayerID pIDOfPersonWhoCouldntBuild = players.get(0).getID();
        PlayerID pIDOFPersonWhoWins = players.get(1).getID();


        addScore(pIDOfPersonWhoCouldntBuild, COULDNTBUILDBUTPLAYEDSPECIAL);
        addScore(pIDOFPersonWhoWins, OPPONENTCOULDNOTBUILD);

        return true ;
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
