package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainPage extends BasePage {
    @FindBy(xpath = "//ul[@class='main-side-menu']/li/a")
    public List<WebElement> menuItems;

    @FindBy(xpath = "//ul[@class='main-side-menu']/li/ul//a")
    public List<WebElement> subMenuItems;

    @FindBy(xpath = "//div[@class='page']")
    public WebElement adminPanel;

    @FindBy(xpath = "//table")
    public WebElement table;

    @FindBy(xpath = "//table/thead/tr//th")
    public List<WebElement> columns;

}

