package frontendtests.page_objects;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;

public class AccountCreationPage extends BasePage {

    public AccountCreationPage(WebDriver driver) {
        super(driver);
    }

    public static final String MALE_TITLE_RADIO_BUTTON = "//label[@for='id_gender1']";

    public static final String FEMALE_TITLE_RADIO_BUTTON = "//label[@for='id_gender1']";

    public static final String FIRST_NAME_INPUT = "//input[@id='customer_firstname']";

    public static final String LAST_NAME_INPUT = "//input[@id='customer_lastname']";

    public static final String EMAIL_INPUT  = "//input[@id='email']";

    public static final String PASSWORD_INPUT = "//input[@id='passwd']";

    public static final String SELECT_DAYS = "//select[@id='days']";

    public static final String SELECT_MONTHS = "//select[@id='months']";

    public static final String SELECT_YEARS = "//select[@id='years']";

    public static final String NEWSLETTER_CHECKBOX = "//input[@id='newsletter']";

    public static final String SPECIAL_OFFER_CHECKBOX = "//input[@id='optin']";

    public static final String COMPANY_INPUT = "//input[@id='company']";

    Faker faker = new Faker();
    //String age = faker.name()
    String name = faker.name().fullName(); // Miss Samanta Schmidt
    String firstName = faker.name().firstName(); // Emory
    String lastName = faker.name().lastName(); // Barton
    String streetAddress = faker.address().streetAddress(); // 60018 Sawayn Brooks Suite 449


    public void selectTheTitle(String title) {
        if(title == "Mr"){
        $(MALE_TITLE_RADIO_BUTTON).click();
        }
        else {
            $(FEMALE_TITLE_RADIO_BUTTON).click();
        }
    }
    public void enterTheFirstName() {
        $(FIRST_NAME_INPUT).type(firstName);
    }

}
