package tigerisland.game;


import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.datalogger.DataLogger;
import tigerisland.hex.Hex;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.player.Player;
import tigerisland.player.PlayerID;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.SettlementBoard;
import tigerisland.terrains.*;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class GameManager {

    private DataLogger logger;
    private ArrayList<Player> players;
    private int playerIndex;
    private HexBoard gameBoard;
    private ScoreManager scoreKeeper;
    private PieceBoard pieces;
    private SettlementBoard settlements;
    private TilePlacementController tilePlacer;
    private BuildController buildController;
    private int tileID = 1;
    private int tilesDrawn;

    public GameManager(ArrayList<Player> players, DataLogger logger){
        this.logger = logger;
        if (players.size() != 2)
        {
            throw new IllegalArgumentException("Exactly two players required");
        }

        gameBoard = new HexBoard();

        this.players=players;

        tilesDrawn = 0;

        initializeScoreKeeper();
        initializeSettlementBoard();
        placeStartingHexes();

        tilePlacer = new TilePlacementController(gameBoard, settlements, pieces);
        buildController = new BuildController(gameBoard, pieces, settlements, scoreKeeper);
    }


    public static GameManager injectStuffOnlyForTesting(HexBoard hexBoard, ArrayList<Player> players, PieceBoard pieces,
                                                        DataLogger logger){
       return new GameManager(players, hexBoard, pieces, logger);
    }


    private void initializeScoreKeeper()
    {
        scoreKeeper = new ScoreManager();

        for (int i = 0; i < players.size(); i++) {
            scoreKeeper.addNewPlayer(players.get(i).getId());
        }
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public int getTilesDrawn(){
        return tilesDrawn;
    }

    public BuildController getBuildController(){
        return buildController;
    }

    private void initializeSettlementBoard(){
        pieces = new PieceBoardImpl();
        settlements = new LazySettlementBoard(pieces);
    }

    private void placeStartingHexes() {
        int startingTileId = -2;

        Location centerLocation = new Location(0,0,0);
        Hex startingVolcano = new Hex(startingTileId,Volcano.getInstance());


        gameBoard.placeHex(centerLocation, startingVolcano);

        Hex startingJungle = new Hex(startingTileId,Jungle.getInstance());
        gameBoard.placeHex(centerLocation.getAdjacent(Orientation.getNorthWest()), startingJungle);

        Hex startingLake = new Hex(startingTileId,Lake.getInstance());
        gameBoard.placeHex(centerLocation.getAdjacent(Orientation.getNorthEast()), startingLake);

        Hex startingGrassland = new Hex(startingTileId,Grassland.getInstance());
        gameBoard.placeHex(centerLocation.getAdjacent(Orientation.getSouthEast()), startingGrassland);

        Hex startingRocky = new Hex(startingTileId,Rocky.getInstance());
        gameBoard.placeHex(centerLocation.getAdjacent(Orientation.getSouthWest()), startingRocky);
    }

    public boolean placeTile(Tile tile, Location location) {
        tilesDrawn++;
        logger.writePlacedTileMove(new PlayerID(), location, tile.getOrientation(), tile.toString());
        // TODO : mbregg
        boolean result = tilePlacer.placeTile(tile, location);
        logger.writeMoveResult("Tile placement " + location + " " + result + " , " + tile + " SIZE OF BOARD IS " + gameBoard.getSize());
        if ( !result ) {
            logger.writeInvalidMoveAttempted(new PlayerID(),"Failed to place tile!");
        }
        return result;
    }

    public boolean foundSettlement(Location location, Player player){
        BuildActionData buildAction = new BuildActionData.Builder()
                .withHexLocation(location)
                .withPlayer(player)
                .build();
        logger.writeFoundedSettlementMove(player.getId(),location);
        BuildActionResult result = buildController.foundSettlement(buildAction);
        logger.writeMoveResult("FoundSettlement " + location + " " + result.successful + " " + result.errorMessage);
        if ( !result.successful ) {
            logger.writeInvalidMoveAttempted(player.getId(),result.errorMessage);
        }
        return result.successful;
    }

    public boolean expandSettlement(Location locationExpandingFrom, Terrain terrainToExpandTo, Player player){
        BuildActionData buildAction = new BuildActionData.Builder()
                .withSettlementLocation(locationExpandingFrom)
                .withTerrain(terrainToExpandTo)
                .withPlayer(player)
                .build();
        logger.writeExpandedSettlementMove(player.getId(),locationExpandingFrom,terrainToExpandTo.toString());
        BuildActionResult result = buildController.expandSettlement(buildAction);
        logger.writeMoveResult("Expand Settlement " + result.successful + " " + result.errorMessage);
        if ( !result.successful ) {
            logger.writeInvalidMoveAttempted(player.getId(),result.errorMessage);
        }
        return result.successful;
    }

    public boolean buildTotoro(Location locationExpandingTo, Player player){
        BuildActionData buildAction = new BuildActionData.Builder()
                .withHexLocation(locationExpandingTo)
                .withPlayer(player)
                .build();
        logger.writePlacedTotoroMove(player.getId(),locationExpandingTo);
        BuildActionResult result = buildController.buildTotoro(buildAction);
        logger.writeMoveResult("Totoroplacement " + locationExpandingTo + " " + result.successful + " " + result.errorMessage);
        if ( !result.successful ) {
            logger.writeInvalidMoveAttempted(player.getId(),result.errorMessage);
        }
        return result.successful;
    }

    public boolean buildTiger(Location locationExpandingTo, Player player){
        BuildActionData buildAction = new BuildActionData.Builder()
                .withHexLocation(locationExpandingTo)
                .withPlayer(player)
                .build();
        logger.writePlacedTigerMove(player.getId(),locationExpandingTo);
        BuildActionResult result = buildController.buildTiger(buildAction);
        logger.writeMoveResult("Tigerplacement " + locationExpandingTo + " " + result.successful + " " + result.errorMessage);
        if ( !result.successful ) {
            logger.writeInvalidMoveAttempted(player.getId(),result.errorMessage);
        }
        return result.successful;
    }

    public boolean totoroOrTigerPlaced(PlayerID pID){
        Player player = players.get(0);
        Player player2 = players.get(1);

        if (player.getId() == pID){
            if((player.getTotoroCount() < 3) || (player.getTigerCount() < 2)){
                return true;
            }
        }
        else if(player2.getId() == pID){
            if((player2.getTotoroCount() < 3) || (player2.getTigerCount() < 2)){
                return true;
            }
        }
        return false;
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

    public Player getPlayer(PlayerID pID)
    {
        for(Player p: players)
        {
            if(p.getId().equals(pID))
            {
                return p;
            }
        }

        return null;
    }

    public ScoreManager getScoreManager()
    {
        return scoreKeeper;
    }

    private GameManager(ArrayList<Player> players , HexBoard hexboard, PieceBoard pieces, DataLogger logger){
        this.logger = logger;
        if (players.size() != 2)
        {
            throw new IllegalArgumentException("Exactly two players required");
        }

        this.gameBoard = hexboard;
        this.pieces = pieces;
        this.settlements = new LazySettlementBoard(pieces);
        this.players = players;

        tilesDrawn = 0;

        initializeScoreKeeper();

        tilePlacer = new TilePlacementController(gameBoard, settlements, pieces);
        buildController = new BuildController(gameBoard, pieces, settlements, scoreKeeper);
    }

    synchronized public int getTileId() {
        ++tileID;
        return tileID;
    }
}
