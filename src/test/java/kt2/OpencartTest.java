package kt2;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.UUID;

import static java.lang.Thread.sleep;
import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpencartTest {
    private static final WebDriver driver = new ChromeDriver();
    private static final String URL = "http://localhost:8081";

    @BeforeEach
    public void setup() {
        driver.get(URL);
        webSleep(500);
    }

    @AfterAll
    public static void destroy() {
        driver.close();
    }

    @DisplayName("1) Should click on product Positive")
    @Test
    public void testShouldClickOnProduct() {
        var mainProduct = driver.findElement(By.id("carousel-banner-0"));
        mainProduct.click();
        webSleep(1500);

        var bigScreen = driver.findElement(By.className("magnific-popup"));
        bigScreen.click();
        webSleep(100);

        var rightArrow = driver.findElement(By.className("mfp-arrow-right"));
        rightArrow.click();
        webSleep(100);

        rightArrow = driver.findElement(By.className("mfp-arrow-right"));
        rightArrow.click();
        webSleep(100);

        rightArrow = driver.findElement(By.className("mfp-arrow-right"));
        rightArrow.click();
        webSleep(100);
    }

    @DisplayName("1) Should click on product Negative")
    @Test
    public void testShouldClickOnProductNegative() {
        var mainProduct = driver.findElement(By.id("carousel-banner-0"));
        mainProduct.click();
        webSleep(500);

        try {
            driver.findElement(By.className("testClass"));
        } catch (NoSuchElementException ex) {
            System.out.println(ex);
        }
    }

    @DisplayName("2) Change currency Positive")
    @Test
    public void testShouldChangeCurrency() {
        WebElement dropDown = driver.findElement(By.className("dropdown-toggle"));
        String currency = dropDown.findElement(By.xpath("//strong")).getText();
        assertEquals(currency, "$");
        dropDown.click();
        webSleep(100);

        WebElement euro = driver.findElement(By.xpath("//a[@href='EUR']"));
        euro.click();
        webSleep(100);

        dropDown = driver.findElement(By.className("dropdown-toggle"));
        currency = dropDown.findElement(By.xpath("//strong")).getText();
        assertEquals(currency, "€");
    }

    @DisplayName("2) Change currency Negative")
    @Test
    public void testShouldChangeCurrencyNegative() {
        WebElement dropDown = driver.findElement(By.className("dropdown-toggle"));
        String currency = dropDown.findElement(By.xpath("//strong")).getText();
        assertEquals(currency, "$");
        dropDown.click();
        webSleep(100);

        try {
            driver.findElement(By.xpath("//a[@href='RUB']"));
        } catch (NoSuchElementException ex) {
            System.out.println(ex);
        }
    }

    @DisplayName("3) Select categories Positive")
    @Test
    public void testShouldSelectCategories() {
        var desktopLink = driver.findElement(By.xpath("//a[text()[contains(., 'Desktops')]]"));
        desktopLink.click();
        webSleep(100);

        var pcLink = driver.findElement(By.xpath("//a[text()[contains(., 'PC (0)')]]"));
        pcLink.click();
        webSleep(100);

        String text = driver.findElement(
                By.xpath("//p[text()[contains(., 'There are no products to list in this category.')]]")).getText();

        assertEquals("There are no products to list in this category.", text);

    }

    @DisplayName("3) Select categories Negative")
    @Test
    public void testShouldSelectCategoriesNegative() {
        var desktopLink = driver.findElement(By.xpath("//a[text()[contains(., 'Desktops')]]"));
        desktopLink.click();
        webSleep(100);

        try {
            driver.findElement(By.xpath("//test"));
        } catch (NoSuchElementException ex) {
            System.out.println(ex);
        }
    }

    @DisplayName("4) Register account Positive")
    @Test
    public void testClickOnMenu() throws InterruptedException {
        var accountLink = driver.findElement(By.xpath("//span[text()[contains(., 'My Account')]]"));
        accountLink.click();
        webSleep(500);

        var registerBtn = driver.findElement(By.xpath("//a[text()[contains(., 'Register')]]"));
        registerBtn.click();
        webSleep(1500);

        var firstName = driver.findElement(By.id("input-firstname"));
        firstName.sendKeys("Firstname");
        String firstNameText = firstName.getDomProperty("value");
        assertEquals(firstNameText, "Firstname");

        var lastName = driver.findElement(By.id("input-lastname"));
        lastName.sendKeys("Lastname");

        UUID uuid = UUID.randomUUID();
        var email = driver.findElement(By.id("input-email"));
        email.sendKeys("test" + uuid + "@gmail.com");

        var password = driver.findElement(By.id("input-password"));
        password.sendKeys("123456789");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 250)");
        sleep(500);
        js.executeScript("window.scrollBy(0, 250)");
        sleep(500);

        var policyInput = driver.findElement(By.xpath("//input[@name='agree']"));
        policyInput.click();

        var continueBtn = driver.findElement(By.xpath("//button[text()[contains(., 'Continue')]]"));
        continueBtn.click();

        webSleep(1500);

        var confirmationHeader = driver.findElement(
                By.xpath("//h1[text()[contains(., 'Your Account Has Been Created!')]]"));
        String confirmationText = confirmationHeader.getText();

        assertEquals("Your Account Has Been Created!", confirmationText);
    }

    @DisplayName("4) Register account Negative")
    @Test
    public void testClickOnMenuNegative() {
        var accountLink = driver.findElement(By.xpath("//span[text()[contains(., 'My Account')]]"));
        accountLink.click();
        webSleep(500);

        var registerBtn = driver.findElement(By.xpath("//a[text()[contains(., 'Register')]]"));
        registerBtn.click();
        webSleep(1500);

        var firstName = driver.findElement(By.id("input-firstname"));
        firstName.sendKeys("Firstname");
        String firstNameText = firstName.getDomProperty("value");
        assertEquals(firstNameText, "Firstname");

        var lastName = driver.findElement(By.id("input-lastname"));
        lastName.sendKeys("Lastname");

        UUID uuid = UUID.randomUUID();
        var email = driver.findElement(By.id("input-email"));
        email.sendKeys("test" + uuid + "@gmail.com");

        var password = driver.findElement(By.id("input-password"));
        password.sendKeys("123456789");

        var policyInput = driver.findElement(By.xpath("//input[@name='agree']"));

        try {
            policyInput.click();
        } catch (ElementClickInterceptedException ex) {
            System.out.println(ex);
        }
    }

    @DisplayName("5) Search Positive")
    @Test
    public void testShouldSearch() throws InterruptedException {
        var searchInput = driver.findElement(By.xpath("//input[@name='search']"));
        searchInput.sendKeys("iPhone");
        searchInput.sendKeys(Keys.ENTER);

        sleep(150);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 250)");
        sleep(150);
        js.executeScript("window.scrollBy(0, 250)");
        sleep(150);

        var iphoneLink = driver.findElement(By.xpath("//a[text()[contains(., 'iPhone')]]"));
        sleep(1500);
    }

    @DisplayName("5) Search Negative")
    @Test
    public void testShouldSearchNegative() throws InterruptedException {
        String tooLongText = "a".repeat(15000);
        var searchInput = driver.findElement(By.xpath("//input[@name='search']"));
        searchInput.sendKeys(tooLongText);
        sleep(1000);
        searchInput.sendKeys(Keys.ENTER);

        sleep(5000);

        var requestTooLongHeader = driver.findElement(By.xpath("//h1"));
        String headerText = requestTooLongHeader.getText();

        assertEquals("Request-URI Too Long", headerText);
    }

    private static void webSleep(int millis) {
        driver.manage().timeouts().implicitlyWait(ofMillis(millis));
    }
}
