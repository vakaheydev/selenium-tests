package kt10;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.bidi.module.Input;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.crypto.KeyAgreementSpi;

import java.io.IOException;
import java.net.SocketException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;

public class SeleniumTest {
    private static final WebDriver driver = new ChromeDriver();
    private static final String URL = "https://the-internet.herokuapp.com";

    @BeforeEach
    public void setup() {
        driver.get(URL);
        webSleep(500);
    }

    @AfterAll
    public static void destroy() {
        driver.close();
    }

    @DisplayName("A/B Testing")
    @Test
    public void shouldTestAAndB() {
        driver.get(URL + "/abtest");
        String expectedText = "Also known as split testing. This is a way in which businesses are able to simultaneously test and learn different versions of a page to see which text and/or functionality works best towards a desired outcome (e.g. a user action such as a click-through).";
        String actualText = driver.findElement(By.xpath("//p"))
                .getText();

        assertEquals(expectedText, actualText);
    }

    @DisplayName("Add/Remove Elements")
    @Test
    public void shouldAndAndRemoveElements() {
        driver.get(URL + "/add_remove_elements/");
        try {
            driver.findElement(By.xpath("//button[text() = 'Delete']"));
        } catch (NoSuchElementException ignored) {

        }

        driver.findElement(By.xpath("//button[text() = 'Add Element']")).click();

        driver.findElement(By.xpath("//button[text() = 'Delete']")).click();

        try {
            driver.findElement(By.xpath("//button[text() = 'Delete']"));
        } catch (NoSuchElementException ignored) {

        }
    }

    @DisplayName("Basic Auth")
    @Test
    public void shouldPerformBasicAuth() {
        driver.get("https://admin:admin@the-internet.herokuapp.com" + "/basic_auth");
        String actualText = byXpath("//p").getText();
        assertEquals("Congratulations! You must have the proper credentials.", actualText);
    }

    @DisplayName("Broken Images")
    @Test
    public void shouldHaveBrokenImages() {
        driver.get(URL + "/broken_images");
        int iBrokenImageCount = 0;
        List<WebElement> image_list = driver.findElements(By.tagName("img"));
        try {
            for (WebElement img : image_list)
            {
                if (img != null)
                {
                    HttpClient client = HttpClient.newBuilder().build();
                    HttpRequest httpRequest = HttpRequest.newBuilder()
                            .uri(URI.create(img.getAttribute("src")))
                            .GET()
                            .build();
                    HttpResponse<?> response = null;

                    try {
                        response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if (response.statusCode() != 200)
                    {
                        System.out.println(img.getAttribute("outerHTML") + " is broken.");
                        iBrokenImageCount++;
                    }
                }
            }
        } catch (Exception ignored) {
        }


        assertEquals(2, iBrokenImageCount);
    }

    @DisplayName("Checkboxes")
    @Test
    public void shouldManageWithCheckboxes() {
        driver.get(URL + "/checkboxes");
        var checkbox1 = byXpath("//input[position() = 1]");
        var checkbox2 = byXpath("//input[position() = 2]");

        assertTrue(checkbox2.isSelected());

        checkbox1.click();
        assertTrue(checkbox1.isSelected());

        checkbox2.click();
        assertFalse(checkbox2.isSelected());
    }

    @DisplayName("Context Menu")
    @Test
    public void shouldTriggerContextMenu() {
        driver.get(URL + "/context_menu");
        var hotSpot = driver.findElement(By.id("hot-spot"));
        Actions actions = new Actions(driver);
        actions.contextClick(hotSpot).perform();

        webSleep(500);

        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();

        assertEquals("You selected a context menu", alertText);
    }

    @DisplayName("Digest Authentication")
    @Test
    public void shouldPerformDigestAuthentication() {
        driver.get("https://admin:admin@the-internet.herokuapp.com" + "/digest_auth");
        String actualText = byXpath("//p").getText();
        assertEquals("Congratulations! You must have the proper credentials.", actualText);
    }

    @DisplayName("Drag and Drop")
    @Test
    public void shouldPerformDragAndDrop() {
        driver.get(URL + "/drag_and_drop");

        String firstColText = driver.findElement(By.id("column-a")).getText();
        String secondColText = driver.findElement(By.id("column-b")).getText();

        assertEquals("A", firstColText);
        assertEquals("B", secondColText);

        WebElement colA = driver.findElement(By.id("column-a"));
        WebElement colB = driver.findElement(By.id("column-b"));

        Actions actions = new Actions(driver);
        actions.dragAndDrop(colA, colB).perform();

        firstColText = driver.findElement(By.id("column-a")).getText();
        secondColText = driver.findElement(By.id("column-b")).getText();

        assertEquals("B", firstColText);
        assertEquals("A", secondColText);
    }

    @DisplayName("Dropdown")
    @Test
    public void shouldPerformDropdown() {
        driver.get(URL + "/dropdown");
        Select select = new Select(byXpath("//select[@id='dropdown']"));
        select.selectByValue("2");
        assertTrue(byXpath("//option[@value='2']").isSelected());
    }

    @DisplayName("Inputs")
    @Test
    public void shouldPerformInput() {
        driver.get(URL + "/inputs");
        WebElement input = byXpath("//input");
        input.sendKeys("125");

        assertEquals("125", input.getDomProperty("value"));
    }

    private static void webSleep(int millis) {
        driver.manage().timeouts().implicitlyWait(ofMillis(millis));
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {

        }
    }

    private WebElement byXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }
}
