package tigerisland.gameplay;


import org.junit.*;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.build_moves.SettlementExpansionUtility;
import tigerisland.build_moves.actions.*;
import tigerisland.build_moves.builds.*;
import tigerisland.build_moves.rules.EnoughVillagersToExpandRule;
import tigerisland.game.BuildController;
import tigerisland.game.GameManager;
import tigerisland.hex.Hex;
import tigerisland.piece.*;
import tigerisland.player.Player;
import tigerisland.player.PlayerID;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.SettlementBoard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.build_moves.actions.MakeBuildAction;
import tigerisland.build_moves.actions.PlaceVillagerOnHexAction;
import tigerisland.build_moves.rules.*;
import tigerisland.piece.PieceBoard;
import tigerisland.score.ScoreManager;
import tigerisland.terrains.Grassland;
import tigerisland.terrains.Jungle;
import tigerisland.terrains.Lake;
import tigerisland.terrains.Rocky;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by christinemoore on 4/5/17.
 */
public class BuildControllerTest {
    public static GameManager gameManager;
    public static BuildActionData buildActionData;
    public static BuildController buildController;
    public static Player currentPlayer;

    @BeforeClass
    public static void initializeGameManagerAndBuildController(){
        gameManager = new GameManager();
        buildController = gameManager.getBuildController();
    }


    @Test
    public void checkThatRockyStartingTilesIsPlaced(){
        HashMap<Location, Hex> hexBoard = gameManager.getHexBoard().getBoard();

        Hex rockyHex = new Hex(Rocky.getInstance());

        Location rockyLocation = new Location(-1, 0 , 1);

        Hex shouldRocky = hexBoard.get(rockyLocation);


        boolean rockCheck = false;

        if(rockyHex.getTerrain() == shouldRocky.getTerrain()){
            rockCheck = true;
        }


        Assert.assertTrue( rockCheck);
    }

    @Test
    public void checkThatLakeStartingTilesISPlaced(){
        HashMap<Location, Hex> hexBoard = gameManager.getHexBoard().getBoard();

        Hex lakeHex = new Hex(Lake.getInstance());

        Location lakeLocation = new Location(1, 0, -1);

        Hex shoudlLake= hexBoard.get(lakeLocation);

        boolean lakeCheck= false;

        if(lakeHex.getTerrain() == shoudlLake.getTerrain()) {
            lakeCheck = true;
        }

        Assert.assertTrue(lakeCheck );
    }

    @Test
    public void checkThatGrassStartingTilesIsPlaced(){
        HashMap<Location, Hex> hexBoard = gameManager.getHexBoard().getBoard();

        Hex grassHex = new Hex(Grassland.getInstance());

        Location grassLocatoin = new Location(0, -1, 1);

        Hex shouldGrass = hexBoard.get(grassLocatoin);

        boolean grassCheck= false;

        if(grassHex.getTerrain() == shouldGrass.getTerrain()){
            grassCheck = true;
        }


        Assert.assertTrue( grassCheck);
    }

    @Test
    public void checkThatJungleStartingTilesArePlaced(){
        HashMap<Location, Hex> hexBoard = gameManager.getHexBoard().getBoard();

        Hex jungleHex = new Hex(Jungle.getInstance());

        Location jungleLocation = new Location(0, 1, -1 );

        Hex shouldJungle = hexBoard.get(jungleLocation);

        boolean jungleCheck = false;

        if(jungleHex.getTerrain() == shouldJungle.getTerrain()){
            jungleCheck = true;
        }

        Assert.assertTrue(jungleCheck);
    }

