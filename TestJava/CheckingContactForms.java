package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CheckingContactForms {
    private WebDriver driver;

    @BeforeMethod
    public void SetUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\distr\\psychologVaM\\" +
                "TestingPsychologVaM_2\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.psycholog-vam.ru/");
        driver.manage().window().maximize();

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void checkingCFMailElement() throws InterruptedException {
        WebElement wb_Text8 = driver.findElement(By.cssSelector("#wb_Text8"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", wb_Text8);
        WebElement mailelement =  driver.findElement(By.cssSelector("#formListView li:first-child p"));
        assertEquals("vlada.magnich@yandex.ru", mailelement.getText(),
                "Значение в поле Mail не соответствует ожиданию!");
        System.out.println("Tests for Mail : completed successfully");
    }

    @Test
    public void checkingCFCallMe() throws InterruptedException {
        WebElement wb_Text8 = driver.findElement(By.cssSelector("#wb_Text8"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", wb_Text8);
        WebElement callMe = driver.findElement(By.cssSelector("#formListView li:nth-child(2) p"));
        assertEquals("+79274660406", callMe.getText(),
                "Значение в поле 'Позвони мне' не соответствует ожиданию!");
        System.out.println("Tests for Позвони мне: completed successfully");
    }

    @Test
    public void checkingCFLocation() throws InterruptedException {
        WebElement wb_Text8 = driver.findElement(By.cssSelector("#wb_Text8"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", wb_Text8);
        WebElement location = driver.findElement(By.cssSelector("#formListView li:nth-child(3) p"));
        assertEquals("Город Казань", location.getText(),
                "Значение в поле 'Расположение' не соответствует ожиданию!");
        System.out.println("Tests for location: completed successfully");
    }
}
