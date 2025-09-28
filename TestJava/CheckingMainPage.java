package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class CheckingMainPage {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\distr\\psychologVaM\\" +
                    "TestingPsychologVaM_2\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://psycholog-vam.ru");
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void checkTitle() throws InterruptedException {
        String title = driver.getTitle();
        assert title.contains("psycholog-vam");
        System.out.println("Tests for logoSearch(): completed successfully!");
    }

    @Test
    public void logoSearch() throws InterruptedException {
        WebElement logo = driver.findElement(By.id("Picture2"));
        String actualSrcValue = logo.getAttribute("src");
        assertEquals("Атрибут src не соответсвует ожидаемому значению!", actualSrcValue,
                "https://psycholog-vam.ru/images/DSC_6602_smol.JPG");
        System.out.println("Tests for logoSearch(): completed successfully!");
    }
    @Test
    public void checkingMenuMainPageFirstPointMenu()  throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement firstPointMenu = driver.findElement(By.cssSelector("#menu-list > li:first-child > a"));
        String actualHrefValue = firstPointMenu.getAttribute("href");
        assertEquals("Значение атрибута href отличается от ожидаемого!",
                actualHrefValue, "https://psycholog-vam.ru/#");
        System.out.println("Tests for checkingMenuMainPageFirstPointMenu(): completed successfully!");
    }

    @Test
    public void checkingMenuMainPageSecondElementMenu() throws InterruptedException {
        WebElement secondElementMenu = driver.findElement(By.cssSelector("#menu-list > li:nth-child(2) > a"));
        secondElementMenu.click();
        String title = driver.getTitle();
        assertEquals("При переходе значение title открытой страницы не соответсвует ожиданию!",
                title, "About me");
        System.out.println("Tests for checkingMenuMainPageSecondElementMenu(): completed successfully!");
    }

    @Test
    public void checkingMenuMainPageThirdElementMenu() throws InterruptedException {
        WebElement thirdElementMenu =  driver.findElement(By.cssSelector("#menu-list > li:nth-child(3) > a"));
        thirdElementMenu.click();
        String title2 = driver.getTitle();
        assertEquals("При переходе значение title открытой страницы не соответсвует ожиданию!", title2,
                "Education");
        System.out.println("Tests for checkingMenuMainPageThirdElementMenu(): completed successfully!");
    }

    @Test
    public void checkingMenuMainPageFourthElementMenu() throws InterruptedException {
        WebElement fourthElementMenu = driver.findElement(By.cssSelector("#menu-list > li:nth-child(4) > a"));
        fourthElementMenu.click();
        String title3 = driver.getTitle();
        assertEquals("При переходе значение title открытой страницы не соответсвует ожиданию!", title3,
                "Все публикации");
        System.out.println("Tests for checkingMenuMainPageFourthElementMenu(): completed successfully!");
    }

    @Test
    public void chekFifthElementMenu() throws  InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement fifthElementMenu = driver.findElement(By.cssSelector("#menu-list > li:nth-child(5) > a"));
        fifthElementMenu.click();
        WebElement wbtext8 = driver.findElement(By.cssSelector("#wb_Text8 span"));
        boolean isVisible = wbtext8.isDisplayed();
        assertTrue("Элемент Контакты не отображается на экране!", isVisible);
        System.out.println("Tests for chekFifthElementMenu(): completed successfully!");
    }

    @Test
    public void chekIntroButtonElement() throws InterruptedException {
        WebElement introButton = driver.findElement(By.cssSelector("#introButton"));
        introButton.click();
        String title = driver.getTitle();
        assertEquals("Значение title открытой страницы не соответствует ожиданию!", title, "About me");
        System.out.println("Tests for chekIntroButtonElement(): completed successfully!");
    }

    @Test
    public void chekWhyButtonElement() throws InterruptedException {
        WebElement whyButton = driver.findElement(By.cssSelector("#whyButton"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",
                whyButton);
        whyButton.click();
        String title = driver.getTitle();
        assertEquals("Значение title открытой страницы не соответствует ожиданию!", title, "Раздел 3");
        System.out.println("Tests for chekWhyButtonElement(): completed successfully!");

    }

    @Test
    public void chekButton2Element() throws InterruptedException {
        WebElement button2 = driver.findElement(By.cssSelector("#Button2"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",
                button2);
        button2.click();
        String title = driver.getTitle();
        assertEquals("Значение title открытой страницы не соответствует ожиданию!", title, "ККП");
        System.out.println("Tests for chekButton2Element(): completed successfully!");
    }

    @Test
    public void chekHowButtonElement() throws InterruptedException {
        WebElement howButton = driver.findElement(By.cssSelector("#howButton"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",
                howButton);
        howButton.click();
        String title = driver.getTitle();
        assertEquals("Значение title открытой страницы не соответствует ожиданию!", title, "Coaching");
        System.out.println("Tests for chekHowButtonElement(): completed successfully!");
    }

    @Test
    public void checkingPublicationSection() throws InterruptedException{
        WebElement publication = driver.findElement(By.cssSelector("#testimonialsHeading"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",
                publication);
        boolean isVisible =  publication.isDisplayed();
        assertTrue("Элемент Публикации не отображается на экране!", isVisible);
        System.out.println("Tests for checkingPublicationSection(): completed successfully!");
    }

    @Test
    public void checkingPublicationBlockDatePublished()  throws InterruptedException{
        WebElement publication = driver.findElement(By.cssSelector("#Article1"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",
                publication);
        WebElement blogSubjectSpan = driver.findElement(By.cssSelector("#Article1 .blogsubject"));
        String fullBlogSubjectText =  blogSubjectSpan.getText();
        String datePublished = fullBlogSubjectText.split("-")[0].trim();
        assertNotEquals("Loading date... - ", datePublished, "Не выполнена загрузка данных date!");
        System.out.println("Tests for checkingPublicationBlockDatePublished(): completed successfully!");

    }

    @Test
    public void checkingPublicationBlockBlogTextSpan() throws InterruptedException{
        WebElement publication = driver.findElement(By.cssSelector("#Article1"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",
                publication);
        WebElement blogTextSpan = driver.findElement(By.cssSelector("#Article1 .blogtext span"));
        String mainText = blogTextSpan.getText();
        assertNotEquals("Loading content...", blogTextSpan, "Не выполнена загрузка данных content!");
        System.out.println("Tests for checkingPublicationBlockBlogTextSpan(): completed successfully!");
    }

    @Test
    public void checkingPublicationBlockArticleLink() throws InterruptedException{
        WebElement publication = driver.findElement(By.cssSelector("#Article1"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",
                publication);
        WebElement articleLink = driver.findElement(By.cssSelector("#Article1 .blogsubject a"));
        String articleTitle = articleLink.getText();
        assertNotEquals("Loading title...", articleTitle, "Не выполнена загрузка данных title!");
    }

    @Test
    public void checkRequestConsultation()  throws InterruptedException {
        WebElement requestConsultation = driver.findElement(By.cssSelector("#LayoutGrid3 .col-2"));
        WebElement privacyConsentElement = driver.findElement(By.cssSelector("#privacyConsent"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",
                requestConsultation);
        boolean isVisible = requestConsultation.isDisplayed();
        assertTrue("Элемент checkRequestConsultation не отображается!", isVisible );
        WebElement formButton = driver.findElement(By.cssSelector("#formButton"));
        privacyConsentElement.click();
        formButton.click();
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        assertNotNull(alertText, "Alert не отображается на экране!");
        System.out.println("Tests for checkRequestConsultation(): completed successfully!");
    }

    @Test
    public void checkingEmptyConsentCheckbox()  throws InterruptedException {
        WebElement requestConsultation = driver.findElement(By.cssSelector("#LayoutGrid3 .col-2"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",
                requestConsultation);
        WebElement formButton = driver.findElement(By.cssSelector("#formButton"));
        formButton.click();
        String alertText = driver.switchTo().alert().getText();
        assertEquals("Отсутствует Аллерт - 'Необходимо согласиться с условиями конфиденциальности.'", alertText,
                "Необходимо согласиться с условиями конфиденциальности.");
        System.out.println("Tests for checkingEmptyConsentCheckbox(): completed successfully!");
    }

    @Test
    public void checkButton1Element() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement button1 = driver.findElement(By.cssSelector("#Button1"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",
                button1);
        button1.click();
        WebElement logoElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#RollOver1 a")));
        boolean logoIsVisible = logoElement.isDisplayed();
        assertTrue("Скролл до header не выполнен!", logoIsVisible);
        System.out.println("Tests for checkButton1Element(): completed successfully");
    }
}
