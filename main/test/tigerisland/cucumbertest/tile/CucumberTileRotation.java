package tigerisland.cucumbertest.tile;

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


public class CucumberTileRotation {
    public Tile tileWithEastOrientation = null;
    public Tile tileWithNWOrientation = null;
    @Given("^We have a tile$")
    public void weHaveATile() throws Throwable {
        tileWithEastOrientation = new Tile(0, Orientation.getEast());
        tileWithNWOrientation = new Tile(1, Orientation.getNorthWest());
    }

    @When("^We rotate said tile$")
    public void weCanRotateTheTile() throws Throwable {
        tileWithEastOrientation.rotate();
    }

    @Then("^The tile's orientation changes$")
    public void weFindThatOrientationChange() throws Throwable {
        Assert.assertTrue(tileWithEastOrientation != tileWithNWOrientation);
        Assert.assertTrue(tileWithEastOrientation.getOrientation()==Orientation.getNorthEast());

    }
}
