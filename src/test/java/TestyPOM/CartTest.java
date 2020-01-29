package TestyPOM;

import PageObjects.CategoryPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

public class CartTest extends BaseTests {


    String productId = "386";
    String productUrl = "https://fakestore.testelka.pl/product/egipt-el-gouna";
    String categoryURL = "https://fakestore.testelka.pl/product-category/windsurfing/";


    @Test
    public void addToCartFromProductPageTest(){
        ProductPage productPage = new ProductPage(driver);
        int productAmount = productPage.goTo(productUrl).addToCart().viewCart().getProductsAmount(productId);
        assertTrue(productAmount == 1,
                "Remove button was not found for a product with id=386 (Egipt - El Gouna). " +
                        "Was the product added to cart?");
    }

    @Test
    public void addToCartFromCategoryPageTest() {
        CategoryPage categoryPage = new CategoryPage(driver);
        int productAmount = categoryPage.goTo(categoryURL).addToCart(productId).viewCart().getProductsAmount(productId);

        assertTrue(productAmount == 1,
                "Remove button was not found for a product with id=386 (Egipt - El Gouna). " +
                        "Was the product added to cart?");
    }

    @Test
    public void addOneProductTenTimesTest() {
        ProductPage productPage = new ProductPage(driver);
        int productQuantity = productPage.goTo(productUrl).addToCart(10).viewCart().getProductQuanity();
        assertEquals(10, productQuantity,
                "Quantity of the product is not what expected. Expected: 10, but was " + productQuantity);

    }

}


