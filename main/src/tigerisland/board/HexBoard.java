package tigerisland.board;

import tigerisland.hex.NullHex;
import tigerisland.hex.Hex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by josh and on 3/15/17.
 */

public class HexBoard implements Board{
    private HashMap<Location, Hex> board;

    public HexBoard() {
        board = new HashMap();
    }

    @Override
    public List<Location> getUsedBoardLocations() {
        return new ArrayList(board.keySet());
    }

    @Override
    public void placeHex(Location location, Hex hex) {
        board.put(location, hex);
    }

    @Override
    public Hex getHex(Location location){
        if (board.containsKey(location))
            return board.get(location);
        else
            return new NullHex();
    }

    @Override
    public int getSize() {
        return this.board.size();
    }

    @Override
    public boolean isLocationUsed(Location location) {
        return this.board.get(location) != null;
    }

    public void setBoard(HashMap<Location, Hex> newBoard){
        board = newBoard;
    }

    public HashMap<Location, Hex> getBoard(){
        return board;
    }
}
