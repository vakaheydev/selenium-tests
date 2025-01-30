package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;

public class SeleniumTest {
    private static final WebDriver driver = new ChromeDriver();
    private static final String URL = "https://konflic.github.io/examples/";

    @BeforeEach
    public void setup() {
        driver.get(URL);
        webSleep(500);
    }

    @AfterAll
    public static void destroy() {
        driver.close();
    }

    @DisplayName("1) Open modal button")
    @Test
    public void testShouldClickOpenModalBtn() {
        WebElement openModalBtn = driver.findElement(By.id("myBtn"));
        openModalBtn.click();
        webSleep(100);

        WebElement spanClose = driver.findElement(By.className("close"));
        spanClose.click();
        webSleep(100);
    }

    @DisplayName("2) Insert text into first input")
    @Test
    public void testShouldTypeIntoFirstInput() {
        WebElement input = driver.findElement(By.id("inp"));
        String text = input.getDomProperty("value");
        assertTrue(text.isEmpty());

        String inputText = "Test text";
        input.sendKeys(inputText);
        webSleep(100);

        input = driver.findElement(By.id("inp"));
        assertEquals(inputText, input.getDomProperty("value"));
    }

    @DisplayName("3) Insert text into second input")
    @Test
    public void testShouldTypeIntoSecondInput() {
        WebElement input = driver.findElement(By.id("inp_form"));
        String text = input.getDomProperty("value");
        assertEquals("This is value", text);

        input.clear();

        String inputText = "Test text";
        input.sendKeys(inputText);
        webSleep(100);

        input = driver.findElement(By.id("inp_form"));
        assertEquals(inputText, input.getDomProperty("value"));
    }

    @DisplayName("4) Click on menu")
    @Test
    public void testClickOnMenu() throws InterruptedException {
        WebElement menuElement = driver.findElement(By.className("dropdown-toggle"));
        menuElement.click();
        webSleep(100);

        List<WebElement> menuBtns = driver.findElements(By.className("dropdown-item"));
        WebElement firstBtn = menuBtns.get(0);
        firstBtn.click();
        webSleep(100);
    }

    @DisplayName("5) Click on menu, then select inputs")
    @Test
    public void testClickOnMenuSelectInputs() throws InterruptedException {
        WebElement menuElement = driver.findElement(By.className("dropdown-toggle"));
        menuElement.click();
        webSleep(100);

        List<WebElement> menuBtns = driver.findElements(By.className("dropdown-item"));
        WebElement btn = menuBtns.get(2);
        btn.click();
        webSleep(100);

        WebElement haveMoneyCheckbox = driver.findElement(By.id("scales"));

        List<WebElement> otherCheckBoxes = driver.findElements(By.id("horns"));
        WebElement noMoneyCheckbox = otherCheckBoxes.get(0);
        WebElement bitcoinBox = otherCheckBoxes.get(1);

        assertTrue(haveMoneyCheckbox.isSelected());
        haveMoneyCheckbox.click();
        assertFalse(haveMoneyCheckbox.isSelected());

        noMoneyCheckbox.click();
        assertTrue(noMoneyCheckbox.isSelected());

        bitcoinBox.click();
        assertTrue(bitcoinBox.isSelected());

        WebElement maleRadioBtn = driver.findElement(By.id("male"));
        WebElement femaleRadioBtn = driver.findElement(By.id("female"));
        WebElement otherRadioBtn = driver.findElement(By.id("other"));

        maleRadioBtn.click();
        assertTrue(maleRadioBtn.isSelected());

        femaleRadioBtn.click();
        assertTrue(femaleRadioBtn.isSelected());

        otherRadioBtn.click();
        assertTrue(otherRadioBtn.isSelected());
    }

    private static void webSleep(int millis) {
        driver.manage().timeouts().implicitlyWait(ofMillis(millis));
    }
}
