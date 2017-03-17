package tigerisland.cucumbertest.tile;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import tigerisland.Jungle;
import tigerisland.Rocky;
import tigerisland.Terrain;
import tigerisland.Tile;
import tigerisland.Volcano;

/**
 * Created by mbregg on 3/16/17.
 */
public class CucumberTileStepper {
    public Tile tile = null;
    @Given("^We can examine a tile$")
    public void weCanExamineATile() throws Throwable {
       Terrain lTerrain = Rocky.getInstance();
       Terrain rTerrain = Jungle.getInstance();
       tile = new Tile(0,"left",lTerrain, rTerrain);
    }

    @When("^We examine the left and right portions of said tile$")
    public void weExamineTheLeftAndRightPortionsOfSaidTile() throws Throwable {
        // Wish I could word this test better, but I want something running.
    }

    @Then("^We find two non volcano terrain types$")
    public void weFindTwoNonVolcanoTerrainTypes() throws Throwable {
       assert(tile.getLeftTerrain() != Volcano.getInstance());
       assert(tile.getRightTerrain() != Volcano.getInstance());
    }
}
