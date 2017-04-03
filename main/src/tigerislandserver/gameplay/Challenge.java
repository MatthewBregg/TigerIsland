package tigerislandserver.gameplay;

import tigerisland.tile.Tile;
import tigerisland.tile.TileDeck;
import tigerislandserver.gameplay.identifiers.ChallengeID;
import tigerislandserver.server.TournamentPlayer;
import tigerislandserver.server.TournamentServer;

import java.util.ArrayList;

public class Challenge {
    private Scheduler schedule;
    private ArrayList<TournamentPlayer> playerList;
    private long challengeID;
    private TournamentScoreboard scoreboard;
    private ArrayList<Match> currentRoundMatches;
    private int roundNumber;
    private long currentSeed;

    public Challenge(ArrayList<TournamentPlayer> participants){
        playerList = participants;
        challengeID = ChallengeID.getID();
        scoreboard = new TournamentScoreboard();
        schedule = new Scheduler(playerList.size());
        currentRoundMatches = new ArrayList<>();
        roundNumber = 0;
    }

    public void setMatchupType(ScheduleType matchmaker){
        schedule.setTournamentType(matchmaker);
    }

    public void playNextRound(){
        if(!(getRoundsRemaining() > 0))
            return;

        ++roundNumber;
        setupRound();

        for(Match m : currentRoundMatches)
            m.start();
    }

    private void setupRound(){
        currentRoundMatches.clear();

        ArrayList<Tile> roundTiles = generateTileArray();
        ArrayList<ArrayList<TournamentPlayer>> roundMatchups = getPlayerMatchups(roundNumber);

        for(ArrayList<TournamentPlayer> matchup : roundMatchups){
            Match newMatch = new Match(matchup, roundTiles);
            currentRoundMatches.add(newMatch);
        }
    }

    private ArrayList<Tile> generateTileArray() {
        currentSeed = generateSeed();
        TileDeck tileDeck = new TileDeck(currentSeed);

        ArrayList<Tile> tiles = new ArrayList<>();
        int totalTiles = tileDeck.getCount();
        for(int i = 0; i < totalTiles; ++i){
            Tile t = tileDeck.drawTile();
            tiles.add(t);
        }

        return tiles;
    }

    private long generateSeed(){
        long randomSeed;
        randomSeed = Math.round(Math.random());
        return randomSeed;
    }

    private ArrayList<ArrayList<TournamentPlayer>> getPlayerMatchups(int round){
        ArrayList<ArrayList<TournamentPlayer>> playerMatchups = new ArrayList<>();
        ArrayList<Matchup> matchupIndexes = schedule.getMatchups(round);

        for(Matchup m : matchupIndexes){
            int p1Index = m.getPlayer1Index();
            TournamentPlayer player1 = playerList.get(p1Index);

            int p2Index = m.getPlayer2Index();
            TournamentPlayer player2 = playerList.get(p2Index);

            ArrayList<TournamentPlayer> newMatchup = new ArrayList<>();
            newMatchup.add(player1);
            newMatchup.add(player2);

            playerMatchups.add(newMatchup);
        }

        return playerMatchups;
    }

    public int getCurrentRound(){
        return roundNumber;
    }

    public int getRoundsRemaining(){
        return (schedule.getTotalRounds() - roundNumber);
    }

    public int getTotalChallengeRounds(){
        return schedule.getTotalRounds();
    }
}
