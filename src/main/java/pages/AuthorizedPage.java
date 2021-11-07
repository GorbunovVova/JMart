package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class AuthorizedPage extends BasePage {
    @FindBy(id="UserLogin_username")
    public WebElement loginInput;

    @FindBy(id="UserLogin_password")
    public WebElement passwordInput;

    @FindBy(xpath = "//input[@value='Sign in']")
    public WebElement signInButton;

}
