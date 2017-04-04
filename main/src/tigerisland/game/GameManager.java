package tigerisland.game;


import tigerisland.board.*;
import tigerisland.hex.Hex;
import tigerisland.piece.*;
import tigerisland.player.Player;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.*;
import tigerisland.terrains.*;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;

import java.util.ArrayList;

public class GameManager {
    static int PLAYER_COUNT = 2;

    private ArrayList<Player> players;
    private int playerIndex;
    private HexBoard gameBoard;
    private ScoreManager scoreKeeper;
    private PieceBoard pieces;
    private SettlementBoard settlements;
    private TilePlacementController tilePlacer;
    private BuildController buildController;

    public GameManager(){
        gameBoard = new HexBoard();
        scoreKeeper = new ScoreManager();

        initializePlayers();
        initializeSettlementBoard();
        placeStartingHexes();

        tilePlacer = new TilePlacementController(gameBoard, settlements, pieces);
        buildController = new BuildController();
    }

    private void initializePlayers(){
        players = new ArrayList<>();
        for(int i = 0; i < PLAYER_COUNT; ++i){
            Player newPlayer = new Player();
            players.add(newPlayer);
            scoreKeeper.addNewPlayer(newPlayer.getId());
        }
    }

    private void initializeSettlementBoard(){
        pieces = new PieceBoardImpl();
        settlements = new LazySettlementBoard(pieces);
    }

    private void placeStartingHexes() {
        Location centerLocation = new Location(0,0,0);
        Hex startingVolcano = new Hex(Volcano.getInstance());

        gameBoard.placeHex(centerLocation, startingVolcano);

        Hex startingJungle = new Hex(Jungle.getInstance());
        gameBoard.placeHex(centerLocation.getAdjacent(Orientation.getNorthWest()), startingJungle);

        Hex startingLake = new Hex(Lake.getInstance());
        gameBoard.placeHex(centerLocation.getAdjacent(Orientation.getNorthEast()), startingLake);

        Hex startingGrassland = new Hex(Grassland.getInstance());
        gameBoard.placeHex(centerLocation.getAdjacent(Orientation.getSouthEast()), startingGrassland);

        Hex startingRocky = new Hex(Rocky.getInstance());
        gameBoard.placeHex(centerLocation.getAdjacent(Orientation.getSouthWest()), startingRocky);
    }

    public boolean placeTile(Tile tile, Location location) {
        // TODO: place tile logic. Need player ID?
        return false;
    }

    public boolean foundSettlement(Location location){
        // TODO: build action logic. Need player ID?
        return false;
    }

    public boolean expandSettlement(Location location){
        // TODO: build action logic. Need player ID?
        return false;
    }

    public boolean buildTotoro(Location location){
        // TODO: build action logic. Need player ID?
        return false;
    }

    public boolean buildTiger(Location location){
        // TODO: build action logic. Need player ID?
        return false;
    }

    public void endGame(){
        // TODO
    }

    public GameResults returnResults(){
        GameResults results = new GameResults();
        return results;
    }
}
