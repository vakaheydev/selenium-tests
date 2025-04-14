package kt7;

import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import practice.pagepattern.MainPage;
import practice.pagepattern.kt5.AdminPage;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import static java.time.Duration.ofMillis;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SelenoidTest {
    private static RemoteWebDriver driver;
    private static final String URL = "http://172.26.205.104:8081";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    @BeforeAll
    public static void init() {
        ChromeOptions options = new ChromeOptions();

        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
            /* How to add test badge */
            put("name", "Test badge...");

            /* How to set session timeout */
            put("sessionTimeout", "15m");

            /* How to set timezone */
            put("env", new ArrayList<String>() {{
                add("TZ=UTC");
            }});

            /* How to add "trash" button */
            put("labels", new HashMap<String, Object>() {{
                put("manual", "true");
            }});

            /* How to enable video recording */
            put("enableVideo", true);
            put("enableVNC", true);
        }});

        options.addArguments("window-size=1920,1080");

        options.setBrowserVersion("128.0");

        try {
            driver = new RemoteWebDriver(URI.create("http://localhost:4444/wd/hub").toURL(), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() {
        driver.get(URL);
        webSleep(500);
    }

    @AfterAll
    public static void destroy() {
        driver.close();
    }

    @DisplayName("Create category Devices")
    @Test
    @Order(1)
    public void shouldCreateCategoryDevices() {
        AdminPage adminPage = new AdminPage(driver, URL);
        adminPage.open(USERNAME, PASSWORD);
        sleep(500);
        adminPage.createCatalog("Devices");
    }

    @DisplayName("Create items")
    @Test
    @Order(2)
    public void shouldCreateItems() {
        AdminPage adminPage = new AdminPage(driver, URL);
        adminPage.open(USERNAME, PASSWORD);
        sleep(500);
        adminPage.createItem("Devices", "Mouse-1");
        adminPage.open(USERNAME, PASSWORD);
        sleep(500);
        adminPage.createItem("Devices", "Mouse-2");
        adminPage.open(USERNAME, PASSWORD);
        sleep(500);
        adminPage.createItem("Devices", "Keyboard-1");
        adminPage.open(USERNAME, PASSWORD);
        adminPage.createItem("Devices", "Keyboard-2");
    }

    @DisplayName("Check created items")
    @Test
    @Order(3)
    public void shouldHaveCreatedItems() {
        MainPage mainPage = new MainPage(driver, URL);
        var mouse1 = mainPage.search("Mouse-1").getFirst();
        mainPage.home();
        sleep(500);
        var mouse2 = mainPage.search("Mouse-2").getFirst();
        mainPage.home();
        sleep(500);
        var keyboard1 = mainPage.search("Keyboard-1").getFirst();
        mainPage.home();
        sleep(500);
        var keyboard2 = mainPage.search("Keyboard-2").getFirst();

        Assertions.assertNotNull(mouse1);
        Assertions.assertNotNull(mouse2);
        Assertions.assertNotNull(keyboard1);
        Assertions.assertNotNull(keyboard2);
    }

    @DisplayName("Delete items")
    @Test
    @Order(4)
    public void shouldDeleteItems() {
        AdminPage adminPage = new AdminPage(driver, URL);
        adminPage.open(USERNAME, PASSWORD);
        sleep(500);
        adminPage.deleteItem("Mouse-1");
        sleep(500);
        adminPage.open(USERNAME, PASSWORD);
        sleep(500);
        adminPage.deleteItem("Keyboard-1");
    }

    @DisplayName("Check deleted itmes")
    @Test
    @Order(5)
    public void shouldNotHaveDeletedItems() {
        MainPage mainPage = new MainPage(driver, URL);

        try {
            var mouse1 = mainPage.search("Mouse-1").getFirst();
        } catch (java.util.NoSuchElementException ex) {
            System.out.println("Mouse was deleted correctly");
        }

        mainPage.home();


        try {
            var mouse1 = mainPage.search("Keyboard-1").getFirst();
        } catch (java.util.NoSuchElementException ex) {
            System.out.println("Keyboard was deleted correctly");
        }

        mainPage.home();

        var mouse2 = mainPage.search("Mouse-2").getFirst();
        mainPage.home();
        var keyboard2 = mainPage.search("Keyboard-2").getFirst();
        mainPage.home();

        Assertions.assertNotNull(mouse2);
        Assertions.assertNotNull(keyboard2);
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
}
