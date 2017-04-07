package tigerisland.game;

import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.piece.PieceBoard;
import tigerisland.settlement.SettlementBoard;
import tigerisland.tile.Tile;
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
        NukeTilePlacer nukePlacer = new NukeTilePlacer(gameBoard, new NukeCoverHexesLevelRule(gameBoard),
                new NukeHexesOfDifferentTilesRule(gameBoard), new NukeNonNukeablePieceRule(pieces),
                new NukeSettlementEradicationRule(settlements), new NukeVolcanoHexRule(gameBoard),
                new NukeVolcanoHexRule(gameBoard), new NukeVolcanoOnVolcanoRule(gameBoard));

        return nukePlacer;
    }

    public boolean placeTile(Tile tile, Location boardLocation){
        boolean validPlacement = true;
        try {
            tilePlacerChain.placeTile(tile, boardLocation);
        } catch (Exception e) {
            System.out.println("Error placing tile");
            e.printStackTrace();
            validPlacement = false;
        }
        // TODO validate tile placement-- DId this JOsh B- should be enough to be valid
        finally {
            return validPlacement;
        }
    }
}
