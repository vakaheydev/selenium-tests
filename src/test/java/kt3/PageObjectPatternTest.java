package kt3;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import practice.pagepattern.Currency;
import practice.pagepattern.MainPage;
import practice.pagepattern.ProductPage;
import practice.pagepattern.RegistrationPage;

import java.util.UUID;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PageObjectPatternTest {
    private static final WebDriver driver = new ChromeDriver();
    private static final String URL = "http://192.168.0.107:8081";
    private static String registeredEmail;
    private static String registeredPassword;

    @BeforeEach
    public void setup() {
        driver.get(URL);
        webSleep(500);
    }

    @AfterAll
    public static void destroy() {
        driver.close();
    }

    @DisplayName("1) Should click on product")
    @Test
    public void testShouldClickOnProduct() {
        MainPage mainPage = new MainPage(driver, URL);
        ProductPage productPage = mainPage.clickOnPosterProduct();
        productPage.clickOnRightArrow();
        productPage.clickOnRightArrow();
    }

    @DisplayName("2) Should change currency")
    @Test
    public void testShouldChangeCurrency() {
        MainPage mainPage = new MainPage(driver, URL);
        mainPage.changeCurrencyTo(Currency.EURO);
        Currency currentCurrency = mainPage.getCurrency();

        assertEquals(Currency.EURO, currentCurrency);
    }

    @DisplayName("3) Should select categories")
    @Test
    public void testShouldSelectCategories() {
        MainPage mainPage = new MainPage(driver, URL);
        String text = mainPage.selectCategory("Desktops", "PC");

        assertEquals("There are no products to list in this category.", text);
    }

    @DisplayName("4) Should register account")
    @Test
    @Order(Integer.MIN_VALUE)
    public void testShouldRegisterAccount() throws InterruptedException {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        String randomEmail = "john_smith_" + UUID.randomUUID() + "@gmail.com";
        String randomPassword = UUID.randomUUID().toString().substring(0, 8);
        String confirmationText = registrationPage.register("John", "Smith", randomEmail, randomPassword);

        assertEquals(confirmationText, "Your Account Has Been Created!");
        registeredEmail = randomEmail;
        registeredPassword = randomPassword;

        System.out.println(String.format("Registered account [email:%s, password:%s]", registeredEmail, registeredPassword));
    }

    @DisplayName("5) Should search")
    @Test
    public void testShouldSearch() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, URL);
        int foundSize = mainPage.search("iPhone").size();

        assertEquals(1, foundSize);
    }

    @DisplayName("6) Should add to with list")
    @Test
    public void testShouldAddToWishList() throws InterruptedException {
        if (registeredEmail == null) {
            testShouldRegisterAccount();
        }

        MainPage mainPage = new MainPage(driver, URL);

        ProductPage productPage = mainPage.clickOnPosterProduct();
        productPage.addToWishList();

        assertEquals(1, mainPage.getWishListSize());
    }

    @DisplayName("7) Should add camera to cart")
    @Test
    public void testShouldAddCameraToCart() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, URL);

        WebElement cameraElement = mainPage.search("Canon").getFirst();
        ProductPage productPage = new ProductPage(driver, cameraElement);
        productPage.clickOnProduct();
        productPage.addToCart();

        assertEquals(1, mainPage.getShoppingCartSize());
    }

    @DisplayName("8) Should add tablet to cart")
    @Test
    public void testShouldAddTabletToCart() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, URL);

        WebElement cameraElement = mainPage.search("Samsung Galaxy Tab 10.1").getFirst();
        ProductPage productPage = new ProductPage(driver, cameraElement);
        productPage.clickOnProduct();
        productPage.addToCart();

        assertEquals(1, mainPage.getShoppingCartSize());
    }

    @DisplayName("9) Should add phone HTC to cart")
    @Test
    public void testShouldAddPhoneToCart() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, URL);

        WebElement cameraElement = mainPage.search("HTC").getFirst();
        ProductPage productPage = new ProductPage(driver, cameraElement);
        productPage.clickOnProduct();
        productPage.addToCart();

        assertEquals(2, mainPage.getShoppingCartSize());
    }

    @DisplayName("10) Should write review")
    @Test
    public void testShouldWriteReview() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, URL);
        ProductPage productPage = mainPage.clickOnPosterProduct();
        productPage.writeReview("testName", "Amazing!!11!");
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
