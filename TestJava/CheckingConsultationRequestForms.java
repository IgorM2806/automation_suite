package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class CheckingConsultationRequestForms {
    private WebDriver driver;

    @BeforeMethod
    public void SeTup(){
        System.setProperty("webdriver.chrome.driver",  "C:\\Users\\User\\distr\\psychologVaM\\" +
                "TestingPsychologVaM_2\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://psycholog-vam.ru/");
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void TearDown(){
        driver.quit();
    }

    @Test
    public void CheckingConsultationForms() throws InterruptedException {
        WebElement wb_Text6Element = driver.findElement(By.cssSelector("#wb_Text6 span"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", wb_Text6Element);
        assert  wb_Text6Element.isDisplayed();
        System.out.println("Tests for CheckingConsultationForms(): completed successfully!");
    }

    @Test
    public void CheckingConsultationFormLabel() throws InterruptedException {
        WebElement wb_Text6Element = driver.findElement(By.cssSelector("#wb_Text6 span"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", wb_Text6Element);
        WebElement consultationFormLabel =  driver.findElement(By.cssSelector("#consultationForm label"));
        assertEquals("Значение в поле 'Имя' не соответствует ожиданию!", consultationFormLabel.getText(),
                "Имя");
        System.out.println("Tests for CheckingConsultationFormLabel(): " +
                "completed successfully!");
    }

    @Test
    public void CheckingConsultationForNameValue() throws InterruptedException {
        WebElement wb_Text6Element = driver.findElement(By.cssSelector("#wb_Text6 span"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", wb_Text6Element);
        WebElement formNameElement = driver.findElement(By.cssSelector("#formName"));
        String forNameValue = formNameElement.getAttribute("placeholder");
        assertEquals("Значение поля 'placeholder' для поля ввода 'Имя' не соответствует ожиданию!",
                forNameValue, "Введите своё имя");
        System.out.println("Tests for CheckingConsultationForNameValue(): " +
                "completed successfully!");
    }

    @Test
    public void CheckingConsultationFormLabel2Element() throws InterruptedException {
        WebElement wb_Text6Element = driver.findElement(By.cssSelector("#wb_Text6 span"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", wb_Text6Element);
        WebElement formLabel2Element = driver.findElement(By.cssSelector("#formLabel2"));
        assertEquals("Значение в поле 'Email' не соответствует ожиданию!", formLabel2Element.getText(),
                "Email");
        System.out.println("Tests for CheckingConsultationFormLabel2Element (): " +
                "completed successfully!");
    }

    @Test
    public void CheckingConsultationFormLabel3Element() throws InterruptedException {
        WebElement wb_Text6Element = driver.findElement(By.cssSelector("#wb_Text6 span"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", wb_Text6Element);
        WebElement formEmailElement = driver.findElement(By.cssSelector("#formEmail"));
        String forEmailValue = formEmailElement.getAttribute("placeholder");
        assertEquals("Значение поля 'placeholder' для поля ввода 'Email' не соответствует ожиданию!",
                forEmailValue, "Введите адрес электронной почты");
        System.out.println("Tests for CheckingConsultationFormLabel3Element (): " +
                "completed successfully!");

    }

    @Test
    public void CheckingConsultationFormLabel4Element() throws InterruptedException {
        WebElement wb_Text6Element = driver.findElement(By.cssSelector("#wb_Text6 span"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", wb_Text6Element);
        WebElement formLabel4Element = driver.findElement(By.cssSelector("#formLabel4"));
        assertEquals("Название поля 'formLabel4' не соответствует ожиданию!", formLabel4Element.getText(),
                "Сообщение");
        System.out.println("Tests for CheckingConsultationFormLabel4Element (): " +
                "completed successfully!");
    }
    @Test
    public void CheckingConsultationFormMessageElement() throws InterruptedException {
        WebElement wb_Text6Element = driver.findElement(By.cssSelector("#wb_Text6 span"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", wb_Text6Element);
        WebElement formMessageElement = driver.findElement(By.cssSelector("#formMessage"));
        String forMessageValue = formMessageElement.getAttribute("placeholder");
        assertEquals("Значение поля 'placeholder' для поля ввода 'Сообщение' не соответствует ожиданию!",
                forMessageValue, "Введите сообщение - не более 255 символов");
        System.out.println("Tests for CheckingConsultationFormMessageElement(): completed successfully!");
    }

}
