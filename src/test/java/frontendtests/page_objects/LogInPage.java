package frontendtests.page_objects;

import com.github.javafaker.Faker;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.WebDriver;

@DefaultUrl("http://automationpractice.com/index.php?controller=authentication&back=my-account")
public class LogInPage extends BasePage {

    public LogInPage(WebDriver driver) {
        super(driver);
    }

    public static final String CREATE_AN_ACCOUNT_EMAIL_ADDRESS_INPUT = "//input[@id='email_create']";

    public static final String CREATE_AN_ACCOUNT_BUTTON = "//button[@id='SubmitCreate']";

    public static final String LOGIN_EMAIL_ADDRESS_INPUT = "//input[@id='email']";

    public static final String LOGIN_PASSWORD_INPUT = "//input[@id='passwd']";

    public static final String FORGOT_YOUR_PASSWORD_LINK = "//a[@title='Recover your forgotten password']";

    public static final String SIGN_IN_BUTTON = "//button[@id='SubmitLogin']";

    public static final String INVALID_EMAIL_ADDRESS_MESSAGE = "//div[@id='create_account_error']";

    final String email = faker.internet().emailAddress();

    public void navigateToTheAccountCreationPage() {
        open();
    }

    public void enterEmailIntoTheCreateAccountEmailInput(){
       $(CREATE_AN_ACCOUNT_EMAIL_ADDRESS_INPUT).type(email);
    }

    public void clickTheAccountCreationButton() {
        $(CREATE_AN_ACCOUNT_BUTTON).click();

    }

    public boolean errorMessageForAccountCreationIsPresent() {
        return $(INVALID_EMAIL_ADDRESS_MESSAGE).isDisplayed();
    }


}
