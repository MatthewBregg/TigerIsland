package tigerisland.game;


import tigerisland.board.*;
import tigerisland.hex.Hex;
import tigerisland.piece.*;
import tigerisland.player.Player;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.*;
import tigerisland.terrains.*;
import tigerisland.tile.Orientation;

import java.util.ArrayList;

public class GameManager {
    static int PLAYER_COUNT = 2;

    private ArrayList<Player> players;
    private HexBoard gameBoard;
    private ScoreManager scoreKeeper;
    private PieceBoard pieces;
    private SettlementBoard settlements;
    private TilePlacementController tilePlacer;

    public GameManager(){
        gameBoard = new HexBoard();
        scoreKeeper = new ScoreManager();

        initializePlayers();
        initializeSettlementBoard();
        placeStartingHexes();

        tilePlacer = new TilePlacementController(gameBoard, settlements, pieces);
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
}
