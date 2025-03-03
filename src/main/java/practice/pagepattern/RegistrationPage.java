package practice.pagepattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static java.lang.Thread.sleep;
import static java.time.Duration.ofMillis;

public class RegistrationPage {
    private final WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public String register(String firstName, String lastName, String email, String password) {
        var accountLink = driver.findElement(By.xpath("//span[text()[contains(., 'My Account')]]"));
        accountLink.click();
        webSleep(500);

        var registerBtn = driver.findElement(By.xpath("//a[text()[contains(., 'Register')]]"));
        registerBtn.click();
        webSleep(500);

        var fn = driver.findElement(By.id("input-firstname"));
        fn.sendKeys(firstName);
        String firstNameText = fn.getDomProperty("value");

        var ln = driver.findElement(By.id("input-lastname"));
        ln.sendKeys(lastName);

        var em = driver.findElement(By.id("input-email"));
        em.sendKeys(email);

        var pass = driver.findElement(By.id("input-password"));
        pass.sendKeys(password);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 250)");
        try {
            sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        js.executeScript("window.scrollBy(0, 250)");
        try {
            sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        var policyInput = driver.findElement(By.xpath("//input[@name='agree']"));
        policyInput.click();

        var continueBtn = driver.findElement(By.xpath("//button[text()[contains(., 'Continue')]]"));
        continueBtn.click();

        webSleep(500);

        var confirmationHeader = driver.findElement(
                By.xpath("//h1[text()[contains(., 'Your Account Has Been Created!')]]"));
        return confirmationHeader.getText();
    }

    private void webSleep(int millis) {
        driver.manage().timeouts().implicitlyWait(ofMillis(millis));
    }
}
