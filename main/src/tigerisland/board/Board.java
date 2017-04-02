package tigerisland.board;

import tigerisland.hex.Hex;

import java.util.List;

public interface Board {

    List<Location> getUsedBoardLocations();

    void placeHex(Location location, Hex hex);

    Hex getHex(Location location);

    int getSize();

    boolean isLocationUsed(Location location);
}

