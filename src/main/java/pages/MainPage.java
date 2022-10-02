package pages;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MainPage extends BasePage {

    @Title("поле \"Имя\"")
    @FindBy(id = "firstName")
    public WebElement firstName;

    @Title("поле \"Фамилия\"")
    @FindBy(id = "lastName")
    public WebElement lastName;

    @Title("поле \"Email\"")
    @FindBy(id = "userEmail")
    public WebElement userEmail;

    @Title("радиобаттон \"Пол\"")
    @FindBy(name = "gender")
    public List<WebElement> gender;

    @Title("поле \"Телефон\"")
    @FindBy(id = "userNumber")
    public WebElement userNumber;

    @Title("поле \"Дата рождения\"")
    @FindBy(id = "dateOfBirthInput")
    public WebElement dateOfBirthInput;

    @Title("поле \"Subjects\"")
    @FindBy(id = "subjectsInput")
    public WebElement subjectsInput;

    @Title("чекбокс \"Hobbies\"")
    @FindBy(xpath = "//input[contains(@id, 'hobbies-checkbox')]")
    public List<WebElement> hobbies;

    @Title("ввод \"Загрузить картинку\"")
    @FindBy(id = "uploadPicture")
    public WebElement uploadPicture;

    @Title("поле \"Адрес\"")
    @FindBy(id = "currentAddress")
    public WebElement currentAddress;

    @Title("комбобокс \"Штат\"")
    @FindBy(xpath = "//div[@id='stateCity-wrapper']/div[2]//input")
    public WebElement state;

    @Title("комбобокс \"Город\"")
    @FindBy(xpath = "//div[@id='stateCity-wrapper']/div[3]//input")
    public WebElement city;

    @Title("кнопку \"Submit\"")
    @FindBy(xpath = "//button[@id='submit']")
    public WebElement submit;

    @Title("поле \"Thanks for submitting the form\"")
    @FindBy(xpath = "//*[text()='Thanks for submitting the form']")
    public WebElement submittingTheForm;

    @Title("таблица \"Результат\"")
    @FindBy(xpath = "//table")
    public WebElement table;

    public Object getElement (String title) {
        Field[] fields = this.getClass().getDeclaredFields();
        Optional<Field> field = Arrays.stream(fields).filter(x -> x.getAnnotation(Title.class).value().equalsIgnoreCase(title)).findFirst();
        Assert.assertTrue("Елемент - " + title + " не найден", field.isPresent());
        Object o = null;
        try {
            o = field.get().get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return o;
    }

}

