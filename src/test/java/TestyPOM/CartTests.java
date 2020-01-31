package TestyPOM;
import PageObjects.CategoryPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class CartTests extends BaseTest {
    String productId = "386";
    String productUrl = "https://fakestore.testelka.pl/product/egipt-el-gouna/";
    String categoryURL = "https://fakestore.testelka.pl/product-category/windsurfing/";
    String[] productPages = {"/egipt-el-gouna/","/wspinaczka-via-ferraty/","/wspinaczka-island-peak/",
            "/fuerteventura-sotavento/", "/grecja-limnos/", "/windsurfing-w-karpathos/",
            "/wyspy-zielonego-przyladka-sal/", "/wakacje-z-yoga-w-kraju-kwitnacej-wisni/",
            "/wczasy-relaksacyjne-z-yoga-w-toskanii/", "/yoga-i-pilates-w-hiszpanii/"};

    @Test
    public void addToCartFromProductPageTest() {
        ProductPage productPage = new ProductPage(driver).goTo(productUrl);
        productPage.demoNotice.close();
        boolean isProductInCart = productPage.addToCart().viewCart().isProductInCart(productId);
        assertTrue(isProductInCart,
                "Remove button was not found for a product with id=" + productId + ". " +
                        "Was the product added to cart?");
    }

    @Test
    public void addToCartFromCategoryPageTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(categoryURL);
        categoryPage.demoNotice.close();
        boolean isProductInCart = categoryPage.addToCart(productId).viewCart().isProductInCart(productId);

        assertTrue(isProductInCart,
                "Remove button was not found for a product with id=" + productId + ". " +
                        "Was the product added to cart?");
    }

    @Test
    public void addOneProductTenTimesTest() {
        ProductPage productPage = new ProductPage(driver).goTo(productUrl);
        productPage.demoNotice.close();
        int productQuantity = productPage.addToCart(10).viewCart().getProductQuantity();

        assertEquals(10, productQuantity,
                "Quantity of the product is not what expected. Expected: 10, but was " + productQuantity);
    }

    @Test
    public void addTenProductsToCartTest() {
        ProductPage productPage = new ProductPage(driver);
        for(String product : productPages) {
            productPage.goTo("https://fakestore.testelka.pl/product" + product).addToCart();
        }
        int numberOfItems = productPage.header.viewCart().getNumberOfProducts();

        assertEquals(10,numberOfItems, "Number of items in the cart is not correct. Expected: 10, but was:" +
                numberOfItems);
    }

    @Test
    public void changeNumberOfProductsTest(){
       ProductPage productPage = new ProductPage(driver).goTo(productUrl);
       productPage.demoNotice.close();
       int quantity =  productPage.addToCart().viewCart().changeQuantity(8).updateCart().getProductQuantity();

        assertEquals(8, quantity,
                "Quantity of the product is not what expected. Expected: 8, but was " + quantity);
    }

    @Test
    public void removePositionFromCartTest(){
        ProductPage productPage = new ProductPage(driver).goTo(productUrl);
        productPage.demoNotice.close();
        boolean isCarEmpty = productPage.addToCart().viewCart().removeProduct(productId).isCartEmpty();
        assertTrue(isCarEmpty,
                "Cart is not empty after removing the product");
    }



//    @Test
//    public void buyWithoutAccountTest(){
//        addProductAndViewCart("https://fakestore.testelka.pl/product/egipt-el-gouna/");
//        driver.findElement(checkoutButton).click();
//        fillOutCheckoutForm("test1@testelka.pl");
//        fillOutCardData(true);
//        orderAndWaitToComplete();
//        int numberOfOrderReceivedMessages = driver.findElements(By.cssSelector(".woocommerce-thankyou-order-received")).size();
//        int expectedNumberOfMessages = 1;
//        assertTrue(expectedNumberOfMessages == numberOfOrderReceivedMessages,
//                "Number of 'order received' messages is not 1. Was the payment successful?");
//    }



}