package CucumberTests.Example.CucumberTestExample.CucumberSteppers;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * Created by mbregg on 3/16/17.
 */

public class ZExampleCucumberSteps {
    String cucumberTaste = "";
    @Given("^I have a cucumber$")
    public void iHaveACucumber() throws Throwable {
        System.out.println("I have a cucumber");
        // If this step fails, throws exception to indicate to cucumber
    }

    @And("^I place it in my mouth and attempt to bite into it$")
    public void iPlaceItInMyMouthAndAttemptToBiteIntoIt() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        cucumberTaste = "worse Than Pickle, but Good";
        // If this step fails, throws exception to indicate to cucumber
    }

    @Then("^I should enjoy something, although make note that it would be better tasting as a pickle$")
    public void iShouldEnjoySomethingAlthoughMakeNoteThatItWouldBeBetterTastingAsAPickle() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
       if (!(cucumberTaste.contains("Pickle") && cucumberTaste.contains("Good")) )
        throw new PendingException(); // Throw exception is test failed and cucumber doesn't taste good, but bring memories of pickles.
    }
}
