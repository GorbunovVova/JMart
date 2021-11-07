import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/features",
        glue = {"stepDefs"},
        plugin = {"io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm", "pretty"},
        tags = "@all"
)
public class TestRunner {
}