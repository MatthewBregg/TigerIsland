package tigerisland.hex;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.hex.Hex;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.hex.NullHex;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * Created by josh on 3/15/17.
 */
public class HexBoardTest {

    private HexBoard hexBoard;

    @Before
    public void setUp() {
        hexBoard = new HexBoard();
    }

    @Test
    public void test_ShouldAddHexAtALocation() {

        // Arrange
        Location location = new Location(0, 0, 0);
        Hex hex = new Hex();

        // Act
        hexBoard.placeHex(location, hex);

        // Assert
        Assert.assertEquals(hexBoard.getHex(location), hex);
    }

    @Test
    public void test_ShouldReturnNullHexWhenLocationIsNotUsed() {

        // Arrange
        Location location = new Location(0, 0, 0);

        Location unPlacedLocation = new Location(1, -1, 0);

        Hex hex = new Hex();

        // Act
        hexBoard.placeHex(location, hex);

        // Assert
        Assert.assertThat(hexBoard.getHex(unPlacedLocation), instanceOf(NullHex.class));

    }

    @Test
    public void test_ShouldDemonstrateDifferentLocationsShouldHaveDifferentHexes(){

        //Arrange
        Location location = new Location(0,1,-1);
        Location location1 = new Location(0, 2, -2);

        Hex hex = new Hex();
        Hex hex1 = new Hex();

        //Act
        hexBoard.placeHex(location, hex);
        hexBoard.placeHex(location1, hex1);

        //Assert
        Assert.assertNotEquals(hexBoard.getHex(location), hexBoard.getHex(location1));
    }

    @Test
    public void test_ShouldReturnUsedBoardLocations(){

        //Arrange
        List<Location> locationList = new ArrayList<Location>();
        List<Hex> hexList = new ArrayList<Hex>();

        // Act
        for (int i = 0; i < 3; ++i) {
            locationList.add(new Location(i, -i, 0));
        }

        for (Location location : locationList) {
            Hex newHex = new Hex();
            hexBoard.placeHex(location, newHex);
            hexList.add(newHex);
        }

        List<Location> boardLocations = hexBoard.getUsedBoardLocations();

        // Assert
        Assert.assertTrue(boardLocations.containsAll(locationList));
        Assert.assertEquals(boardLocations.size(), hexBoard.getSize());
    }

}