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
    private NukeTilePlacer nukePlacer;

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

        return adjacentPlacer;
    }

    private NukeTilePlacer initializeNukePlacer(){
        nukePlacer = new NukeTilePlacer(gameBoard,
                pieces,
                new NukeCoverHexesLevelRule(gameBoard),
                new NukeHexesOfDifferentTilesRule(gameBoard),
                new NukeNonNukeablePieceRule(pieces),
                new NukeSettlementEradicationRule(settlements),
                new NukeVolcanoOnVolcanoRule(gameBoard));
        nukePlacer.setSettlementBoard(settlements);
        return nukePlacer;
    }

    public boolean placeTile(Tile tile, Location boardLocation){
        boolean validPlacement = true;
        try {
            tilePlacerChain.placeTile(tile, boardLocation);
        } catch (TilePlacementException e) {
            validPlacement = false;
        }
        return validPlacement;
    }

}
