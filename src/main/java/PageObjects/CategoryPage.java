package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CategoryPage {
    private WebDriver driver;
    private WebDriverWait wait;
    public CategoryPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver,7);
    }

    private By viewCartButtonLocator = By.cssSelector(".added_to_cart");
    private String addToCartButtonCssSelector = ".post-<product_id>>.add_to_cart_button";

    public CategoryPage goTo(String url) {
        driver.navigate().to(url);
        return new CategoryPage(driver);
    }

    public CategoryPage addToCart(String productId) {
        By addToCartButton = By.cssSelector(addToCartButtonCssSelector.replace("<product_id>", productId));
        driver.findElement(addToCartButton).click();
        wait.until(ExpectedConditions.attributeContains(addToCartButton,"class","added"));
        return new CategoryPage(driver);
    }

    public CartPage viewCart() {
        wait.until(ExpectedConditions.elementToBeClickable(viewCartButtonLocator)).click();
        return new CartPage(driver);
    }

}
