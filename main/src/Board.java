import java.util.List;
public interface Board {

    public List<Location> getUsedBoardLocations();

    public void placeHex(Location location, Hex hex);

    public Hex getHex(Location location);



}

