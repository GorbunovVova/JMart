# Инструкция по запуску

- проверить соответсвтвие ChromeDriver локальной версии браузера (лежит в _drivers/chromedriver.exe_, версия - 95)
- перейти в файл src/test/java/TestRunner.java
- в _tags_ указать нужный тег (@all - все тесты, @ui - UI-тест, @api - REST-тест)
- запустить команду _mvn clean test_ (или нажать зеленый треугольник напротив _public class TestRunner {_)
- чтобы посмотреть отчет - перейти в _Plugins -> allure -> allure:serve_
