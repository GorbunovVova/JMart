package stepDefs;

import com.github.javafaker.Faker;
import cucumber.api.java.ru.Дано;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import pages.MainPage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UiStepDefs {

    MainPage mainPage = new MainPage();
    Map<String, String> data = new HashMap<>();

    @Когда("^ввести в \"(.+)\" значение \"(.+)\"$")
    public void fillField(String field, String value) throws AWTException {
        value = getData(value);
        WebElement element = (WebElement) mainPage.getElement(field);
        if (!element.getAttribute("value").equals("")) {
            element.click();
            Robot r = new Robot();
            r.mouseMove(element.getLocation().getX(), element.getLocation().y);
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_A);
            r.delay(500);
            r.keyRelease(KeyEvent.VK_CONTROL);
            r.keyRelease(KeyEvent.VK_A);
        }
        element.sendKeys(value);
        if (element.getAttribute("autocapitalize").equals("none")) {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_ENTER);
            r.delay(500);
            r.keyRelease(KeyEvent.VK_ENTER);
        }
    }

    @Когда("^выбрать в \"(.+)\" значение \"(.+)\"$")
    public void choose(String elementCollection, String value) {
        String finalValue = getData(value);
        List<WebElement> elements = (List<WebElement>) mainPage.getElement(elementCollection);
        Optional<WebElement> element = elements.stream().filter(x -> x.getAttribute("value").equals(finalValue)).findFirst();
        Assert.assertTrue("Елемент - " + value + " не найден", element.isPresent());
        element.get().findElement(By.xpath("./../label")).click();
    }

    @Когда("^загрузить в \"(.+)\" файл \"(.+)\"$")
    public void uploadFile(String name, String value) {
        value = getData(value);
        WebElement element = (WebElement) mainPage.getElement(name);
        element.sendKeys(System.getProperty("user.dir") + "/src/test/resources/" + value);
    }

    @Когда("^нажать на \"(.+)\"$")
    public void press(String name) {
        WebElement element = (WebElement) mainPage.getElement(name);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            element.submit();
        }
    }

    @Тогда("^появилось \"(.+)\"$")
    public void exist(String name) {
        WebElement element = (WebElement) mainPage.getElement(name);
        Assert.assertTrue(name + " не появился", element.isDisplayed());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Тогда("^проверить содержимое таблицы \"(.+)\"$")
    public void checkTable(String name, Map<String, String> expectedValues) {
        WebElement element = (WebElement) mainPage.getElement(name);
        List<WebElement> rows = element.findElements(By.xpath(".//tr/td/.."));
        Map<String, String> actualValues = new HashMap<>();
        rows.forEach(x -> actualValues.put(x.findElement(By.xpath("./td[1]")).getText(), x.findElement(By.xpath("./td[2]")).getText()));

        for (Map.Entry<String, String> entry : expectedValues.entrySet()) {
            String value = getData(entry.getValue());
            Assert.assertEquals("ожидалось - " + entry.getKey() + " = " + value + "; фактическое - "
                    + actualValues.get(entry.getKey()), value, actualValues.get(entry.getKey()));

        }
    }

    @Дано("тестовые данные")
    public void initializeTestData(Map<String, String> expectedValues) {
        expectedValues.forEach((key, value) -> {
            if (value.startsWith("generate.")) data.put(key, generateValue(value));
            else data.put(key, value);
        });
        System.out.println(data);
    }

    private String generateValue(String value) {
        String result = null;
        Faker inFaker = new Faker(new Locale("en-IN"));
        switch (value) {
            case ("generate.name"):
                result = inFaker.name().firstName();
                break;
            case ("generate.lastname"):
                result = inFaker.name().lastName();
                break;
            case ("generate.email"):
                result = inFaker.internet().emailAddress();
                break;
            case ("generate.phone"):
                result = inFaker.regexify("[0-9]{10}");
                break;
            case ("generate.address"):
                result = inFaker.address().fullAddress();
        }
        return result;
    }

    private String getData(String value) {
        String result = value;
        if (value.contains("${")) {
            Pattern pattern = Pattern.compile("\\$\\{.+?\\}");
            Matcher matcher = pattern.matcher(value);
            while (matcher.find()) {
                String match = value.substring(matcher.start(), matcher.end());
                String dataParameter = match;
                dataParameter = dataParameter.replace("${", "");
                dataParameter = dataParameter.replace("}", "");
                result = result.replace(match, data.get(dataParameter));
            }
        }
        return result;
    }
}
