package frontendtests.page_objects;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Array;
import java.util.List;
import java.util.stream.Collectors;

@DefaultUrl("http://automationpractice.com/index.php?controller=my-account")
public class MyAccountPage extends BasePage {

    public static final String MY_ACCOUNT_LINK_IDENTIFYER = "//ul[class='myaccount-link-list']//child::li";

    public static final String ORDER_HISTORY_AND_DETAILS_BUTTON = "//a[@title='Orders']";

    public static final String MY_CREDIT_SLIPS_BUTTON = "//a[@title='Credit slips']";

    public static final String MY_ADDRESSES_BUTTON = "//a[@title='Addresses']";

    public static final String MY_PERSONAL_INFORMATION_BUTTON = "//a[@title='Information']";

    public static final String MY_WISHLISTS_BUTTON = "//a[@title='My wishlists']";


    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getMyAccountLinks() {
        return findAll(MY_ACCOUNT_LINK_IDENTIFYER).stream()
                .map(WebElementFacade::getText)
                .collect(Collectors.toList());
    }


}
