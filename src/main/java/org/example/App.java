package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Comparator;
import java.util.List;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;

public class App {
    private static final WebDriver driver = new ChromeDriver();
    private static final String URL = "https://konflic.github.io/examples/";

    public static void main(String[] args) {
        driver.get(URL);
        sleep(1000);

        WebElement openModalBtn = driver.findElement(By.id("myBtn"));
        openModalBtn.click();
        sleep(100);

        WebElement spanClose = driver.findElement(By.className("close"));
        spanClose.click();
        sleep(100);
    }

    // 1 - open modal
    // 2 and 3 - text boxes
    // 4 - menu and select items

    private static void sleep(int millis) {
        driver.manage().timeouts().implicitlyWait(ofMillis(2));
    }
}