    @Test
    public void checkThatBuildersExist(){
        SettlementExpansionUtility settlementExpansionUtility = buildController.getSettlementExpansionUtility();
        FoundNewSettlementBuild foundSettlementAction = buildController.getFoundNewSettlementBuild();
        ExpandSettlementBuild expandAction = buildController.getExpandSettlmentBuild();
        TotoroBuild totoroAction = buildController.getTotoroBuild();
        TigerBuild tigerAction = buildController.getTigerBuild();

        boolean seuCheck = false;
        boolean fnsbCheck = false;
        boolean esohaCheck = false;
        boolean totoroActionCheck = false;
        boolean tigerActionCheck = false;

        if (settlementExpansionUtility != null){
            seuCheck = true;
        }

        if (foundSettlementAction != null) {
            fnsbCheck = true;
        }

        if (expandAction != null) {
            esohaCheck = true;
        }

        if (totoroAction != null) {
            totoroActionCheck = true;
        }

        if (tigerAction != null) {
            tigerActionCheck = true;
        }

        Assert.assertTrue(seuCheck && fnsbCheck && esohaCheck && totoroActionCheck && tigerActionCheck);
    }

    @Test
    public void checkThatScoreresWereAllInitialized(){
        ScoreVillagersOnHex villageScorer = buildController.getVillageScorer();
        ScoreTotoroOnHex totoroScorer = buildController.getTotoroScorer();
        ScoreTigerOnHex tigerScorer = buildController.getTigerScorer();

        boolean villageCheck = false;
        boolean totoroCheck = false;
        boolean tigerCheck = false;

        if (villageScorer != null) {
            villageCheck = true;
        }

        if (totoroScorer != null) {
            totoroCheck = true;
        }

        if (tigerScorer != null) {
            tigerCheck = true;
        }

        Assert.assertTrue(villageCheck && totoroCheck && tigerCheck);

    }

    @Test
    public void ableToFoundSettlementOnTopLeftStartingTileHex() {
        Location validTopLeft = new Location(0, 1, -1);

        currentPlayer = new Player();

        BuildActionData badTL = new BuildActionData.Builder()
                .withHexLocation(validTopLeft)
                .withPlayer(currentPlayer)
                .build();

        boolean resultTL = buildController.foundSettlement(badTL);
        Assert.assertTrue(resultTL);
    }

    @Test
    public void ableToFoundSettlementOnAllValidStartingTileHexes(){
        Location validTopLeft = new Location(0, 1, -1 );
        Location validTopRight = new Location(1, 0, -1);
        Location validBottomLeft = new Location(-1, 0 , 1);
        Location validBottomRight = new Location(0, -1, 1);

        currentPlayer = new Player();

        BuildActionData badTL = new BuildActionData.Builder()
                .withHexLocation(validTopLeft)
                .withPlayer(currentPlayer)
                .build();

        BuildActionData badTR = new BuildActionData.Builder()
                .withHexLocation(validTopRight)
                .withPlayer(currentPlayer)
                .build();

        BuildActionData badBL = new BuildActionData.Builder()
                .withHexLocation(validBottomLeft)
                .withPlayer(currentPlayer)
                .build();

        BuildActionData badBR = new BuildActionData.Builder()
                .withHexLocation(validBottomRight)
                .withPlayer(currentPlayer)
                .build();

        boolean resultBL = buildController.foundSettlement(badBL);
        boolean resultBR = buildController.foundSettlement(badBR);
        boolean resultTL = buildController.foundSettlement(badTL);
        boolean resultTR = buildController.foundSettlement(badTR);

        Assert.assertTrue(resultBL && resultBR && resultTL && resultTR);

    }

    @Test
    public void unableToFoundNewSettlementWhereTileDoesntExist() {
        Location l = new Location(1, 0, -1);
        currentPlayer = new Player();
        BuildActionData bad = new BuildActionData.Builder()
                .withHexLocation(l)
                .withPlayer(currentPlayer)
                .build();

        boolean result = buildController.foundSettlement(bad);
        Assert.assertFalse(result);
    }

    @Test
    public void ableToFoundNewSettlementOnStartingTile() {
        Location topRightStartingHex = new Location(1, 0, -1);
        currentPlayer = new Player();
        BuildActionData bad = new BuildActionData.Builder()
                .withHexLocation(topRightStartingHex)
                .withPlayer(currentPlayer)
                .build();

        boolean result = buildController.foundSettlement(bad);
        Assert.assertTrue(result);
    }

