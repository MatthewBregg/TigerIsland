package tigerisland.game;

import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.piece.PieceBoard;
import tigerisland.settlement.SettlementBoard;
import tigerisland.tile.Tile;
import tigerisland.tile_placement.exceptions.TilePlacementException;
import tigerisland.tile_placement.placers.*;
import tigerisland.tile_placement.rules.*;

public class TilePlacementController {
    private HexBoard gameBoard;
    private PieceBoard pieces;
    private SettlementBoard settlements;
    private TilePlacement tilePlacerChain;

    public TilePlacementController(HexBoard hexBoard, SettlementBoard settlementBoard, PieceBoard pieceBoard){
        gameBoard = hexBoard;
        settlements = settlementBoard;
        pieces = pieceBoard;

        tilePlacerChain = initializePlacementChain();
    }

    private TilePlacement initializePlacementChain() {
        InvalidTilePlacer invalidPlacer = new InvalidTilePlacer();

        NukeTilePlacer nukePlacer = initializeNukePlacer();
        nukePlacer.setNextTilePlacement(invalidPlacer);

        AdjacentToBoardTilePlacer adjacentPlacer = new AdjacentToBoardTilePlacer(gameBoard);
        adjacentPlacer.setNextTilePlacement(nukePlacer);

        FirstTilePlacer firstPlacer = new FirstTilePlacer(gameBoard);
        firstPlacer.setNextTilePlacement(adjacentPlacer);

        return firstPlacer;
    }

    private NukeTilePlacer initializeNukePlacer(){
        NukeTilePlacer nukePlacer = new NukeTilePlacer(gameBoard, pieces, new NukeCoverHexesLevelRule(gameBoard),
                new NukeHexesOfDifferentTilesRule(gameBoard), new NukeNonNukeablePieceRule(pieces),
                new NukeSettlementEradicationRule(settlements), new NukeVolcanoHexRule(gameBoard),
                new NukeVolcanoHexRule(gameBoard), new NukeVolcanoOnVolcanoRule(gameBoard));

        return nukePlacer;
    }

    public boolean placeTile(Tile tile, Location boardLocation){
        boolean validPlacement = true;
        try {
            tilePlacerChain.placeTile(tile, boardLocation);
        } catch (TilePlacementException e) {
            System.out.println("Error placing tile " + e);
//            e.printStackTrace();
            validPlacement = false;
            //TODO Come back and decide what to do with stackTrace
        }
        // TODO validate tile placement-- DId this with the finally JOsh B- should be enough to be valid
        return validPlacement;
    }
}
