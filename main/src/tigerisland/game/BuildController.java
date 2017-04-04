package tigerisland.game;

import tigerisland.board.HexBoard;
import tigerisland.build_moves.SettlementExpansionUtility;
import tigerisland.build_moves.actions.*;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.piece.PieceBoard;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.SettlementBoard;

public class BuildController {
    HexBoard hexBoard;
    PieceBoard pieceBoard;
    SettlementBoard settlementBoard;
    ScoreManager scoreMgr;

    SettlementExpansionUtility expansionUtility;
    PlaceVillagerOnHexAction foundSettlementAction;
    ExpandSettlementOnHexAction expandAction;
    PlaceTotoroOnHexAction totoroAction;
    PlaceTigerOnHexAction tigerAction;

    ScoreVillagersOnHex villageScorer;
    ScoreTotoroOnHex totoroScorer;
    ScoreTigerOnHex tigerScorer;

    public BuildController(HexBoard hexBoard, PieceBoard pieceBoard, SettlementBoard settlementBoard, ScoreManager scoreManager){
        this.hexBoard = hexBoard;
        this.pieceBoard = pieceBoard;
        this.settlementBoard = settlementBoard;
        scoreMgr = scoreManager;

        initializeBuilders();
        initializeScoring();
    }

    private void initializeBuilders(){
        expansionUtility = new SettlementExpansionUtility(hexBoard, pieceBoard, settlementBoard);
        foundSettlementAction = new PlaceVillagerOnHexAction(pieceBoard);
        expandAction = new ExpandSettlementOnHexAction(pieceBoard, expansionUtility);
        totoroAction = new PlaceTotoroOnHexAction(pieceBoard);
        tigerAction = new PlaceTigerOnHexAction(pieceBoard);
    }

    private void initializeScoring(){
        villageScorer = new ScoreVillagersOnHex(hexBoard, scoreMgr);
        totoroScorer = new ScoreTotoroOnHex(scoreMgr);
        tigerScorer = new ScoreTigerOnHex(scoreMgr);
    }

    public boolean foundSettlement(BuildActionData buildActionData){
        foundSettlementAction.applyAction(buildActionData);
        villageScorer.applyAction(buildActionData);
        return false;
    }
}
