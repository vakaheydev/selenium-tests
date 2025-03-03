package practice;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.plaf.TableHeaderUI;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.time.Duration.ofMillis;

public class AlertWindowPractice {
    private static WebDriver driver = new ChromeDriver();
    private static String URL = "https://demoqa.com/alerts";

    public static void main(String[] args)  {
        try {
//            testAlerts();
            testWindows();
        } finally {
            driver.close();
        }
    }

    private static void testAlerts()  {
        driver.get(URL);
        List<WebElement> btns = driver.findElements(By.xpath("//button[text()='Click me']"));

        var btn1 = btns.get(0);
        var btn2 = btns.get(1);
        var btn3 = btns.get(2);
        var btn4 = btns.get(3);

        btn1.click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        webSleep(500);

        btn2.click();
        try {
            Thread.sleep(5500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Alert alert2 = driver.switchTo().alert();
        alert2.accept();

        btn3.click();
        Alert alert3 = driver.switchTo().alert();
        alert3.accept();
        webSleep(500);

        btn4.sendKeys(Keys.SPACE);
        Alert alert4 = driver.switchTo().alert();
        alert4.sendKeys("Test");
        alert4.accept();
        webSleep(1500);
    }

    private static void testWindows() {
        driver.get("https://demoqa.com/browser-windows");
        List<WebElement> btns = driver.findElements(By.xpath("//button[text()[contains(., 'New')]]"));
        btns.get(0).click();
        Object[] windowHandles = driver.getWindowHandles().toArray();
        System.out.println(Arrays.toString(windowHandles));
        webSleep(500);
        driver.switchTo().window((String) windowHandles[0]);

        webSleep(500);
        driver.switchTo().window((String) windowHandles[1]);
        webSleep(500);

        btns.get(1).click();
        windowHandles = driver.getWindowHandles().toArray();
        System.out.println(Arrays.toString(windowHandles));
        driver.switchTo().window((String) windowHandles[0]);

        driver.switchTo().window((String) windowHandles[1]);
    }

    private static void webSleep(int millis) {
        driver.manage().timeouts().implicitlyWait(ofMillis(millis));
    }
}
