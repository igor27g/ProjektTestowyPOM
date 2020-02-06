package PageObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
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

//    @FindBys({                                 // to dziala jak taki lancuch
//           @FindBy(tagName = "form") ,         // wyszukaj mi elementow o tagu form
//           @FindBy(className = "shop_table")  // i w tych elementach, wyszukaj pod nimi, ktorymi klasa jest shop_table. To musi byc dziecko
//                                              // to moze byc duzo glebiej niz na poziomie dziecko, po prostu co kolwiek tam jest
//                                             // to jest rownoznacznie z form .shop_table (szukanie potomka)
//    })
//    @FindAll({
//            @FindBy(tagName = "form"),         //FindAll znajduje wszystkie elementy, ktore spelniaja jeden z podanych warunkow
//            @FindBy(className = "shop_table")  // Zwroci wszystkie elementy, ktore maja tag form i maja klase shop_table
//    })

    //@FindBy(how = How.CSS, using = "form>.shop_table"); //drugi sposob szukania selektorow
    @FindBy(css = "form>.shop_table") @CacheLookup private List<WebElement> shopTables;
    @FindBy(css= "div.quantity>input") @CacheLookup private WebElement productQuantityField;
    @FindBy(css = ".cart_item") @CacheLookup private List<WebElement> cartItems;
    @FindBy(css = "[name='update_cart']") @CacheLookup private WebElement updateCartButton;
    private By loaderLocator = By.cssSelector(".blockOverlay");
    @FindBy(css = ".checkout-button") @CacheLookup private WebElement checkoutButton;

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
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loaderLocator));
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