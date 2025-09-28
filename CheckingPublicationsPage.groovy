
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import spock.lang.Specification

import java.time.Duration
import java.util.logging.ConsoleHandler
import java.util.logging.Level
import java.util.logging.Logger
import java.util.logging.SimpleFormatter

import static org.hamcrest.MatcherAssert.assertThat

class CheckingPublicationsPage extends Specification {

    private final Logger logger = Logger.getLogger(CheckingPublicationsPage.class.name)
    WebDriver driver

    void setup(){
        // Настройка логгера вручную для вывода в консоль
        ConsoleHandler consoleHandler = new ConsoleHandler(); // Экземпляр обработчика для консоли
        consoleHandler.setLevel(Level.ALL);                   // Уровень записи сообщений
        consoleHandler.setFormatter(new SimpleFormatter());   // Простой формат отображения
        logger.addHandler(consoleHandler);                    // Регистрация обработчика
        logger.setUseParentHandlers(false);                   // Отключаем родительские обработчики
        logger.setLevel(Level.ALL);                           // Общий уровень логирования
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Users\\User\\distr\\psychologVaM\\TestingPsychologVaM_2\\" +
                        "drivers\\chromedriver.exe")
        driver = new ChromeDriver()
    }

    void cleanup(){
        driver.quit()
    }

    def "Проверка значения title на странице"(){
        given:
        String url = "https://psycholog-vam.ru/AllPublications.html"
        String expectedTitle = "Все публикации"

        when:
        driver.get(url)


        then:
        String actualTitle = driver.title
        if(!expectedTitle.equals(actualTitle)) {
            throw new AssertionError("Ожидалось: '" + expectedTitle + "', фактически получено: '" + actualTitle + "'");}
    }

    def "Проверка перехода по пукту 'Главная' основного меню" (){
        given:
        String url = "https://psycholog-vam.ru/AllPublications.html"
        String expectedTitle = "psycholog-vam"

        when:
        driver.get(url)

        then:
        WebElement elementMain = driver.findElement(By.cssSelector("#menu-list li:first-child>a"))
        elementMain.click()
        String actualTitle = driver.getTitle()
        if(!expectedTitle.equals(actualTitle)) {
            throw new AssertionError("Ожидалось: '" + expectedTitle + "', фактически получено: '" + actualTitle + "'");}
    }

    def "Проверка перехода по пукту 'Обо мне' основного меню"(){
        given:
        String url = "https://psycholog-vam.ru/AllPublications.html"
        String expectedTitle = "About me"

        when:
        driver.get(url)

        then:
        WebElement elementAbout = driver.findElement(By.cssSelector("#menu-list li:nth-child(2)>a"))
        elementAbout.click()
        String actualTitle = driver.getTitle()
        if (!expectedTitle.equals(actualTitle)){
            throw new AssertionError("Ожидалось: '" + expectedTitle + "', фактически получено: '" + actualTitle + "'");}
    }

    def "Проверка перехода по пукту 'Образование' основного меню"(){
        given:
        String url = "https://psycholog-vam.ru/AllPublications.html"
        String expectedTitle = "psycholog-vam"
        String expectedText = "Контакты"

        when:
        driver.get(url)

        then:
        WebElement elementContacts = driver.findElement(By.cssSelector("#menu-list li:nth-child(5)>a"))
        elementContacts.click()
        WebElement elementContactsBlock = driver.findElement(By.cssSelector("#wb_Text8 span"))
        String actualText = elementContactsBlock.getText()
        String actualTitle = driver.getTitle()
        if(!expectedTitle.equals(actualTitle)){
            throw new AssertionError("Ожидалось: '" + expectedTitle + "', фактически получено: '" + actualTitle + "'");
        }
        if (!expectedText.equals(actualText)){
            throw new AssertionError("Ожидалось: '" + expectedText + "', фактически получено: '" + actualText + "'");
        }
    }

    def "Проверка НЕразвернутого состояния выпадающего списка Публикаций без клика по '#DropList1-button'"(){
        given:
        String url = "https://psycholog-vam.ru/AllPublications.html"

        when:
        driver.get(url)

        then:
        List<WebElement> elements = driver.findElements(By.cssSelector("#DropList1-menu .ui-menu-item"));
        if (elements.size() > 0) {
            throw new AssertionError("Элемент '#DropList1-menu .ui-menu-item' ОТОБРАЖАЕТСЯ на странице!");
        } else {
            logger.info("Элемент '#DropList1-menu .ui-menu-item' НЕ отображается на странице!");
        }
    }

    def "Отображение списка Публикаций при вызове его"() {
        given:
        String url = "https://psycholog-vam.ru/AllPublications.html"

        when:
        driver.get(url)

        then:

            def elementList = null
            try {
                elementList = driver.findElement(By.cssSelector("#wb_DropList1"))
            } catch (NoSuchElementException e) {
                logger.error("Ошибка: Основной элемент '#w_DropList1' не найден на странице! ${e.message}")

                throw new AssertionError("Основной элемент '#w_DropList1' не найден на странице.", e)
            }
            elementList.click()
            def elementOpenList = null
            try {
                elementOpenList = driver.findElement(By.cssSelector("#DropList1-menu .ui-menu-item"))
            } catch (NoSuchElementException e) {
                logger.error("Ошибка: Меню '.ui-menu-item' не найдено на странице! ${e.message}")
                throw new AssertionError("Меню '.ui-menu-item' не найдено на странице.", e)
            }
            boolean isVisible = elementOpenList.isDisplayed()
            assertThat("Элемент НЕ отображается на странице!", isVisible)
    }

    def "переход по любой ссылке из выпадающкего списка" () {
        given:
        String url = "https://psycholog-vam.ru/AllPublications.html"

        when:
        driver.get(url)

        then:
        WebElement elementList = driver.findElement(By.cssSelector("#wb_DropList1"))
        elementList.click()
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10))
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#DropList1-menu")))
        List<WebElement> elements = driver.findElements(By.cssSelector("#DropList1-menu > li"))
        if (elements.size() >= 2){
            int indexToSelect = new Random().nextInt(elements.size()-1)+1;
            WebElement selectedElement = elements.get(indexToSelect)
            String textValue = ((JavascriptExecutor)driver).executeScript("return arguments[0].innerText.trim();",
                    selectedElement)?.toString();
            selectedElement.click()
            WebElement elementTitle = wait.until({ d ->
                def titleEl = d.findElement(By.cssSelector("#Article1 .blogsubject"))
                def currentText = titleEl.getText()
                while(currentText.startsWith("LOADING...")) {
                    Thread.sleep(500)
                    currentText = titleEl.getText()
                }
                return titleEl // Возвращаем сам элемент
            })
            String actualTitle = elementTitle.getText()
            if (!actualTitle.toLowerCase().equals(textValue.toLowerCase())){
                throw new AssertionError("Ожидалось: '" + textValue + "', фактически получено: '" + actualTitle + "'");}
        }else {
            System.out.println("Статьи не отображаются в списке!");
        }
    }
}
