package stepDefs;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.restassured.RestAssured;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.TestProperties;

import java.time.Duration;
import java.util.Properties;

public class BaseStepDefs {
    private static WebDriver driver;

    private static Properties properties = TestProperties.getInstance().getProperties();

    public static Properties getProperties() {
        return properties;
    }

    public static synchronized WebDriver getDriver() {
        if (driver==null) webDriverSetUp();
        return driver;
    }

    @Before
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
        RestAssured.baseURI = properties.getProperty("restURL");
    }

    public static void webDriverSetUp() {
        driver = new ChromeDriver();
        String baseUrl = properties.getProperty("app.url");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    @After
    public static void tearDown() {
        if (driver != null) driver.quit();
    }
}
