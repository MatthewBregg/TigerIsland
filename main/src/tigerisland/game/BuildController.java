package tigerisland.game;

import tigerisland.board.HexBoard;
import tigerisland.build_moves.SettlementExpansionUtility;
import tigerisland.build_moves.actions.ScoreShamanOnHex;
import tigerisland.build_moves.actions.ScoreTigerOnHex;
import tigerisland.build_moves.actions.ScoreTotoroOnHex;
import tigerisland.build_moves.actions.ScoreVillagersOnHex;
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
    FoundNewSettlementBuild foundSettlementAction;
    ExpandSettlementBuild expandSettlementBuild;
    TotoroBuild totoroAction;
    TigerBuild tigerAction;
    ShamanBuild shamanAction;

    ScoreVillagersOnHex villageScorer;
    ScoreTotoroOnHex totoroScorer;
    ScoreTigerOnHex tigerScorer;
    ScoreShamanOnHex shamanScorer;

    boolean justNukedShaman;

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

    public ShamanBuild getShamanBuild(){
        return shamanAction;

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

    public ScoreShamanOnHex getShamanScorer(){
        return shamanScorer;
    }

    private void initializeBuilders(){

        foundSettlementAction = new FoundNewSettlementBuild(hexBoard, pieceBoard, scoreMgr);
        expansionUtility = new SettlementExpansionUtility(hexBoard, pieceBoard,settlementBoard);
        expandSettlementBuild = new ExpandSettlementBuild(expansionUtility,pieceBoard,scoreMgr);

        totoroAction = new TotoroBuild(hexBoard, pieceBoard, settlementBoard, scoreMgr);
        tigerAction = new TigerBuild(hexBoard, pieceBoard, settlementBoard, scoreMgr);
        shamanAction = new ShamanBuild(hexBoard, pieceBoard, settlementBoard, scoreMgr);
    }

    private void initializeScoring(){
        villageScorer = new ScoreVillagersOnHex(hexBoard, scoreMgr, justNukedShaman);
        totoroScorer = new ScoreTotoroOnHex(scoreMgr);
        tigerScorer = new ScoreTigerOnHex(scoreMgr);
        shamanScorer = new ScoreShamanOnHex(scoreMgr);
    }

    public BuildActionResult foundSettlement(BuildActionData buildActionData){
        BuildActionResult result = foundSettlementAction.build(buildActionData);
        return result;
    }

    public BuildActionResult expandSettlement(BuildActionData buildActionData){

        BuildActionResult result = expandSettlementBuild.build(buildActionData);
        return result;
    }

    public BuildActionResult buildTiger(BuildActionData buildActionData){
        BuildActionResult result = tigerAction.build(buildActionData);
        return result;
    }

    public BuildActionResult buildTotoro(BuildActionData buildActionData){
        BuildActionResult result = totoroAction.build(buildActionData);
        return result;
    }

    public BuildActionResult buildShaman(BuildActionData buildActionData){
        BuildActionResult result = shamanAction.build(buildActionData);
        return result;
    }
}
