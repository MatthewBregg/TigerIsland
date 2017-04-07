package tigerisland.cucumbertest.steppers;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import tigerisland.hex.Hex;
import tigerisland.terrains.Jungle;
import tigerisland.terrains.Rocky;
import tigerisland.terrains.Terrain;
import tigerisland.terrains.Volcano;
import tigerisland.tile.Tile;

public class TileTerrainNonVolcano {
    public Tile tile = null;
    @Given("^We can examine a tile$")
    public void weCanExamineATile() throws Throwable {
       Terrain lTerrain = Rocky.getInstance();
       Terrain rTerrain = Jungle.getInstance();
       tile = new Tile(0, lTerrain, rTerrain);
    }

    @When("^We examine the left and right portions of said tile$")
    public void weExamineTheLeftAndRightPortionsOfSaidTile() throws Throwable {
        // Wish I could word this test better, but I want something running.
    }

    @Then("^We find two non volcano terrain types$")
    public void weFindTwoNonVolcanoTerrainTypes() throws Throwable {
       Assert.assertTrue(tile.getLeftTerrain() != Volcano.getInstance());
       Assert.assertTrue(tile.getRightTerrain() != Volcano.getInstance());
    }

    @Then("^We find one volcano terrain types$")
    public void weFindOneVolcanoTerrainTypes() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertTrue(tile.getReferenceTerrain() == Volcano.getInstance());
    }
}
