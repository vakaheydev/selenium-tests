package practice.pagepattern.kt5;

import org.openqa.selenium.*;

import static java.time.Duration.ofMillis;

public class AdminPage {
    private final WebDriver driver;
    private final String url;

    public AdminPage(WebDriver driver, String url) {
        this.driver = driver;
        this.url = url;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void open(String username, String password) {
        driver.get(url + "/administration");
        authorize(username, password);
    }

    public void authorize(String username, String password) {
        var elUsername = driver.findElement(By.id("input-username"));
        elUsername.sendKeys(username);

        var elPassword = driver.findElement(By.id("input-password"));
        elPassword.sendKeys(password);

        var loginBtn = driver.findElement(By.xpath("//button[text()[contains(., 'Login')]]"));
        loginBtn.click();
    }

    public void createCatalog(String name) {
        var catalogBtn = byXpath("//a[text()[contains(., 'Catalog')]]");
        catalogBtn.click();

        var categoriesBtn = byXpath("//a[text()[contains(., 'Categories')]]");
        categoriesBtn.click();

        var plusBtn = byXpath("//a[@title='Add New']");
        plusBtn.click();

        webSleep(500);

        var categoryName = byId("input-name-1");
        categoryName.sendKeys(name);

        var metaTag = byId("input-meta-title-1");
        metaTag.sendKeys(name);

        webSleep(500);

        var ceoBtn = byXpath("//a[text()='SEO']");
        ceoBtn.click();

        var seoInput = byId("input-keyword-0-1");
        seoInput.sendKeys(name);

        var saveBtn = byXpath("//button[@title='Save']");
        saveBtn.click();
    }

    public void createItem(String categoryName, String itemName) {
        var catalogBtn = byXpath("//a[text()[contains(., 'Catalog')]]");
        catalogBtn.click();

        var productsBtn = byXpath("//a[text()[contains(., 'Products')]]");
        productsBtn.click();

        var plusBtn = byXpath("//a[@title='Add New']");
        plusBtn.click();

        webSleep(500);

        var productName = byId("input-name-1");
        productName.sendKeys(itemName);

        var metaTag = byId("input-meta-title-1");
        metaTag.sendKeys(itemName);

        var dataBtn = byXpath("//a[text()='Data']");
        dataBtn.click();

        var model = byId("input-model");
        model.sendKeys(itemName);

        var linkBtn = byXpath("//a[text()='Links']");
        linkBtn.click();

        var categoryInput = byId("input-category");
        categoryInput.sendKeys(categoryName);

        categoryInput.sendKeys(Keys.SPACE, Keys.ENTER);

        webSleep(500);

        var ceoBtn = byXpath("//a[text()='SEO']");
        ceoBtn.click();

        var seoInput = byId("input-keyword-0-1");
        seoInput.sendKeys(itemName);

        var saveBtn = byXpath("//button[@title='Save']");
        saveBtn.click();
    }

    public void deleteItem(String itemName) {
        var catalogBtn = byXpath("//a[text()[contains(., 'Catalog')]]");
        catalogBtn.click();

        var productsBtn = byXpath("//a[text()[contains(., 'Products')]]");
        productsBtn.click();

        byId("input-name").sendKeys(itemName);
        byXpath("//button[text()[contains(., 'Filter')]]").click();
        webSleep(500);
        byXpath("//input[@type='checkbox']").click();
        byXpath("//button[@title='Delete']").click();

        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public WebElement byId(String id) {
        return driver.findElement(By.id(id));
    }

    public WebElement byXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    private void webSleep(int millis) {
        driver.manage().timeouts().implicitlyWait(ofMillis(millis));
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {

        }
    }
}
