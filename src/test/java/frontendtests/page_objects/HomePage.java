package frontendtests.page_objects;

import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public static final String SEARCH_INPUT = "//input[@id='search_query_top']";

    public static final String SHOPPING_CART = "//div[@class='shopping_cart']";

    public static final String WOMEN_LINK = "//a[@title='Women']";

    public static final String DRESSES_LINK = "//li/ul/li/a[@title='Dresses']";

    public static final String T_SHIRTS_LINK = "//li/ul/li/a[@Title='T-shirts']";

    public HomePage(WebDriver driver) {
        super(driver);
    }


}
