package frontendtests.page_objects;

import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Array;

public class BasePage extends PageObject {

    public static final String LOG_IN_LINK = "//a[@class='login']";

    public static final String GET_SAVINGS_NOW_BUTTON  = "//img[@class='img-responsive']";

    public static final String CREATE_ACCOUNT_BUTTON = "//button[@name='SubmitCreate']";

    public static final String CALL_US = "//span[@class='shop-phone']";

    public static final String SIGN_IN_LINK = "//a[@class='login']";

    public static final String NEWSLETTER_INPUT = "//input[@name='email']";

    public static final String FACEBOOK_LINK = "//li[@class='facebook']/a";

    public static final String TWITTER_LINK = "//li[@class='twitter']/a";

    public static final String YOUTUBE_LINK = "//li[@class='youtube']/a";

    public static final String GOOGLE_PLUS_LINK = "//li[@class='google-plus']/a";

  public BasePage(WebDriver driver){
      super(driver);
  }

  public void navigateToTheHomePage() {
      open();
  }

  public void clickTheLoginLink() {

      $(LOG_IN_LINK).click();
  }

  public void clickGetSavingsNowButton() {
      $(GET_SAVINGS_NOW_BUTTON).click();
  }

  public String getCallUsNowNumber(String number) {
      System.out.println("The Call us now number is: " + $(CALL_US).getText());
      String phoneNumber = $(CALL_US).getText();
      return phoneNumber;
  }


  public boolean checkIfAccountButtonIsPresent(){
      return $(CREATE_ACCOUNT_BUTTON).isPresent();
  }

}
