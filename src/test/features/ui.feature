#language: ru

@all
@ui
Функционал: UI

  Сценарий: заполнение формы
    Дано тестовые данные
      | first_name           | generate.name     |
      | last_name            | generate.lastname |
      | email                | generate.email    |
      | gender               | Male              |
      | phone                | generate.phone    |
      | date_of_birth        | 24 Sep 2022       |
      | date_of_birth_result | 24 September,2022 |
      | subjects             | Maths             |
      | hobbies              | 1                 |
      | hobbies_result       | Sports            |
      | picture              | 1.png             |
      | address              | generate.address  |
      | state                | NCR               |
      | city                 | Delhi             |

    Когда ввести в "поле "Имя"" значение "${first_name}"
    И ввести в "поле "Фамилия"" значение "${last_name}"
    И ввести в "поле "Email"" значение "${email}"
    И выбрать в "радиобаттон "Пол"" значение "${gender}"
    И ввести в "поле "Телефон"" значение "${phone}"
    И ввести в "поле "Дата рождения"" значение "${date_of_birth}"
    И ввести в "поле "Subjects"" значение "${subjects}"
    И выбрать в "чекбокс "Hobbies"" значение "${hobbies}"
    И загрузить в "ввод "Загрузить картинку"" файл "${picture}"
    И ввести в "поле "Адрес"" значение "${address}"
    И ввести в "комбобокс "Штат"" значение "${state}"
    И ввести в "комбобокс "Город"" значение "${city}"
    И нажать на "кнопку "Submit""

    Тогда появилось "поле "Thanks for submitting the form""
    И проверить содержимое таблицы "таблица "Результат""
      | Student Name   | ${first_name} ${last_name} |
      | Student Email  | ${email}                   |
      | Gender         | ${gender}                  |
      | Mobile         | ${phone}                   |
      | Date of Birth  | ${date_of_birth_result}    |
      | Subjects       | ${subjects}                |
      | Hobbies        | ${hobbies_result}          |
      | Picture        | ${picture}                 |
      | Address        | ${address}                 |
      | State and City | ${state} ${city}           |