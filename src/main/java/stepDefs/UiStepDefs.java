package stepDefs;

import cucumber.api.java.ru.Когда;
import pages.AuthorizedPage;
import pages.MainPage;
import steps.UiSteps;

public class UiStepDefs {

    UiSteps uiSteps = new UiSteps(new AuthorizedPage(), new MainPage());

    @Когда("^выбирается пункт меню \"(.+)\"$")
    public void select(String menuItem) {
        uiSteps.select(menuItem);
    }

    @Когда("^выбирается пункт подменю \"(.+)\"$")
    public void selectSub(String subMenuItem) {
        uiSteps.selectSub(subMenuItem);
    }

    @Когда("^появил(?:ась|ся|ось) \"(.+)\"$")
    public void isVisible(String element) {
        uiSteps.isVisible(element);
    }

    @Когда("^выполняется сортировка таблицы по \"(.+)\"$")
    public void sortTable(String column) throws InterruptedException {
        uiSteps.sortTable(column);
    }

    @Когда("^проверяется сортировка таблицы$")
    public void checkSortedTable() {
        uiSteps.checkSortedTable();
    }

    @Когда("^выполняется авторизация$")
    public void auth() {
        uiSteps.auth();
    }


}
