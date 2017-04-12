package cucumbertests.tile;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by mbregg on 3/16/17.
 */
public class CucumberTileStepper {
    public tile.Tile tile = null;
    @Given("^We can examine a tile$")
    public void weCanExamineATile() throws Throwable {
       terrain.Terrain lTerrain = terrain.Rocky.getInstance();
       terrain.Terrain rTerrain = terrain.Jungle.getInstance();
       tile = new tile.Tile(0,"left",lTerrain, rTerrain);
    }

    @When("^We examine the left and right portions of said tile$")
    public void weExamineTheLeftAndRightPortionsOfSaidTile() throws Throwable {
        // Wish I could word this test better, but I want something running.
    }

    @Then("^We find two non volcano terrain types$")
    public void weFindTwoNonVolcanoTerrainTypes() throws Throwable {
       assert(tile.getLeftTerrain() != terrain.Volcano.getInstance());
       assert(tile.getRightTerrain() != terrain.Volcano.getInstance());
    }
}
