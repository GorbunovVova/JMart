# Инструкция по запуску

- проверить соответсвтвие ChromeDriver локальной версии браузера (лежит в _drivers/chromedriver.exe_, версия - 106)
- перейти в файл src/test/java/TestRunner.java
- в _tags_ указать нужный тег (@ui - UI-тест)
- запустить команду _mvn clean test_ (или нажать зеленый треугольник напротив _public class TestRunner {_)
- чтобы посмотреть отчет - перейти в _Plugins -> allure -> allure:serve_
