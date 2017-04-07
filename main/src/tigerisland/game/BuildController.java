package tigerisland.game;

import tigerisland.board.HexBoard;
import tigerisland.build_moves.SettlementExpansionUtility;
import tigerisland.build_moves.actions.ScoreTigerOnHex;
import tigerisland.build_moves.actions.ScoreTotoroOnHex;
import tigerisland.build_moves.actions.ScoreVillagersOnHex;
import tigerisland.build_moves.builds.*;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.Tiger;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.SettlementBoard;

public class BuildController {
    HexBoard hexBoard;
    PieceBoard pieceBoard;
    SettlementBoard settlementBoard;
    ScoreManager scoreMgr;

    SettlementExpansionUtility expansionUtility;
    FoundNewSettlementBuild foundSettlementAction;
    ExpandSettlementBuild expandSettlementBuild;
    TotoroBuild totoroAction;
    TigerBuild tigerAction;

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


    public SettlementExpansionUtility getSettlementExpansionUtility(){
        return expansionUtility;
    }

    public FoundNewSettlementBuild getFoundNewSettlementBuild(){
        return foundSettlementAction;
    }

    public ExpandSettlementBuild getExpandSettlmentBuild(){
        return expandSettlementBuild;
    }

    public TotoroBuild getTotoroBuild(){
        return totoroAction;
    }

    public TigerBuild getTigerBuild(){
        return tigerAction;
    }

    public ScoreVillagersOnHex getVillageScorer(){
        return villageScorer;
    }
    public ScoreTotoroOnHex getTotoroScorer(){
        return totoroScorer;
    }

    public ScoreTigerOnHex getTigerScorer(){
        return tigerScorer;
    }

    private void initializeBuilders(){

        foundSettlementAction = new FoundNewSettlementBuild(hexBoard, pieceBoard, scoreMgr);
        expansionUtility = new SettlementExpansionUtility(hexBoard, pieceBoard,settlementBoard);
        expandSettlementBuild = new ExpandSettlementBuild(expansionUtility,pieceBoard);

        totoroAction = new TotoroBuild(hexBoard, pieceBoard, settlementBoard, scoreMgr);
        tigerAction = new TigerBuild(hexBoard, pieceBoard, settlementBoard, scoreMgr);
    }

    private void initializeScoring(){
        villageScorer = new ScoreVillagersOnHex(hexBoard, scoreMgr);
        totoroScorer = new ScoreTotoroOnHex(scoreMgr);
        tigerScorer = new ScoreTigerOnHex(scoreMgr);
    }

    public boolean foundSettlement(BuildActionData buildActionData){
        BuildActionResult result = foundSettlementAction.build(buildActionData);
        return result.successful;
        //TODO change to FoundNewSettlement Build
    }

    public boolean expandSettlement(BuildActionData buildActionData){

        BuildActionResult result = expandSettlementBuild.build(buildActionData);
        //TODO check scoring for expansion vs founding
        return result.successful;
    }

    public boolean buildTiger(BuildActionData buildActionData){
        BuildActionResult result = tigerAction.build(buildActionData);
        return result.successful;
    }

    public boolean buildTotoro(BuildActionData buildActionData){
        BuildActionResult result = totoroAction.build(buildActionData);
        return result.successful;
    }




    //TODO
}
