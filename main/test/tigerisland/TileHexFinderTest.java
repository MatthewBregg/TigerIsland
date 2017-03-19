package tigerisland;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class TileHexFinderTest {

    private HashMap<NonVolcanoHex, Location> nonVolcanoHexes;
    private static TileHexFinder hexFind;
    private Location volcanoHexLocation = new Location(0,0,0);

    private NonVolcanoHex left = NonVolcanoHex.LEFT;
    private NonVolcanoHex right = NonVolcanoHex.RIGHT;

    Location leftHexShouldBe;
    Location rightHexShouldBe;



    Orientation tileOrientation;

    @Before
    public void setUp() {
        nonVolcanoHexes = new HashMap<>();

    }

    @BeforeClass
    public static void makeHexFinder(){
        hexFind = new TileHexFinder();
    }


    @Test
    public void test_ShouldReturnSpecificCoordinatesGivenInTestWithEastOrientation() {

        tileOrientation= Orientation.getEast();
        nonVolcanoHexes= hexFind.getNonVolcanoHexLocations(volcanoHexLocation, tileOrientation);
        leftHexShouldBe= new Location(-1,0,1);
        rightHexShouldBe = new Location (0,-1,1);

        Assert.assertEquals(nonVolcanoHexes.get(left), leftHexShouldBe);
        Assert.assertEquals(nonVolcanoHexes.get(right), rightHexShouldBe );

    }
    @Test
    public void test_ShouldReturnSpecificCoordinatesGivenInTestWithNorthEastOrientation() {

        tileOrientation= Orientation.getNorthEast();
        nonVolcanoHexes= hexFind.getNonVolcanoHexLocations(volcanoHexLocation, tileOrientation);
        leftHexShouldBe= new Location(-1,1,0);
        rightHexShouldBe = new Location (-1,0,1);

        Assert.assertEquals(nonVolcanoHexes.get(left), leftHexShouldBe);
        Assert.assertEquals(nonVolcanoHexes.get(right), rightHexShouldBe );

    }
    @Test
    public void test_ShouldReturnSpecificCoordinatesGivenInTestWithNorthWestOrientation() {

        tileOrientation= Orientation.getNorthWest();
        nonVolcanoHexes= hexFind.getNonVolcanoHexLocations(volcanoHexLocation, tileOrientation);
        leftHexShouldBe= new Location(0,1,-1);
        rightHexShouldBe = new Location (-1,1,0);

        Assert.assertEquals(nonVolcanoHexes.get(left), leftHexShouldBe);
        Assert.assertEquals(nonVolcanoHexes.get(right), rightHexShouldBe );

    }
    @Test
    public void test_ShouldReturnSpecificCoordinatesGivenInTestWithWestOrientation() {

        tileOrientation= Orientation.getWest();
        nonVolcanoHexes= hexFind.getNonVolcanoHexLocations(volcanoHexLocation, tileOrientation);
        leftHexShouldBe= new Location(1,0,-1);
        rightHexShouldBe = new Location (0,1,-1);

        Assert.assertEquals(nonVolcanoHexes.get(left), leftHexShouldBe);
        Assert.assertEquals(nonVolcanoHexes.get(right), rightHexShouldBe );

    }
    @Test
    public void test_ShouldReturnSpecificCoordinatesGivenInTestWithSouthWestOrientation() {

        tileOrientation= Orientation.getSouthWest();
        nonVolcanoHexes= hexFind.getNonVolcanoHexLocations(volcanoHexLocation, tileOrientation);
        leftHexShouldBe= new Location(1,-1,0);
        rightHexShouldBe = new Location (1,0,-1);

        Assert.assertEquals(nonVolcanoHexes.get(left), leftHexShouldBe);
        Assert.assertEquals(nonVolcanoHexes.get(right), rightHexShouldBe );

    }
    @Test
    public void test_ShouldReturnSpecificCoordinatesGivenInTestWithSouthEastOrientation() {

        tileOrientation= Orientation.getSouthEast();
        nonVolcanoHexes= hexFind.getNonVolcanoHexLocations(volcanoHexLocation, tileOrientation);
        leftHexShouldBe= new Location(0,-1,1);
        rightHexShouldBe = new Location (1,-1,0);

        Assert.assertEquals(nonVolcanoHexes.get(left), leftHexShouldBe);
        Assert.assertEquals(nonVolcanoHexes.get(right), rightHexShouldBe );

    }

}