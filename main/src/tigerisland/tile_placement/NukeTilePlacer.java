package tigerisland.tile_placement;

import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class NukeTilePlacer  implements  TilePlacement, TilePlacementChain{

    TilePlacement nextTilePlacement;
    List<TilePlacement> nukeTilePlacementRules;
    Board board;

    public NukeTilePlacer(Board board, TilePlacement... rules) {

        this.board = board;
        nukeTilePlacementRules = new ArrayList<>();
        for(TilePlacement rule : rules) {
            nukeTilePlacementRules.add(rule);
        }
    }
    
    @Override
    public void placeTile(Tile tile, Location location) throws Throwable {

        // TODO: when all hexes from tile cover existing hexes on the board then call all the rules
        // TODO: else call nextTilePlacement
        for(TilePlacement rule : nukeTilePlacementRules) {
           rule.placeTile(tile, location);
        }
    }

    @Override
    public void setNextTilePlacement(TilePlacement tilePlacement) {
        this.nextTilePlacement = tilePlacement;
    }

    @Override
    public void nextTilePlacement(Tile tile, Location location) throws Throwable {
        this.nextTilePlacement.placeTile(tile, location);
    }
}
