package frontendtests.serenity_definitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import frontendtests.cucumber_steps.AutomationPractiseSteps;
import net.thucydides.core.annotations.Steps;

import static org.junit.Assert.assertTrue;

public class AutomationPracticeStepDefinition {

    @Steps
    AutomationPractiseSteps step;

    @Given("^the user has navigated to the homepage$")
    public void theUserHasNavigatedToTheHomepage() {
        step.clickTheLoginLink();
        assertTrue(step.checkIfAccountButtonIsPresent());
    }

    @When("^he tries to register$")
    public void heTriesToRegister() {
        System.out.println("He can register");
    }

    @Then("^he will see the page where he can register or log in$")
    public void heWillSeeThePageWhereHeCanRegisterOrLogIn() {
        System.out.println("He can see the page");
    }

    @Then("^he can confirm that the \"([^\"]*)\" in the header is present$")
    public void heCanConfirmThatTheInTheHeaderIsPresent(String number)  {
        boolean result = false;
        if(step.getCallUsNowNumber(number).contains(number)) {
            result = true;
        }
        assertTrue(result);

    }
}
