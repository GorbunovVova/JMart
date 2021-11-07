package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepDefs.BaseStepDefs;

import java.time.Duration;

public class BasePage {

    public Wait<WebDriver> wait;

    BasePage() {
        WebDriver driver = BaseStepDefs.getDriver();
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofSeconds(1));
    }
}