    @Test
    public void ableToFoundNewSettlementOnOpenLevel1Hex() {
        //only criteria is that it is by a level 3 settlement
        HashMap<Location, Hex> hexBoard = gameManager.getHexBoard().getBoard();
        PieceBoard pieces = gameManager.getPieceBoard();
        Player currentPlayer = new Player();
        PlayerID pID = currentPlayer.getId();

        Location openHex = new Location(-1, 3, -2);
        Hex hexToBuildOn = new Hex();

        hexBoard.put(openHex, hexToBuildOn);

        gameManager.setHexBoard(hexBoard);

        currentPlayer = new Player();
        BuildActionData bad = new BuildActionData.Builder()
                .withHexLocation(openHex)
                .withPlayer(currentPlayer)
                .build();

        boolean result = buildController.foundSettlement(bad);
        Assert.assertTrue(result);
    }

    @Test
    public void cannotBuildOnVolcano(){
        Location l = new Location(0, 0, 0);
        currentPlayer = new Player();
        BuildActionData bad = new BuildActionData.Builder()
                .withHexLocation(l)
                .withPlayer(currentPlayer)
                .build();

        boolean result = buildController.foundSettlement(bad);
        Assert.assertFalse(result);
    }

    @Test
    public void unbleToFoundNewSettlementInLeftConcaveBecauseTileDoesntExist() {
        Location l = new Location(-1, 1, 0);
        currentPlayer = new Player();
        BuildActionData bad = new BuildActionData.Builder()
                .withHexLocation(l)
                .withPlayer(currentPlayer)
                .build();

        boolean result = buildController.foundSettlement(bad);
        Assert.assertFalse(result);
    }

    @Test
    public void wasntAbleToExpandSettlement(){
        Location l = new Location(1, 0, -1);
        BuildActionData buildAction = new BuildActionData.Builder()
                .withSettlementLocation(l)
                .withTerrain(Rocky.getInstance())
                .withPlayer(currentPlayer)
                .build();

        boolean result = buildController.expandSettlement(buildAction);
        Assert.assertFalse(result);
    }

    @Test
    public void wasntAbleToBuildTigerBecauseHexDoesntExist(){
        Location l = new Location(1, 0, -1);
        BuildActionData buildAction = new BuildActionData.Builder()
                .withHexLocation(l)
                .withPlayer(currentPlayer)
                .build();

        boolean result = buildController.buildTiger(buildAction);
        Assert.assertFalse(result);
    }

    @Test
    public void canBuildTiger(){
        //only criteria is that it is by a level 3 settlement
        HashMap<Location, Hex> hexBoard = gameManager.getHexBoard().getBoard();
        PieceBoard pieces = gameManager.getPieceBoard();
        Player currentPlayer = new Player();
        PlayerID pID = currentPlayer.getId();

        //building the hexes in order to expand for a tiger
        //have confirmed multipole times the listed locations are all
        //touchign hexes
        Location level3Location = new Location(1, 1, -2);
        Location level3Location2 = new Location(2, 0 , -2);
        Location level3LocationToPlaceTiger = new Location(2, 1, -3);

        Piece villager = new Villager();
        Piece tiger = new Tiger();

        Hex hexToPlaceTiger = new Hex();
        Hex hexLevel3ToPlace = new Hex();
        Hex hexLevel3ToPlace2 = new Hex();
        hexLevel3ToPlace.setLevel(3);
        hexLevel3ToPlace2.setLevel(3);
        hexToPlaceTiger.setLevel(3);

        hexBoard.put(level3Location, hexLevel3ToPlace);
        hexBoard.put(level3Location2, hexLevel3ToPlace2);
        hexBoard.put(level3LocationToPlaceTiger, hexToPlaceTiger);

        gameManager.setHexBoard(hexBoard);

        // so now there are 3 hexes of lvl 3, 2 of which have villagers
        pieces.addPiece(villager, level3Location, pID);
        pieces.addPiece(villager, level3Location2, pID);

        gameManager.setPieceBoard(pieces);

        // actually placing a tiger on the avaiable hex at level 3
        BuildActionData buildAction = new BuildActionData.Builder()
                .withHexLocation(level3LocationToPlaceTiger)
                .withPlayer(currentPlayer)
                .build();

        boolean result = buildController.buildTotoro(buildAction);
        Assert.assertTrue(result);
    }

