package frontendtests.test_runners;


import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

//Default Test Runner. Used by the Gradle default "test" task.
@RunWith(CucumberWithSerenity.class)

@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber-report.json"},
        features = {"src/test/java/frontendtests/features/AccountCreationFlow.feature"},
        glue = {"frontendtests.serenity_definitions/"}
)
public class TestRunner {

}