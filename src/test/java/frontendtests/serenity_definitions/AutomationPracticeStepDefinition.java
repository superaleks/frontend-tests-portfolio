package frontendtests.serenity_definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import frontendtests.cucumber_steps.AutomationPractiseSteps;

import net.thucydides.core.annotations.Steps;

import static org.junit.Assert.assertTrue;

public class AutomationPracticeStepDefinition {

    @Steps
    AutomationPractiseSteps user;

    @Given("^the user has navigated to the homepage$")
    public void theUserHasNavigatedToTheHomepage() {
        user.navigateToTheHomePage();
    }

    @When("^he navigates to the sign in form$")
    public void heNavigatesToTheSignInForm() {
        user.clickTheLoginLink();
    }

    @Then("^he will see the page where he can register or log in$")
    public void heWillSeeThePageWhereHeCanRegisterOrLogIn() {
        assertTrue(user.checkIfAccountButtonIsPresent());
    }

    @Then("^he can confirm that the \"([^\"]*)\" in the header is present$")
    public void heCanConfirmThatTheInTheHeaderIsPresent(String number)  {
        boolean result = user.getCallUsNowNumber(number).contains(number);
        System.out.println("Successfully obtainer the number:" + result);
        assertTrue(result);

    }

    @Given("^the user has navigated to the account creation page$")
    public void theUserHasNavigatedToTheAccountCreationPage() {
        user.navigateToTheAccountCreationPage();
    }

    @When("^he enters the \"([^\"]*)\" to the account creation form$")
    public void heEntersTheToTheAccountCreationForm() {
        user.enterEmailIntoTheCreateAccountEmailInput();
        user.clickTheAccountCreationButton();
    }

    @Then("^he can initiate the account creation flow successfully$")
    public void heCanInitiateTheAccountCreationFlowSuccessfully() {
        boolean result = !user.errorMessageForAccountCreationIsPresent();
        assertTrue(result);
    }

    @Then("^he sees an error message$")
    public void heSeesAnErrorMessage() {
        assertTrue(user.errorMessageForAccountCreationIsPresent());
    }

    @When("^he enters the email to the account creation form and proceeds to the account creation$")
    public void heEntersTheEmailToTheAccountCreationFormAndProceedsToTheAccountCreation() {
        user.enterEmailIntoTheCreateAccountEmailInput();
        user.clickTheAccountCreationButton();

    }

    @Then("^he can succesfully create an account$")
    public void heCanSuccessfullyCreateAnAccount() {

    }

    @And("^he enters the all the necessary data in the form, generated randomly$")
    public void heEntersTheAllTheNecessaryDataInTheFormGeneratedRandomly() {
        user.selectTheTitle();
        user.enterTheFirstName();
        user.enterTheLastName();
        user.enterThePassword();
        user.selectTheDayOfBirth();
        user.selectTheCompany();
        user.enterTheAddresses();
        user.enterTheCity();
        user.selectTheState();
        user.enterThePostalCode();
        user.selectCountry();
        user.enterAdditionalInformation();
        user.enterHomePhone();
        user.enterMobilePhone();
        user.assignAddressAlias();
    }

}
