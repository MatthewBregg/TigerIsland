import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

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
    public void test_ShouldDemonstrateDifferentLocationsShouldHaveDifferentHexes() {
        // Arrange

        List<Location> locationList = new ArrayList<Location>();
        List<Hex> hexList = new ArrayList<Hex>();

        // Act
        for (int i = 0; i < 3; ++i)
            locationList.add(new Location(i, -i, 0));

        for (Location location : locationList) {
            Hex newHex = new Hex();
            hexBoard.placeHex(location, newHex);
            hexList.add(newHex);
        }

        // Assert

        List<Hex> returnedHexes = new ArrayList<Hex>();
        Hex returnedHex;
        for (Location location : locationList) {
            returnedHex = hexBoard.getHex(location);
            Assert.assertFalse(returnedHexes.contains(returnedHex));
            returnedHexes.add(returnedHex);
        }
    }
    @Test
    public void test_ShouldReturnUsedBoardLocations(){


        List<Location> locationList = new ArrayList<Location>();
        List<Hex> hexList = new ArrayList<Hex>();

        // Act
        for (int i = 0; i < 3; ++i)
            locationList.add(new Location(i, -i, 0));


        for (Location location : locationList) {
            Hex newHex = new Hex();
            hexBoard.placeHex(location, newHex);
            hexList.add(newHex);
        }

        List<Location> boardLocations = hexBoard.getUsedBoardLocations();

        // Assert

        //HashMap returns the correct coordinates but in a different order therefore:

        //check that all coordinates are being returned
        Assert.assertTrue(boardLocations.containsAll(locationList));

        //check that no extra coordinates are being returned
        Assert.assertTrue(boardLocations.size()==locationList.size());
    }

}