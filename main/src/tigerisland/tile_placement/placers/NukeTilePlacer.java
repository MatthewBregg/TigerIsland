package tigerisland.tile_placement.placers;

import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.piece.PieceBoard;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;
import tigerisland.tile.Tile;
import tigerisland.tile.TileUnpacker;
import tigerisland.tile_placement.exceptions.TilePlacementException;
import tigerisland.tile_placement.rules.NukePlacementRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NukeTilePlacer  implements  TilePlacement, TilePlacementChain{

    TilePlacement nextTilePlacement;
    List<NukePlacementRule> nukeTilePlacementRules;
    Board board;
    PieceBoard pieceBoard;
    private SettlementBoard settlementBoard;
    private boolean nukedSHaman;

    public NukeTilePlacer(Board board, PieceBoard pieceBoard, NukePlacementRule... rules) {

        this.board = board;
        this.pieceBoard = pieceBoard;
        nukeTilePlacementRules = new ArrayList<>();
        for(NukePlacementRule rule : rules) {
            nukeTilePlacementRules.add(rule);
        }
    }

    public void setSettlementBoard(SettlementBoard sb){
        settlementBoard = sb;
    }
    
    @Override
    public void placeTile(Tile tile, Location location) throws TilePlacementException {

        Map<Location, Hex> hexes = TileUnpacker.getTileHexes(tile, location);

        if ( aHexIsUnUsed(hexes) ) {
            nextTilePlacement(tile, location);
        }
        else {

            applyNukeRules(hexes);

            nukedSHaman = checkSettlementHasTotoro(hexes);

            removePiecesFromBoard(hexes);

            placeHexesOnBoard(hexes);
        }
    }

    private boolean wasShamanNuked(){
        return nukedSHaman;
    }

    private void applyNukeRules(Map<Location, Hex> hexes) throws TilePlacementException {
        for(NukePlacementRule rule : nukeTilePlacementRules) {
                rule.applyRule(hexes);
        }
    }

    private boolean aHexIsUnUsed(Map<Location, Hex> hexes) {

        for(Location location : hexes.keySet()) {

            if (this.board.isLocationUsed(location) == false) {
                return true;
            }
        }
        return false;
    }


    private void placeHexesOnBoard(Map<Location, Hex> hexes) {
        hexes.forEach( (location, hex) -> {

           Hex mapHex = this.board.getHex(location);
           hex.setLevel(mapHex.getLevel()+1);
           this.board.placeHex(location, hex);

        });
    }

    private void removePiecesFromBoard(Map<Location, Hex> hexes) {
         hexes.forEach( (location, hex) -> {
             this.pieceBoard.removePiece(location);
        });
    }

    private boolean checkSettlementHasTotoro(Map<Location, Hex> hexes){
        Set<Location> locations = hexes.keySet();

        for (Location currentLocation : locations) {
            Settlement settlement = settlementBoard.getSettlement(currentLocation);
            //Null settlement
            if(settlement.hasShamanInSettlement()){
                return true;
            }
        }

        return false;
    }

    @Override
    public void setNextTilePlacement(TilePlacement tilePlacement) {
        this.nextTilePlacement = tilePlacement;
    }

    @Override
    public void nextTilePlacement(Tile tile, Location location) throws TilePlacementException {
        this.nextTilePlacement.placeTile(tile, location);
    }
}
