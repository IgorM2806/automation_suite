# automation_suite

## Автотесты на java:
- **CheckingAboutMePage.java**
- **CheckingConsultationRequestForms.java**
- **CheckingContactForms.java**
- **CheckingEducationPage.java**
- **CheckingMainPage.java**
Представленные выше тесты написаны на java с использованием фреймворков TestNG и Selenium WebDriver.
Тесты предназначены для проверки работоспособности страниц сайта "psycholog-vam.ru".

## Основные методы, использованиые в тестовых классах:
    - setup() Метод выполняется перед каждым тестоым методом (Запуск браузера Chrome, открытие целевой страницы сайта и максимизация окна браузера);
    - tearDoun() Метод закрывает браузер после каждого тестового метода;
    - тестовые методы, выполняющие проверки.

## Особенности реализации:
    - Использованы аннотации TestNG (@BeforeMethod, @AfterMethod, @Test);
    - Для взаимодействия с элементами интерфейса использована библиотека Selenium WebDriver;
    - для логирования использованы стандартные потоки вывода System.out и System.err;
    - использованы утверждение Assert для подтверждения ожидаемого результата.


