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
import tigerisland.piece.Piece;
import tigerisland.piece.PieceBoard;
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
import tigerisland.piece.PieceBoardImpl;
import tigerisland.score.ScoreManager;
import tigerisland.terrains.Rocky;

import java.util.List;

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
    public void checkThatBuildersExist(){
        SettlementExpansionUtility settlementExpansionUtility = buildController.getSettlementExpansionUtility();
        FoundNewSettlementBuild foundSettlementAction = buildController.getFoundNewSettlementBuild();
        ExpandSettlementOnHexAction expandAction = buildController.getExpandSettlmentAction();
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
    public void wasAbleToFoundNewSettlementInRightConcave() {
        Location l = new Location(1, 0, -1);
        currentPlayer = new Player();
        BuildActionData bad = new BuildActionData.Builder()
                .withHexLocation(l)
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
    public void wasAbleToFoundNewSettlementInLeftConcave() {
        Location l = new Location(-1, 0, 1);
        currentPlayer = new Player();
        BuildActionData bad = new BuildActionData.Builder()
                .withHexLocation(l)
                .withPlayer(currentPlayer)
                .build();

        boolean result = buildController.foundSettlement(bad);
        Assert.assertTrue(result);
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
    public void wasntAbleToBuildTotoro(){
        Location l = new Location(1, 0, -1);
        BuildActionData buildAction = new BuildActionData.Builder()
                .withHexLocation(l)
                .withPlayer(currentPlayer)
                .build();

        boolean result = buildController.buildTotoro(buildAction);
        Assert.assertFalse(result);
    }

    @Test
    public void wasntAbleToBuildTiger(){
        Location l = new Location(1, 0, -1);
        BuildActionData buildAction = new BuildActionData.Builder()
                .withHexLocation(l)
                .withPlayer(currentPlayer)
                .build();

        boolean result = buildController.buildTiger(buildAction);
        Assert.assertFalse(result);
    }

}
