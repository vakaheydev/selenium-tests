package practice.pagepattern;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

import static java.time.Duration.ofMillis;

public class ProductPage {
    private final WebElement product;

    private final WebDriver driver;

    public ProductPage(WebDriver driver, WebElement product) {
        checkProduct(product);

        this.driver = driver;
        this.product = product;
    }

    public void clickOnProduct() {
        try {
            product.click();
        } catch (ElementClickInterceptedException ignored) {
        }
    }

    public void clickOnRightArrow() {
        clickOnProduct();
        driver.findElement(By.className("mfp-arrow-right")).click();
    }

    public void addToWishList() {
        WebElement wishListBtn = driver.findElement(By.xpath("//button[@formaction[contains(., 'wishlist')]]"));
        wishListBtn.click();
    }

    public void addToCart() {
        WebElement addToCartBtn = driver.findElement(By.xpath("//button[text()='Add to Cart']"));
        List<WebElement> elements = driver.findElements(By.xpath("//div[@class='col-sm']"));
        String productInfo = elements.get(1).getText();
        int rightIndex = productInfo.indexOf("\n");
        String productName = productInfo.substring(0, rightIndex);
        System.out.println("Adding to cart: " + productName);

        if (productName.equals("Canon EOS 5D")) {
            WebElement selectOptionsElement = driver.findElement(By.id("input-option-226"));
            Select select = new Select(selectOptionsElement);
            select.selectByValue("15");
        }

        addToCartBtn.click();
    }

    public void writeReview(String name, String review) {
        WebElement writeReviewBtn = driver.findElement(By.xpath("//a[text()='Write a review']"));
        writeReviewBtn.click();


        WebElement inputName = driver.findElement(By.id("input-name"));
        inputName.sendKeys(Keys.TAB);
        inputName.sendKeys(name);

        WebElement inputReview = driver.findElement(By.id("input-text"));
        inputReview.sendKeys(Keys.TAB, Keys.TAB);
        inputReview.sendKeys(review);

        int randomValue = new Random().nextInt(1, 6);
        WebElement rate = driver.findElement(By.xpath(String.format("//input[@value='%d']", randomValue)));

        Actions actions = new Actions(driver);
        actions.moveToElement(rate).click().perform();

        WebElement continueBtn = driver.findElement(By.xpath("//button[text()='Continue']"));
        actions.moveToElement(continueBtn).click().perform();
    }

    private void checkProduct(WebElement product) {
        if (product == null) {
            throw new IllegalArgumentException("Product can't be null");
        }
    }

    private void webSleep(int millis) {
        driver.manage().timeouts().implicitlyWait(ofMillis(millis));
    }
}