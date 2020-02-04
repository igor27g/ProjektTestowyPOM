package PageObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartPage extends BasePage{

    public DemoFooterPage footer;
    private WebDriverWait wait;

    public CartPage(WebDriver driver) {
        super(driver);
        footer = new DemoFooterPage(driver);
        wait = new WebDriverWait(driver,7);
    }

    //@FindBy(how = How.CSS, using = "form>.shop_table"); //drugi sposob
    @FindBy(css = "form>.shop_table") private WebElement shopTable;
    @FindBy(css = "form>.shop_table") private List<WebElement> shopTables;
    @FindBy(css= "div.quantity>input") private WebElement productQuantityField;
    @FindBy(css = ".cart_item") private List<WebElement> cartItems;
    @FindBy(css = "[name='update_cart']") private WebElement updateCartButton;
    private By loaderLocator = By.cssSelector(".blockOverlay");
    @FindBy(css = ".checkout-button") private WebElement checkoutButton;

    private String removeProductButtonCssSelector = "a[data-product_id='<product_id>']";


    public int getProductQuantity() {
        waitForShopTable();
        String quantityString = productQuantityField.getAttribute("value");
        return Integer.parseInt(quantityString);
    }

    public boolean isProductInCart(String productId) {
        waitForShopTable();
        By removeProductLocator = By.cssSelector(removeProductButtonCssSelector.replace("<product_id>", productId));
        int productRecords = driver.findElements(removeProductLocator).size();
        boolean presenceOfProduct = false;
        if(productRecords ==1) {
            presenceOfProduct = true;
        } else if (productRecords > 1) {
            throw new IllegalArgumentException("There si more than one record for the product in cart.");
        }
        return presenceOfProduct;
    }

    public int getNumberOfProducts() {
        waitForShopTable();
        return cartItems.size();

    }

    public CartPage changeQuantity(int quantity) {
        productQuantityField.clear();
        productQuantityField.sendKeys(Integer.toString(quantity));
        return this;
    }

    public CartPage updateCart() {
        wait.until(ExpectedConditions.elementToBeClickable(updateCartButton));
        updateCartButton.click();
        return this;
    }

    public CartPage removeProduct(String productId) {
        By removeProductLocator = By.cssSelector(removeProductButtonCssSelector.replace("<product_id>", productId));
        driver.findElement(removeProductLocator).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loaderLocator));
        driver.findElements(By.cssSelector("p.cart-empty")).size();
        return this;
    }

    public CheckoutPage goToCheckout() {
        checkoutButton.click();
        return new CheckoutPage(driver);
    }

    private void waitForShopTable() {
        WebDriverWait wait = new WebDriverWait(driver, 7);
        wait.until(ExpectedConditions.visibilityOf(shopTable));
    }

    public boolean isCartEmpty() {
        int shopTableElements = shopTables.size();
        if(shopTableElements == 1) {
            return false;
        } else if (shopTableElements == 0) {
            return true;
        } else {
            throw  new IllegalArgumentException("Wrong number of shop table elements: there can be only one or none");
        }
    }
}