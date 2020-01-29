package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 7);
    }

    private By shopTableLocator = By.cssSelector(".shop_table");
    private By productQuantityLocator = By.cssSelector("div.quantity>input");
    private String removeProductButtonCssSelector = "a[data-product_id='<product_id>']";

    public int getProductsAmount(String productId) {

        wait.until(ExpectedConditions.presenceOfElementLocated(shopTableLocator));
        By removeProductLocator = By.cssSelector(this.removeProductButtonCssSelector.replace("<product_id>", productId));
        return driver.findElements(removeProductLocator).size();
    }

    public int getProductQuanity() {
        String quantityString = driver.findElement(productQuantityLocator).getAttribute("value");
        return Integer.parseInt(quantityString);
    }

}
