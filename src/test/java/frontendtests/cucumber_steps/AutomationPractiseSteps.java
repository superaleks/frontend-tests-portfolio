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
    public void enterEmailIntoTheCreateAccountEmailInput(){
        loginPage.enterEmailIntoTheCreateAccountEmailInput();
    }

    @Step("Try to create an account")
    public void clickTheAccountCreationButton() {
        loginPage.clickTheAccountCreationButton();
    }

    @Step("Check for the presence of the account creation error message")
    public boolean errorMessageForAccountCreationIsPresent() {
        return loginPage.errorMessageForAccountCreationIsPresent();
    }

    @Step("Select the title as: {0}")
    public void selectTheTitle(String title) {
        accountCreationPage.selectTheTitle(title);
    }

    @Step("Enter the first name")
    public void enterTheFirstName() {
        accountCreationPage.enterTheFirstName();
    }

    @Step("Enter the last name: ")
    public void enterTheLastName() {
        accountCreationPage.enterTheLastName();
    }

    @Step("Enter the password")
    public void enterThePassword() {
        accountCreationPage.enterPassword();
    }

    @Step("Select the day of birth")
    public void selectTheDayOfBirth(){
        accountCreationPage.selectDateOfBirth();
    }

    @Step("Select the company")
        public void selectTheCompany() {
        accountCreationPage.enterCompany();
    }

    @Step("Enter the address")
    public void enterTheAddresses(){
        accountCreationPage.enterAddresses();
    }

    @Step("Enter the city")
    public void enterTheCity() {
        accountCreationPage.enterCity();
    }

    @Step("Select the state")
    public void selectTheState () {
        accountCreationPage.selectState();
    }

    @Step ("Select the country")
    public void selectCountry() {
        accountCreationPage.selectCountry();
    }

    @Step("Enter additional information")
    public void enterAdditionalInformation(){
        accountCreationPage.enterAdditionalInformation();
    }

    @Step("Enter home phone")
    public void enterHomePhone() {
        accountCreationPage.enterHomePhone();
    }

    @Step ("Enter mobile phone")
    public void enterMobilePhone (){
        accountCreationPage.enterMobilePhone();
    }

    @Step("Assign address alias")
    public void assignAddressAlias (){
        accountCreationPage.assignAnAddressAlias();
    }
}
