package tigerisland;

import org.junit.BeforeClass;
import org.junit.Test;
import tigerisland.Orientation;

import java.security.InvalidParameterException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrientationTest
{
    private static Orientation o;

    @BeforeClass
    public static void setUp()
    {
        o=new Orientation(0);
    }

    @Test
    public void Constructor_TwoOffsets_CorrectFields()
    {
        Orientation o = new Orientation(1, 0);

        assertEquals(1, o.getDx());
        assertEquals(0, o.getDy());
        assertEquals(-1, o.getDz());
        assertEquals(60, o.getAngle());
    }

    @Test
    public void Constructor_ThreeOffsets_CorrectFields()
    {
        Orientation o = new Orientation(1, 0, -1);

        assertEquals(1, o.getDx());
        assertEquals(0, o.getDy());
        assertEquals(-1, o.getDz());
        assertEquals(60, o.getAngle());
    }

    @Test(expected = InvalidParameterException.class)
    public void Constructor_InvalidOffsets_CorrectFields()
    {
        Orientation o = new Orientation(1, 0, 1);
    }

    @Test
    public void Constructor_Angle_CorrectFields()
    {
        Orientation o = new Orientation(60);

        assertEquals(1, o.getDx());
        assertEquals(0, o.getDy());
        assertEquals(-1, o.getDz());
        assertEquals(60, o.getAngle());
    }

    @Test(expected = InvalidParameterException.class)
    public void Constructor_InvalidAngle_CorrectFields()
    {
        Orientation o = new Orientation(75);
    }

    @Test
    public void Constructor_East_CorrectFields()
    {
        Orientation o = new Orientation(0);

        assertEquals(1, o.getDx());
        assertEquals(-1, o.getDy());
        assertEquals(0, o.getDz());
        assertEquals(0, o.getAngle());
    }

    @Test
    public void Constructor_NorthEast_CorrectFields()
    {
        Orientation o = new Orientation(60);

        assertEquals(1, o.getDx());
        assertEquals(0, o.getDy());
        assertEquals(-1, o.getDz());
        assertEquals(60, o.getAngle());
    }

    @Test
    public void Constructor_NorthWest_CorrectFields()
    {
        Orientation o = new Orientation(120);

        assertEquals(0, o.getDx());
        assertEquals(1, o.getDy());
        assertEquals(-1, o.getDz());
        assertEquals(120, o.getAngle());
    }

    @Test
    public void Constructor_West_CorrectFields()
    {
        Orientation o = new Orientation(180);

        assertEquals(-1, o.getDx());
        assertEquals(1, o.getDy());
        assertEquals(0, o.getDz());
        assertEquals(180, o.getAngle());
    }

    @Test
    public void Constructor_SouthWest_CorrectFields()
    {
        Orientation o = new Orientation(240);

        assertEquals(-1, o.getDx());
        assertEquals(0, o.getDy());
        assertEquals(1, o.getDz());
        assertEquals(240, o.getAngle());
    }

    @Test
    public void Constructor_SouthEast_CorrectFields()
    {
        Orientation o = new Orientation(300);

        assertEquals(0, o.getDx());
        assertEquals(-1, o.getDy());
        assertEquals(1, o.getDz());
        assertEquals(300, o.getAngle());
    }

    @Test
    public void equals_Object_False()
    {
        assertFalse(o.equals(new Object()));
    }

    @Test
    public void equals_DifferentOrientation_False()
    {
        assertFalse(o.equals(new Orientation(120)));
    }

    @Test
    public void equals_SameInstance_True()
    {
        assertTrue(o.equals(o));
    }

    @Test
    public void equals_SameOrientation_True()
    {
        assertTrue(o.equals(new Orientation(0)));
    }

    @Test
    public void hashCode_SameOrientation_SameHashCode()
    {
        Orientation o = new Orientation(120);
        Orientation o2 = new Orientation(0, 1, -1);
        assertTrue(o.hashCode() == o2.hashCode());
    }

    @Test
    public void hashCode_DifferentOrientation_DifferentHashCode()
    {
        Orientation o = new Orientation(180);
        Orientation o2 = new Orientation(0, 1, -1);
        assertFalse(o.hashCode() == o2.hashCode());
    }

    @Test
    public void toString_East_ReturnExpectedString()
    {
        Orientation o = new Orientation(0);
        assertEquals("East", o.toString());
    }

    @Test
    public void toString_Northeast_ReturnExpectedString()
    {
        Orientation o = new Orientation(60);
        assertEquals("Northeast", o.toString());
    }

    @Test
    public void toString_Northwest_ReturnExpectedString()
    {
        Orientation o = new Orientation(120);
        assertEquals("Northwest", o.toString());
    }

    @Test
    public void toString_West_ReturnExpectedString()
    {
        Orientation o = new Orientation(180);
        assertEquals("West", o.toString());
    }

    @Test
    public void toString_Southwest_ReturnExpectedString()
    {
        Orientation o = new Orientation(240);
        assertEquals("Southwest", o.toString());
    }

    @Test
    public void toString_Southeast_ReturnExpectedString()
    {
        Orientation o = new Orientation(300);
        assertEquals("Southeast", o.toString());
    }
}
