package tigerisland.cucumbertest.steppers;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import tigerisland.player.Player;

public class CucumberPlayerPieceInformation {

    int piecesFound = 0;
    Player player = null;

    void init() {
        piecesFound = 0;
        player = new Player();
    }

    @Given("^Fresh Player with no pieces placed$")
    public void freshPlayerWithNoPiecesPlaced() throws Throwable {
        init();
    }

    @When("^Player attempts to view the amount of villagers available$")
    public void playerAttemptsToViewTheAmountOfVillagersAvailable() throws Throwable {
        piecesFound = player.getVillagerCount();
    }

    @Then("^Player sees (\\d+) of above piece$")
    public void playerSeesOfAbovePiece(int piecesExpected) throws Throwable {
        Assert.assertEquals(piecesExpected, piecesFound);
    }

    @When("^Player attempts to view the amount of totoros available$")
    public void playerAttemptsToViewTheAmountOfTotorosAvailable() throws Throwable {
        piecesFound = player.getTotoroCount();
    }

    @When("^Player attempts to view the amount of Tigers available$")
    public void playerAttemptsToViewTheAmountOfTigersAvailable() throws Throwable {
        piecesFound = player.getTigerCount();
    }
}