    @Test
    public void cantBuildTigerBecauseOneExists(){
        //only criteria is that it is by a level 3 settlement
        HashMap<Location, Hex> hexBoard = gameManager.getHexBoard().getBoard();
        PieceBoard pieces = gameManager.getPieceBoard();
        Player currentPlayer = new Player();
        PlayerID pID = currentPlayer.getId();

        //building the hexes in order to expand for a tiger
        //have confirmed multipole times the listed locations are all
        //touchign hexes
        Location level3LocationThatHasTiger = new Location(1, 1, -2);
        Location level3Location2 = new Location(2, 0 , -2);
        Location level3LocationToPlaceTiger = new Location(2, 1, -3);

        Piece villager = new Villager();
        Piece tiger = new Tiger();

        Hex hexToPlaceTiger = new Hex();
        Hex hexLevel3ToPlace = new Hex();
        Hex hexLevel3ToPlace2 = new Hex();
        hexLevel3ToPlace.setLevel(3);
        hexLevel3ToPlace2.setLevel(3);
        hexToPlaceTiger.setLevel(3);

        hexBoard.put(level3LocationThatHasTiger, hexLevel3ToPlace);
        hexBoard.put(level3Location2, hexLevel3ToPlace2);
        hexBoard.put(level3LocationToPlaceTiger, hexToPlaceTiger);

        gameManager.setHexBoard(hexBoard);

        // so now there are 3 hexes of lvl 3, 2 of which have villagers
        pieces.addPiece(tiger, level3LocationThatHasTiger, pID);
        pieces.addPiece(villager, level3Location2, pID);

        gameManager.setPieceBoard(pieces);

        // actually placing a tiger on the avaiable hex at level 3
        BuildActionData buildAction = new BuildActionData.Builder()
                .withHexLocation(level3LocationToPlaceTiger)
                .withPlayer(currentPlayer)
                .build();

        boolean result = buildController.buildTotoro(buildAction);
        Assert.assertFalse(result);
    }

    @Test
    public void wasntAbleToBuildTotoroBecauseHexDoesntExist(){
        Location l = new Location(1, 0, -1);
        BuildActionData buildAction = new BuildActionData.Builder()
                .withHexLocation(l)
                .withPlayer(currentPlayer)
                .build();

        boolean result = buildController.buildTotoro(buildAction);
        Assert.assertFalse(result);
    }

    @Test
    public void wasntAbleToBuildTotoroBecauseSettlementIsSizeOne(){
        HashMap<Location, Hex> hexBoard = gameManager.getHexBoard().getBoard();
        PieceBoard pieces = gameManager.getPieceBoard();
        Player currentPlayer = new Player();
        PlayerID pID = currentPlayer.getId();

        Location locationToAddTotoro = new Location(1, 0, -1);
        Location topRightStartingHex = new Location(0, 1, -1);
        Hex hexToPlace = new Hex();

        Piece villager = new Villager();
        Piece totoro = new Totoro();

        hexBoard.put(locationToAddTotoro, hexToPlace);
        gameManager.setHexBoard(hexBoard);

        // so now there is a settlment of size 1 all belonging to one player
        pieces.addPiece(villager, topRightStartingHex, pID);

        gameManager.setPieceBoard(pieces);

        BuildActionData buildAction = new BuildActionData.Builder()
                .withHexLocation(locationToAddTotoro)
                .withPlayer(currentPlayer)
                .build();

        boolean result = buildController.buildTotoro(buildAction);
        Assert.assertFalse(result);
    }

