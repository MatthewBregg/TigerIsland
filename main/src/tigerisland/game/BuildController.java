package tigerisland.game;

import tigerisland.board.HexBoard;
import tigerisland.build_moves.SettlementExpansionUtility;
import tigerisland.build_moves.builds.*;
import tigerisland.piece.PieceBoard;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.SettlementBoard;

public class BuildController {
    HexBoard hexBoard;
    PieceBoard pieceBoard;
    SettlementBoard settlementBoard;
    ScoreManager scoreMgr;

    SettlementExpansionUtility expansionUtility;
    FoundNewSettlementBuild foundSettlementBuild;
    ExpandSettlementBuild expandSettlementBuild;
    TotoroBuild totoroBuild;
    TigerBuild tigerBuild;

    public BuildController(HexBoard hexBoard, PieceBoard pieceBoard, SettlementBoard settlementBoard, ScoreManager scoreManager){
        this.hexBoard = hexBoard;
        this.pieceBoard = pieceBoard;
        this.settlementBoard = settlementBoard;
        scoreMgr = scoreManager;

        initializeBuilders();
    }

    private void initializeBuilders(){

        expansionUtility = new SettlementExpansionUtility(hexBoard, pieceBoard,settlementBoard);

        foundSettlementBuild = new FoundNewSettlementBuild(hexBoard, pieceBoard, scoreMgr);
        expandSettlementBuild = new ExpandSettlementBuild(expansionUtility,pieceBoard,scoreMgr);
        totoroBuild = new TotoroBuild(hexBoard, pieceBoard, settlementBoard, scoreMgr);
        tigerBuild = new TigerBuild(hexBoard, pieceBoard, settlementBoard, scoreMgr);
    }

    public SettlementExpansionUtility getSettlementExpansionUtility(){
        return expansionUtility;
    }

    public FoundNewSettlementBuild getFoundNewSettlementBuild(){
        return foundSettlementBuild;
    }

    public ExpandSettlementBuild getExpandSettlmentBuild(){
        return expandSettlementBuild;
    }

    public TotoroBuild getTotoroBuild(){
        return totoroBuild;
    }

    public TigerBuild getTigerBuild(){
        return tigerBuild;
    }

    public BuildActionResult foundSettlement(BuildActionData buildActionData){
        BuildActionResult result = foundSettlementBuild.build(buildActionData);
        return result;
        //TODO change to FoundNewSettlement Build
    }

    public BuildActionResult expandSettlement(BuildActionData buildActionData){

        BuildActionResult result = expandSettlementBuild.build(buildActionData);
        //TODO check scoring for expansion vs founding
        return result;
    }

    public BuildActionResult buildTiger(BuildActionData buildActionData){
        BuildActionResult result = tigerBuild.build(buildActionData);
        return result;
    }

    public BuildActionResult buildTotoro(BuildActionData buildActionData){
        BuildActionResult result = totoroBuild.build(buildActionData);
        return result;
    }

}
