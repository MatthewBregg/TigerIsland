package tigerisland;

import org.junit.Before;
import org.junit.Test;
import tigerisland.Location;
import tigerisland.Orientation;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class LocationTest
{
    private Location loc;

    @Before
    public void TestSetup()
    {
        loc = new Location(5, 5, -10);
    }

    @Test(expected = InvalidParameterException.class)
    public void Constructor_InvalidLocation_ThrowsException()
    {
        Location loc = new Location(6, 4, -1);
    }

    @Test
    public void Constructor_DifferentConstructors_SameResult()
    {
        Location loc = new Location(5, 5);
        assertTrue(this.loc.equals(loc));
    }

    @Test
    public void getAdjacent_East_ValidEast()
    {
        Orientation orient = Orientation.getEast();
        Location newLoc = loc.getAdjacent(orient);

        assertEquals(new Location(6, 4, -10), newLoc);
    }

    @Test
    public void getAdjacent_NorthEast_ValidNorthEast()
    {
        Orientation orient = Orientation.getNorthEast();
        Location newLoc = loc.getAdjacent(orient);

        assertEquals(new Location(6, 5, -11), newLoc);
    }

    @Test
    public void getAdjacent_NorthWest_ValidNorthWest()
    {
        Orientation orient = Orientation.getNorthWest();
        Location newLoc = loc.getAdjacent(orient);

        assertEquals(new Location(5, 6, -11), newLoc);
    }

    @Test
    public void getAdjacent_West_ValidWest()
    {
        Orientation orient = Orientation.getWest();
        Location newLoc = loc.getAdjacent(orient);

        assertEquals(new Location(4, 6, -10), newLoc);
    }

    @Test
    public void getAdjacent_SouthEast_ValidSouthEast()
    {
        Orientation orient = Orientation.getSouthEast();
        Location newLoc = loc.getAdjacent(orient);

        assertEquals(new Location(5, 4, -9), newLoc);
    }

    @Test
    public void getAdjacent_SouthWest_ValidSouthWest()
    {
        Orientation orient = Orientation.getSouthWest();
        Location newLoc = loc.getAdjacent(orient);

        assertEquals(new Location(4, 5, -9), newLoc);
    }

    @Test
    public void equals_Object_False()
    {
        assertFalse(loc.equals(new Object()));
    }

    @Test
    public void equals_DifferentLocation_False()
    {
        assertFalse(loc.equals(new Location(7, 6, -13)));
    }

    @Test
    public void equals_DifferentXAndZ_False()
    {
        assertFalse(loc.equals(new Location(6, 5, -11)));
    }

    @Test
    public void equals_DifferentXAndY_False()
    {
        assertFalse(loc.equals(new Location(6, 4, -10)));
    }

    @Test
    public void equals_DifferentYAndZ_False()
    {
        assertFalse(loc.equals(new Location(5, 6, -11)));
    }

    @Test
    public void equals_SameInstance_True()
    {
        assertTrue(loc.equals(loc));
    }

    @Test
    public void equals_SameLocation_True()
    {
        assertTrue(loc.equals(new Location(5, 5)));
    }

    @Test
    public void getSurroundingLocations_Called_CorrectLocations()
    {
        ArrayList<Location> actual = loc.getSurroundingLocations();
        ArrayList<Location> expected = new ArrayList<Location>();
        expected.add(new Location(6, 4, -10));
        expected.add(new Location(6, 5, -11));
        expected.add(new Location(5, 6, -11));
        expected.add(new Location(4, 6, -10));
        expected.add(new Location(4, 5, -9));
        expected.add(new Location(5, 4, -9));

        assertEquals(expected, actual);
    }

    @Test
    public void getX_Called_ReturnsX()
    {
        assertEquals(5, loc.getX());
    }

    @Test
    public void getY_Called_ReturnsY()
    {
        assertEquals(5, loc.getY());
    }

    @Test
    public void getZ_Called_ReturnsZ()
    {
        assertEquals(-10, loc.getZ());
    }

    @Test
    public void hashCode_SameLocation_SameHashCode()
    {
        Location loc = new Location(5, 5, -10);
        assertEquals(loc.hashCode(), loc.hashCode());
    }

    @Test
    public void hashCode_DifferentLocation_DifferentHashCode()
    {
        Location loc = new Location(3, 4, -7);
        assertEquals(loc.hashCode(), loc.hashCode());
    }

    @Test
    public void toString_Called_ReturnExpectedString()
    {
        assertEquals("(5, 5, -10)", loc.toString());
    }
}