    @Test
    public void cantBuildTotoroBecauseOneAlreadyExists(){
        HashMap<Location, Hex> hexBoard = gameManager.getHexBoard().getBoard();
        PieceBoard pieces = gameManager.getPieceBoard();
        Player currentPlayer = new Player();
        PlayerID pID = currentPlayer.getId();

        //building the hexes in order to expand for a totoro
        // add three more to have settlment of size 5
        // and one more to actually place the totoro
        Location topRightStartingHex = new Location(0, 1, -1);
        Location bottomRightStartingHex = new Location(1, -1 , 0);
        Location l1 = new Location(1, 0, -1);
        Location l2 = new Location(2, 0, -2);
        Location l3 = new Location(1,1, -2);
        Location locationForNewTotoro = new Location(0, 2, -2);
        Location locationForExistingTotoro = new Location(-1, 2, -1);

        Piece villager = new Villager();
        Piece totoro = new Totoro();

        Hex hexToPlace = new Hex();

        hexBoard.put(l1, hexToPlace);
        hexBoard.put(l2, hexToPlace);
        hexBoard.put(l3, hexToPlace);
        hexBoard.put(locationForNewTotoro, hexToPlace);
        hexBoard.put(locationForExistingTotoro, hexToPlace);

        gameManager.setHexBoard(hexBoard);

        // so now there is a settlment of size 5 all belonging to one player
        pieces.addPiece(villager, topRightStartingHex, pID);
        pieces.addPiece(villager, bottomRightStartingHex, pID);
        pieces.addPiece(villager, l1, pID);
        pieces.addPiece(villager, l2, pID);
        pieces.addPiece(villager, l3, pID);
        pieces.addPiece(totoro, locationForExistingTotoro, pID);

        gameManager.setPieceBoard(pieces);

        BuildActionData buildAction = new BuildActionData.Builder()
                .withHexLocation(locationForNewTotoro)
                .withPlayer(currentPlayer)
                .build();

        boolean result = buildController.buildTotoro(buildAction);
        Assert.assertFalse(result);
    }

    @Test
    public void canBuildTotoro(){
        HashMap<Location, Hex> hexBoard = gameManager.getHexBoard().getBoard();
        PieceBoard pieces = gameManager.getPieceBoard();
        Player currentPlayer = new Player();
        PlayerID pID = currentPlayer.getId();

        //building the hexes in order to expand for a totoro
        // add three more to have settlment of size 5
        // and one more to actually place the totoro
        Location topRightStartingHex = new Location(0, 1, -1);
        Location bottomRightStartingHex = new Location(1, -1 , 0);
        Location l1 = new Location(1, 0, -1);
        Location l2 = new Location(2, 0, -2);
        Location l3 = new Location(1,1, -2);
        Location locationForTotoro = new Location(0, 2, -2);

        Piece villager = new Villager();
        Piece totoro = new Totoro();

        Hex hexToPlace = new Hex();

        hexBoard.put(l1, hexToPlace);
        hexBoard.put(l2, hexToPlace);
        hexBoard.put(l3, hexToPlace);
        hexBoard.put(locationForTotoro, hexToPlace);

        gameManager.setHexBoard(hexBoard);

        // so now there is a settlment of size 5 all belonging to one player
        pieces.addPiece(villager, topRightStartingHex, pID);
        pieces.addPiece(villager, bottomRightStartingHex, pID);
        pieces.addPiece(villager, l1, pID);
        pieces.addPiece(villager, l2, pID);
        pieces.addPiece(villager, l3, pID);

        gameManager.setPieceBoard(pieces);

        BuildActionData buildAction = new BuildActionData.Builder()
                .withHexLocation(locationForTotoro)
                .withPlayer(currentPlayer)
                .build();

        boolean result = buildController.buildTotoro(buildAction);
        Assert.assertTrue(result);
    }

}
