package tigerislandserver.gameplay;

import tigerisland.tile.Tile;
import tigerisland.tile.TileDeck;
import tigerislandserver.server.TournamentPlayer;

import java.util.ArrayList;

public class Challenge {
    private Scheduler schedule;
    private ArrayList<TournamentPlayer> playerList;
    private TournamentScoreboard scoreboard;
    public ArrayList<Tile> currentDeck;
    private int roundNumber;
    private long currentSeed;

    public Challenge(ArrayList<TournamentPlayer> participants){
        playerList = participants;
        scoreboard = new TournamentScoreboard();
        schedule = new Scheduler(playerList.size());
        currentDeck = new ArrayList<Tile>();
        roundNumber = 0;
    }

    public void setMatchupType(ScheduleType matchmaker){
        schedule.setTournamentType(matchmaker);
    }

    public void playRound(){
        // initiate next round of matchups
    }

    public void setupRound(){
        generateTileArray();
    }

    private void generateTileArray() {
        currentSeed = generateSeed();
        TileDeck tileDeck = new TileDeck(currentSeed);

        ArrayList<Tile> tiles = new ArrayList<Tile>();
        int totalTiles = tileDeck.getCount();
        for(int i = 0; i < totalTiles; ++i){
            Tile t = tileDeck.drawTile();
            tiles.add(t);
        }

        currentDeck = tiles;
    }

    private long generateSeed(){
        long randomSeed;
        randomSeed = Math.round(Math.random());
        return randomSeed;
    }

    private ArrayList<TournamentPlayer> getMatchPlayers(){
        ArrayList<TournamentPlayer> matchPlayers = new ArrayList<TournamentPlayer>();

        return matchPlayers;
    }

    public int getCurrentRound(){
        return roundNumber;
    }

    public int getRoundsRemaining(){
        return (schedule.getTotalRounds() - roundNumber);
    }
}
