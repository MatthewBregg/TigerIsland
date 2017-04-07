package tigerisland.tile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.hex.Hex;

import java.util.HashMap;
import java.util.Map;

public class TileUnpackerTest {

    private Map<Location, Hex> hexes;
    private Location volcanoHexLocation;

    Location leftHexShouldBe;
    Location rightHexShouldBe;

    Orientation tileOrientation;

    @Before
    public void setUp() {
        hexes = new HashMap<>();
        volcanoHexLocation = new Location(0,0,0);
    }

    private Tile createTile(Orientation orientation) {
        Tile tile = new Tile(0);
        tile.setOrientation(orientation);
        return tile;
    }

    @Test
    public void test_ShouldReturnSpecificCoordinatesGivenTileWithEastOrientation() {

        // Arrange
        tileOrientation = Orientation.getEast();
        Tile tile = createTile(tileOrientation);

        // Act
        hexes = TileUnpacker.getTileHexes(tile, volcanoHexLocation);
        leftHexShouldBe = new Location(-1,0,1);
        rightHexShouldBe = new Location (0,-1,1);

        // Assert
        Assert.assertNotNull(hexes.get(leftHexShouldBe));
        Assert.assertNotNull(hexes.get(rightHexShouldBe));
    }

    @Test
    public void test_ShouldReturnSpecificCoordinatesGivenTileWithNorthEastOrientation() {

        // Arrange
        tileOrientation= Orientation.getNorthEast();
        Tile tile = createTile(tileOrientation);

        // Act
        hexes = TileUnpacker.getTileHexes(tile, volcanoHexLocation);
        leftHexShouldBe = new Location(0,-1,1);
        rightHexShouldBe = new Location (1,-1,0);

        // Assert
        Assert.assertNotNull(hexes.get(leftHexShouldBe));
        Assert.assertNotNull(hexes.get(rightHexShouldBe));

    }

    @Test
    public void test_ShouldReturnSpecificCoordinatesGivenTileWithNorthWestOrientation() {

        // Arrange
        tileOrientation= Orientation.getNorthWest();
        Tile tile = createTile(tileOrientation);

        // Act
        hexes = TileUnpacker.getTileHexes(tile, volcanoHexLocation);
        leftHexShouldBe = new Location(1,-1,0);
        rightHexShouldBe = new Location (1,0,-1);

        // Assert
        Assert.assertNotNull(hexes.get(leftHexShouldBe));
        Assert.assertNotNull(hexes.get(rightHexShouldBe));
    }

    @Test
    public void test_ShouldReturnSpecificCoordinatesGivenTileWithWestOrientation() {

        // Arrange
        tileOrientation= Orientation.getWest();
        Tile tile = createTile(tileOrientation);

        // Act
        hexes = TileUnpacker.getTileHexes(tile, volcanoHexLocation);
        leftHexShouldBe = new Location(1,0,-1);
        rightHexShouldBe = new Location (0,1,-1);

        // Assert
        Assert.assertNotNull(hexes.get(leftHexShouldBe));
        Assert.assertNotNull(hexes.get(rightHexShouldBe));
    }

    @Test
    public void test_ShouldReturnSpecificCoordinatesGivenTileWithSouthWestOrientation() {

        // Arrange
        tileOrientation= Orientation.getSouthWest();
        Tile tile = createTile(tileOrientation);

        // Act
        hexes = TileUnpacker.getTileHexes(tile, volcanoHexLocation);
        leftHexShouldBe= new Location(0,1,-1);
        rightHexShouldBe = new Location(-1,1,0);

        // Assert
        Assert.assertNotNull(hexes.get(leftHexShouldBe));
        Assert.assertNotNull(hexes.get(rightHexShouldBe));
    }

    @Test
    public void test_ShouldReturnSpecificCoordinatesGivenTileSouthEastOrientation() {

        // Arrange
        tileOrientation= Orientation.getSouthEast();
        Tile tile = createTile(tileOrientation);

        // Act
        hexes = TileUnpacker.getTileHexes(tile, volcanoHexLocation);
        leftHexShouldBe= new Location(-1,1,0);
        rightHexShouldBe = new Location (-1,0,1);

        // Assert
        Assert.assertNotNull(hexes.get(leftHexShouldBe));
        Assert.assertNotNull(hexes.get(rightHexShouldBe));
    }
}
