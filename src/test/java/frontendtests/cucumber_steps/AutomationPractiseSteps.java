package frontendtests.cucumber_steps;


import frontendtests.page_objects.AccountCreationPage;
import frontendtests.page_objects.LogInPage;
import frontendtests.page_objects.BasePage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class AutomationPractiseSteps extends ScenarioSteps {

    BasePage basePage;
    LogInPage loginPage;
    AccountCreationPage accountCreationPage;

    @Step("Navigate to the homepage")
    public void navigateToTheHomePage() {
        basePage.navigateToTheHomePage();
    }

    @Step("Click the Sign In link")
    public void clickTheLoginLink() {
        basePage.clickTheLoginLink();
    }

    @Step("Verify if the create account button is present")
    public boolean checkIfAccountButtonIsPresent() {
         return basePage.checkIfAccountButtonIsPresent();
    }

    @Step("Verify the presence of the Call us now number")
    public String getCallUsNowNumber(String number) {
        return basePage.getCallUsNowNumber(number);
    }

    @Step("Navigate to the account creation page")
    public void navigateToTheAccountCreationPage () {
        loginPage.navigateToTheAccountCreationPage();
    }

    @Step("Enter the email into the create account email input")
    public void enterEmailIntoTheCreateAccountEmailInput(String email){
        loginPage.enterEmailIntoTheCreateAccountEmailInput(email);
    }

    @Step("Try to create an account")
    public void clickTheAccountCreationButton() {
        loginPage.clickTheAccountCreationButton();
    }

    @Step("Check for the presence of the account creation error message")
    public boolean errorMessageForAccountCreationIsPresent() {
        return loginPage.errorMessageForAccountCreationIsPresent();
    }

    @Step ("Select the title as: {0}")
    public void selectTheTitle(String title) {
        accountCreationPage.selectTheTitle(title);
    }

    @Step ("Enter the first name as: {0}")
    public void enterTheFirstName() {
        accountCreationPage.enterTheFirstName();
    }

}
