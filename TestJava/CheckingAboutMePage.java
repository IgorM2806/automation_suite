package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class CheckingAboutMePage {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",  "C:\\Users\\User\\distr\\psychologVaM\\TestingPsychologVaM_2\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://psycholog-vam.ru/SpecialistProfile.html");
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void CheckTitleMePage() {
        String title = driver.getTitle();
        boolean isPassed = title.equals("About me");
        if (!isPassed) {
            System.err.println("Error occurred during CheckTitleMePage()");
        }
        System.out.println("Test result for checkTitleMePage(): " + (isPassed ? "PASSED" : "FAILED"));
        Assert.assertEquals(title, "About me");
    }

    @Test
    public void CheckingHeader() {
        String header = driver.findElement(By.id("Heading1")).getText();
        boolean isPassed = header.equals("Влада Магнич – психолог, коуч, карьерный консультант и профориентолог");
        if (!isPassed) {
            System.err.println("Error occurred during CheckingHeader()");
        }
        System.out.println("Test result for CheckingHeader(): " + (isPassed ? "PASSED" : "FAILED"));
        assertEquals("Влада Магнич – психолог, коуч, карьерный консультант и профориентолог", header);
    }
    @Test
    public void CheckingMainPoint() {
        WebElement mainPoint = driver.findElement(By.cssSelector("#menu-list li:first-child a"));
        mainPoint.click();
        String title = driver.getTitle();
        boolean isPassed = title.equals("psycholog-vam");
        if (!isPassed) {
            System.err.println("Error occurred during CheckingMainPoint()");
        }
        System.out.println("Test result for CheckingMainPoint(): " + (isPassed ? "PASSED" : "FAILED"));
        assertEquals(title, "psycholog-vam");
    }
    @Test
    public void CheckingAboutMePoint() {
        WebElement aboutMe = driver.findElement(By.cssSelector("#menu-list li:nth-child(2) a"));
        aboutMe.click();
        String title = driver.getTitle();
        boolean isPassed = title.equals("About me");
        if (!isPassed) {
            System.err.println("Error occurred during CheckingAboutMePoint()");
        }
        System.out.println("Test result for CheckingAboutMePoint(): " + (isPassed ? "PASSED" : "FAILED"));
        assertEquals(title, "About me");
    }
    @Test
    public void CheckingEducationPoint() {
        WebElement education = driver.findElement(By.cssSelector("#menu-list li:nth-child(3) a"));
        education.click();
        String title = driver.getTitle();
        boolean isPassed = title.equals("Education");
        if (!isPassed) {
            System.err.println("Error occurred during CheckingEducationPoint()");
        }
        System.out.println("Test result for CheckingEducationPoint(): " + (isPassed ? "PASSED" : "FAILED"));
        assertEquals(title, "Education");
    }

    @Test
    public void CheckingAllPublicationsPoint() {
        WebElement allPublications =  driver.findElement(By.cssSelector("#menu-list li:nth-child(4) a"));
        allPublications.click();
        String title = driver.getTitle();
        boolean isPassed = title.equals("Все публикации");
        if (!isPassed) {
            System.err.println("Error occurred during CheckingAllPublicationsPoint()");
        }
        System.out.println("Test result for CheckingAllPublicationsPoint(): " + (isPassed ? "PASSED" : "FAILED"));
        assertEquals(title, "Все публикации");
    }
    @Test
    public void CheckingContactsPoint() {
        WebElement contacts =  driver.findElement(By.cssSelector("#menu-list li:nth-child(5) a"));
        contacts.click();
        String title = driver.getTitle();
        boolean isPassed = title.equals("psycholog-vam");
        if (!isPassed) {
            System.err.println("Error occurred during title");
        }
        System.out.println("Test result for title: " + (isPassed ? "PASSED" : "FAILED"));
        assertEquals("Значение title не соответсвует ожиданию!", title, "psycholog-vam");
        WebElement wb_Text8 =  driver.findElement(By.cssSelector("#wb_Text8 span"));
        boolean isVisible = wb_Text8.isDisplayed();
        if (!isVisible) {
            System.err.println("Error occurred during wb_Text8 - Is NOT visible");
        }
        System.out.println("Видимость элемента (#wb_Text8 span): " + isVisible);
        assertTrue(isVisible);
    }


}
