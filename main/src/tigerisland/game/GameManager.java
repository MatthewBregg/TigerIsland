package tigerisland.game;


import tigerisland.board.*;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.hex.Hex;
import tigerisland.piece.*;
import tigerisland.player.Player;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.*;
import tigerisland.terrains.*;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {

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

        initializeSettlementBoard();
        placeStartingHexes();

        tilePlacer = new TilePlacementController(gameBoard, settlements, pieces);
        buildController = new BuildController(gameBoard, pieces, settlements, scoreKeeper);
    }


    public BuildController getBuildController(){
        return buildController;
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
        return tilePlacer.placeTile(tile, location);
    }

    public boolean foundSettlement(Location location, Player player){
        BuildActionData buildAction = new BuildActionData.Builder()
                .withHexLocation(location)
                .withPlayer(player)
                .build();

        return buildController.foundSettlement(buildAction);
    }

    public boolean expandSettlement(Location locationExpandingFrom, Terrain terrainToExpandTo, Player player){
        BuildActionData buildAction = new BuildActionData.Builder()
                .withSettlementLocation(locationExpandingFrom)
                .withTerrain(terrainToExpandTo)
                .withPlayer(player)
                .build();

        return buildController.expandSettlement(buildAction);
    }

    public boolean buildTotoro(Location locationExpandingTo, Player player){
        BuildActionData buildAction = new BuildActionData.Builder()
                .withHexLocation(locationExpandingTo)
                .withPlayer(player)
                .build();

        return buildController.buildTotoro(buildAction);
    }

    public boolean buildTiger(Location locationExpandingTo, Player player){
        BuildActionData buildAction = new BuildActionData.Builder()
                .withHexLocation(locationExpandingTo)
                .withPlayer(player)
                .build();

        return buildController.buildTiger(buildAction);
    }

    public HexBoard getHexBoard(){
        return gameBoard;
    }

    public PieceBoard getPieceBoard(){
        return pieces;
    }

    public void setPieceBoard(PieceBoard newBoard){
        pieces = newBoard;
    }

    public void setHexBoard(HashMap<Location, Hex> board){
        gameBoard.setBoard(board);
    }

    public void endGame(){
        // TODO
    }

    public GameResults returnResults(){
        GameResults results = new GameResults();
        return results;
    }
}
