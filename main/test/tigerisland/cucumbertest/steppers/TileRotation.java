package tigerisland.cucumbertest.steppers;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import tigerisland.terrains.Jungle;
import tigerisland.terrains.Rocky;
import tigerisland.terrains.Terrain;
import tigerisland.terrains.Volcano;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;


public class TileRotation {
    public Tile tileWithEastOrientation = null;
    public Tile tileWithNWOrientation = null;
    @Given("^We have a tile$")
    public void weHaveATile() throws Throwable {
        tileWithEastOrientation = new Tile(0);
        tileWithNWOrientation = new Tile(1);
        tileWithNWOrientation.rotate();
        tileWithNWOrientation.rotate();
    }

    @When("^We rotate said tile$")
    public void weCanRotateTheTile() throws Throwable {
        tileWithEastOrientation.rotate();
    }

    @Then("^The tile's orientation changes$")
    public void weFindThatOrientationChange() throws Throwable {
        Assert.assertTrue(tileWithEastOrientation != tileWithNWOrientation);
        Assert.assertTrue(tileWithEastOrientation.getOrientation().equals(Orientation.getNorthEast()));

    }
}
