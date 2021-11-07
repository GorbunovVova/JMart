package steps;

import com.google.common.collect.Ordering;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.AuthorizedPage;
import pages.MainPage;
import stepDefs.BaseStepDefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UiSteps {

    private AuthorizedPage authorizedPage;
    private MainPage mainPage;
    private int sortedIndex;
    private Properties props = BaseStepDefs.getProperties();

    public UiSteps(AuthorizedPage authorizedPage, MainPage mainPage) {
        this.authorizedPage = authorizedPage;
        this.mainPage = mainPage;
    }

    @Step("выполняется авторизация")
    public void auth() {
        String login = props.getProperty("login");
        String password = props.getProperty("password");
        authorizedPage.loginInput.sendKeys(login);
        authorizedPage.passwordInput.sendKeys(password);
        authorizedPage.signInButton.click();
    }

    @Step("выбирается пункт меню {0}")
    public void select(String menuItem) {
        WebElement item = mainPage.menuItems.stream().filter(x -> x.getText().equals(menuItem)).findFirst().get();
        item.click();
    }

    @Step("выбирается пункт подменю {0}")
    public void selectSub(String subMenuItem) {
        WebElement subItem = mainPage.subMenuItems.stream().filter(x -> x.getText().equals(subMenuItem)).findFirst().get();
        subItem.click();
    }

    @Step("появилась {0}")
    public void isVisible(String element) {
        switch (element) {
            case ("админ-панель"):
                mainPage.wait.until(ExpectedConditions.visibilityOf(mainPage.adminPanel));
                break;
            case ("таблица с игроками"):
                mainPage.wait.until(ExpectedConditions.visibilityOf(mainPage.table));
                break;
            default:
                Assert.fail("такого элемента нет");
        }
    }

    @Step("выполняется сортировка таблицы по {0}")
    public void sortTable(String column) throws InterruptedException {
        for (int i = 0; i < mainPage.columns.size(); i++) {
            WebElement col = mainPage.columns.get(i);
            if (col.getText().equals(column)) {
                col.findElement(By.xpath("./a")).click();
                sortedIndex = i + 1;
                Thread.sleep(2000);
                return;
            }
        }
        Assert.fail("Столбца - " + column + "нет в таблице");
    }

    @Step("проверяется сортировка таблицы")
    public void checkSortedTable() {
        List<WebElement> sortedCells = mainPage.table.findElements(By.xpath("./tbody//td[" + sortedIndex + "]"));
        List<String> sortedValues = new ArrayList<>();
        sortedCells.forEach(cell -> sortedValues.add(cell.getText()));
        boolean isSorted = Ordering.natural().isOrdered(sortedValues);
        Assert.assertTrue("таблица не отсортирована", isSorted);
    }
}
