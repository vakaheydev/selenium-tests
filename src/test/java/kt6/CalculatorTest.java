package kt6;

import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CalculatorTest {
    private static AndroidDriver driver;

    @BeforeAll
    public static void init() {
        driver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:deviceName", "emulator-5554");
        capabilities.setCapability("appium:automationName", "uiautomator2");
        capabilities.setCapability("appium:app", "C:\\Users\\Vaka\\util\\selenium\\calculator-7-8-271241277.apk");
        try {
            driver = new AndroidDriver(URI.create("http://127.0.0.1:4723").toURL(), capabilities);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Should open")
    @Test
    @Order(Integer.MIN_VALUE)
    public void testShouldOpen() {
        WebElement element = driver.findElement(By.id("com.google.android.calculator:id/digit_1"));
        Assertions.assertNotNull(element);
    }

    @DisplayName("Addition")
    @Test
    public void testShouldAdd() {
        driver.findElement(By.id("com.google.android.calculator:id/digit_1")).click();
        driver.findElement(By.id("com.google.android.calculator:id/op_add")).click();
        driver.findElement(By.id("com.google.android.calculator:id/digit_3")).click();
        driver.findElement(By.id("com.google.android.calculator:id/eq")).click();

        String result = driver.findElement(By.id("com.google.android.calculator:id/result_final")).getText();

        Assertions.assertEquals("4", result);
    }

    @DisplayName("Subtraction")
    @Test
    public void testShouldSubtract() {
        driver.findElement(By.id("com.google.android.calculator:id/digit_1")).click();
        driver.findElement(By.id("com.google.android.calculator:id/op_sub")).click();
        driver.findElement(By.id("com.google.android.calculator:id/digit_3")).click();
        driver.findElement(By.id("com.google.android.calculator:id/eq")).click();

        String result = driver.findElement(By.id("com.google.android.calculator:id/result_final")).getText();

        Assertions.assertEquals("−2", result);
    }

    @DisplayName("Multiplication")
    @Test
    public void testShouldMultiply() {
        driver.findElement(By.id("com.google.android.calculator:id/digit_1")).click();
        driver.findElement(By.id("com.google.android.calculator:id/op_mul")).click();
        driver.findElement(By.id("com.google.android.calculator:id/digit_3")).click();
        driver.findElement(By.id("com.google.android.calculator:id/eq")).click();

        String result = driver.findElement(By.id("com.google.android.calculator:id/result_final")).getText();

        Assertions.assertEquals("3", result);
    }

    @DisplayName("Division")
    @Test
    public void testShouldDivide() {
        driver.findElement(By.id("com.google.android.calculator:id/digit_6")).click();
        driver.findElement(By.id("com.google.android.calculator:id/op_div")).click();
        driver.findElement(By.id("com.google.android.calculator:id/digit_2")).click();
        driver.findElement(By.id("com.google.android.calculator:id/eq")).click();

        String result = driver.findElement(By.id("com.google.android.calculator:id/result_final")).getText();

        Assertions.assertEquals("3", result);
    }
}
