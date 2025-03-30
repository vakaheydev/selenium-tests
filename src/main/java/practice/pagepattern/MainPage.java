package practice.pagepattern;

import org.openqa.selenium.*;

import java.util.List;

import static java.lang.Thread.sleep;
import static java.time.Duration.ofMillis;

public class MainPage {
    private WebElement posterProduct;
    private final WebDriver driver;
    private final String URL;

    public MainPage(WebDriver driver, String url) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null");
        }

        if (url == null) {
            throw new IllegalArgumentException("URL cannot be null");
        }

        this.driver = driver;
        this.URL = url;
        driver.get(url);
    }

    public ProductPage clickOnPosterProduct() {
        posterProduct =  driver.findElement(By.id("carousel-banner-0"));
        posterProduct.click();
        var product = driver.findElement(By.className("magnific-popup"));
        return new ProductPage(driver, product);
    }

    public void changeCurrencyTo(Currency currency) {
        WebElement dropDown = driver.findElement(By.className("dropdown-toggle"));
        dropDown.click();
        webSleep(100);

        WebElement currencyElement = driver.findElement(By.xpath(String.format("//a[@href='%s']", currency.getHref())));
        currencyElement.click();

        webSleep(100);

        dropDown = driver.findElement(By.className("dropdown-toggle"));
        dropDown.click();
    }

    public Currency getCurrency() {
        WebElement dropDown = driver.findElement(By.className("dropdown-toggle"));
        String currency = dropDown.findElement(By.xpath("//strong")).getText();
        return Currency.ofSymbol(currency);
    }

    public String selectCategory(String category, String subCategory) {
        var categoryElement = driver.findElement(By.xpath(String.format("//a[text()[contains(., '%s')]]", category)));
        categoryElement.click();
        webSleep(100);

        var subCategoryElement = driver.findElement(By.xpath(String.format("//a[text()[contains(., '%s')]]", subCategory)));
        subCategoryElement.click();
        webSleep(100);

        return driver.findElement(
                By.xpath("//p[text()[contains(., 'There are no products to list in this category.')]]"))
                .getText();

    }

    public List<WebElement> search(String productName) {
        var searchInput = driver.findElement(By.xpath("//input[@name='search']"));
        searchInput.sendKeys(productName);
        searchInput.sendKeys(Keys.ENTER);

        webSleep(150);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 250)");
        webSleep(150);
        js.executeScript("window.scrollBy(0, 250)");
        webSleep(150);

        return driver.findElements(By.xpath(String.format("//a[text()[contains(., '%s')]]", productName)));
    }

    public int getShoppingCartSize() {
//        home();
        String text = driver.findElement(By.xpath("//button[text()[contains(., 'item(s) -')]]")).getText();
        int leftIndex = text.indexOf(" ");

        return Integer.parseInt(text.substring(0, leftIndex));
    }

    public void login(String email, String password) {
        WebElement dropDown = driver.findElements(By.className("dropdown-toggle")).get(1);
        dropDown.click();
        webSleep(600);

        WebElement loginBtn = driver.findElement(By.xpath("//a[text()='Login']"));
        loginBtn.click();
        webSleep(600);

        var emailInput = driver.findElement(By.id("input-email"));
        emailInput.sendKeys(email);

        var passwordInput = driver.findElement(By.id("input-password"));
        passwordInput.sendKeys(password);

        var btnSubmit = driver.findElement(By.xpath("//button[text()='Login']"));
        btnSubmit.click();

        webSleep(1000);

        home();
    }

    public int getWishListSize() {
        home();
        String wishListText = driver.findElement(By.xpath("//span[text()[contains(., 'Wish List')]]")).getText();
        int leftIndex = wishListText.indexOf("(");
        return Integer.parseInt(wishListText.substring(leftIndex + 1, wishListText.length() - 1));
    }

    public void home() {
        driver.get(URL);
    }

    private void webSleep(int millis) {
        driver.manage().timeouts().implicitlyWait(ofMillis(millis));
    }
}
