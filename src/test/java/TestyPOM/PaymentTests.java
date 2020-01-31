package TestyPOM;

import PageObjects.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentTests extends BaseTest {

    private String productUrl = "https://fakestore.testelka.pl/product/egipt-el-gouna/";
    private String name = "Ola";
    private String lastName = "Nowak";
    private String countryCode = "BE";
    private String address = "Wielicka 2/15";
    private String postalCode = "80-001";
    private String city = "Sopot";
    private String phone = "123456789";
    private String emailAddress = "test1@testelka.pl";
    private String cardNumber = "4242424242424242";
    private String expirationDate = "0530";
    private String cvcCode = "123";

    @Test
    public void buyWithoutAccount() {
        ProductPage productPage = new ProductPage(driver).goTo(productUrl);
        productPage.demoNotice.close();
        CartPage cartPage = productPage.addToCart().viewCart();
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        OrderReceivedPage orderReceivedPage = checkoutPage.typeName(name)
                .typeLastName(lastName)
                .chooseCountry(countryCode)
                .typeAddress(address)
                .typePostalCode(postalCode)
                .typeCity(city)
                .typePhone(phone)
                .typeEmail(emailAddress)
                .typeCardNumber(cardNumber)
                .typeCardExpirationDate(expirationDate)
                .typeCvcCode(cvcCode)
                .selectAcceptTerms()
                .order();
        boolean isOrderSuccessful = orderReceivedPage.isOrderSuccessful();
        assertTrue(isOrderSuccessful, "No order successful message found");

//        boolean isOrderViewDisplay = checkbuttonClick().fillOutCheckoutForm("test1@testelka.pl").fillOutCardData(true).orderAndWaitToComplete().isOrderView();
//        assertTrue(isOrderViewDisplay, "Something goes wrong");
    }

}
