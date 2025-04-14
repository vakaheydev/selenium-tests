package kt5;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import practice.pagepattern.MainPage;
import practice.pagepattern.kt5.AdminPage;

import static java.time.Duration.ofMillis;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminPanelTest {
    private static final WebDriver driver = new ChromeDriver();
    private static final String URL = "http://172.26.205.104:8081";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

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
        adminPage.createCatalog("Devices");
    }

    @DisplayName("Create items")
    @Test
    @Order(2)
    public void shouldCreateItems() {
        AdminPage adminPage = new AdminPage(driver, URL);
        adminPage.open(USERNAME, PASSWORD);
        adminPage.createItem("Devices", "Mouse-1");
        adminPage.open(USERNAME, PASSWORD);
        adminPage.createItem("Devices", "Mouse-2");
        adminPage.open(USERNAME, PASSWORD);
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
        var mouse2 = mainPage.search("Mouse-2").getFirst();
        mainPage.home();
        var keyboard1 = mainPage.search("Keyboard-1").getFirst();
        mainPage.home();
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
        adminPage.deleteItem("Mouse-1");
        webSleep(500);
        adminPage.open(USERNAME, PASSWORD);
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
