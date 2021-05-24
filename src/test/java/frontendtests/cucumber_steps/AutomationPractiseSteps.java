package frontendtests.cucumber_steps;


import frontendtests.page_objects.BasePage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class AutomationPractiseSteps extends ScenarioSteps {

    BasePage basePage;

    @Step("Click the Sign In link")
    public void clickTheLoginLink() {
        basePage.clickTheLoginLink();
    }
    @Step("Verify if the create account button is present")
    public boolean checkIfAccountButtonIsPresent() {
         return basePage.checkIfAccountButtonIsPresent();
    }
}
