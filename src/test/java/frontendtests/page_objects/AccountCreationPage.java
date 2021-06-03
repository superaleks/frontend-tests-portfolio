package frontendtests.page_objects;

import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.WebDriver;

public class AccountCreationPage extends BasePage {

    public static final String MALE_TITLE_RADIO_BUTTON = "//label[@for='id_gender1']";

    public static final String FEMALE_TITLE_RADIO_BUTTON = "//label[@for='id_gender1']";

    public static final String FIRST_NAME_INPUT = "//input[@id='customer_firstname']";

    public static final String LAST_NAME_INPUT = "//input[@id='customer_lastname']";

    public static final String PASSWORD_INPUT = "//input[@id='passwd']";

    public static final String SELECT_DAYS = "//select[@id='days']";

    public static final String SELECT_MONTHS = "//select[@id='months']";

    public static final String SELECT_YEARS = "//select[@id='years']";

    public static final String NEWSLETTER_CHECKBOX = "//input[@id='newsletter']";

    public static final String SPECIAL_OFFER_CHECKBOX = "//input[@id='optin']";

    public static final String COMPANY_INPUT = "//input[@id='company']";

    public static final String PASSWORD = "PASSWORD123!";

    public static final String ADDRESS_LINE_1_INPUT = "//input[@name='address1']";

    public static final String ADDRESS_LINE_2_INPUT = "//input[@name='address2']";

    public static final String CITY_INPUT = "//input[@name='city']";

    public static final String STATE_DROPDOWN =  "//select[@name='id_state']";

    public static final String ZIP_CODE_INPUT = "//input[@id='postcode']";

    public static final String COUNTRY_DROPDOWN = "//select[@name='id_country']";

    public static final String ADDITIONAL_INFORMATION_TEXTAREA = "//textarea[@id='other']";

    public static final String HOME_PHONE_INPUT = "//input[@id='phone']";

    public static final String MOBILE_PHONE_INPUT = "//input[@id='phone_mobile']";

    public static final String ADDRESS_ALIAS_INPUT = "//input[@id='alias']";

    final String firstName = faker.name().firstName();

    final String lastName = faker.name().lastName();

    final String company = faker.company().name();

    final String address = faker.address().streetAddress();

    final String city = faker.address().city();

    final String state = faker.address().state();

    final String zipCode = faker.address().zipCode();

    final String randomTextAdditionalInformation = faker.howIMetYourMother().catchPhrase();

    final String phone = faker.number().digits(8);

    final String addressAlias = faker.harryPotter().house();

    public AccountCreationPage(WebDriver driver) {
        super(driver);
    }

    public void selectTheTitle() {
        $(FEMALE_TITLE_RADIO_BUTTON).click();
    }

    public void enterTheFirstName() {
        $(FIRST_NAME_INPUT).type(firstName);

    }

    public void enterTheLastName(){
        $(LAST_NAME_INPUT).type(lastName);
    }

    public void enterPassword() {
        $(PASSWORD_INPUT).type(PASSWORD);
    }
    public void selectDateOfBirth() {
        WebElementFacade daysDropdown = $(SELECT_DAYS);
        WebElementFacade monthsDropdown = $(SELECT_MONTHS);
        WebElementFacade yearsDropdown = $(SELECT_YEARS);
        daysDropdown.selectByValue("17");
        monthsDropdown.selectByValue("1");
        yearsDropdown.selectByValue("1993");
    }

    public void enterCompany() {
        $(COMPANY_INPUT).type(company);
    }

    public void enterAddresses() {
        $(ADDRESS_LINE_1_INPUT).type(address);
        $(ADDRESS_LINE_2_INPUT).type(address);
    }

    public void enterCity(){
        $(CITY_INPUT).type(city);
    }

    public void selectState(){
        WebElementFacade daysDropdown = $(STATE_DROPDOWN);
        daysDropdown.selectByVisibleText(state);
    }

    public void enterZipCode() {
        $(ZIP_CODE_INPUT).type(zipCode);
        System.out.println("Zipcode is:" + zipCode);
    }

    public void selectCountry(){
        WebElementFacade countryDropdown = $(COUNTRY_DROPDOWN);
        countryDropdown.selectByVisibleText("United States");
        }

    public void enterAdditionalInformation (){
        $(ADDITIONAL_INFORMATION_TEXTAREA).type(randomTextAdditionalInformation);
    }

    public void enterHomePhone() {
        $(HOME_PHONE_INPUT).type(phone);
    }

    public void enterMobilePhone() {
        $(MOBILE_PHONE_INPUT).type(phone);
    }

    public void assignAnAddressAlias() {
        $(ADDRESS_ALIAS_INPUT).type(addressAlias);
    }
}
