package frontendtests.serenity_definitions;

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
        step.navigateToTheHomePage();
    }

    @When("^he navigates to the sign in form$")
    public void heNavigatesToTheSignInForm() {
        step.clickTheLoginLink();
    }

    @Then("^he will see the page where he can register or log in$")
    public void heWillSeeThePageWhereHeCanRegisterOrLogIn() {
        assertTrue(step.checkIfAccountButtonIsPresent());
    }

    @Then("^he can confirm that the \"([^\"]*)\" in the header is present$")
    public void heCanConfirmThatTheInTheHeaderIsPresent(String number)  {
        boolean result = false;
        if(step.getCallUsNowNumber(number).contains(number)) {
            result = true;
        }
        System.out.println("Successfully obtainer the number:" + result);
        assertTrue(result);

    }

    @Given("^the user has navigated to the account creation page$")
    public void theUserHasNavigatedToTheAccountCreationPage() {
        step.navigateToTheAccountCreationPage();
    }

    @When("^he enters the \"([^\"]*)\" to the account creation form$")
    public void heEntersTheToTheAccountCreationForm(String email) {
        step.enterEmailIntoTheCreateAccountEmailInput(email);
        step.clickTheAccountCreationButton();
    }

    @Then("^he can create an account succesfully$")
    public void heCanCreateAnAccountSuccesfully() {
        boolean result = true;
        if(step.errorMessageForAccountCreationIsPresent()) {
            result = false;
        }
        System.out.println(result + " Successfull account creation!");
        assertTrue(result);
    }

    @Then("^he sees an error message$")
    public void heSeesAnErrorMessage() {
        System.out.println((step.errorMessageForAccountCreationIsPresent()) + " THIS NEEDS TO BE TRUE");
        assertTrue(step.errorMessageForAccountCreationIsPresent());
    }
}
