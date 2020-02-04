package TestyPOM;

import PageObjects.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentTests extends BaseTest {

//    private String productUrl = testData.getProduct().getUrl();
//            ///"https://fakestore.testelka.pl/product/egipt-el-gouna/";
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
        ProductPage productPage = new ProductPage(driver).goTo(configuration.getBaseUrl() + testData.getProduct().getUrl());
        productPage.demoNotice.close();
        CartPage cartPage = productPage.addToCart().viewCart();
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        OrderReceivedPage orderReceivedPage = checkoutPage.typeName(testData.getCustomer().getName())
                .typeLastName(testData.getCustomer().getLastName())
                .chooseCountry(testData.getAddress().getCountryCode())
                .typeAddress(testData.getAddress().getStreet())
                .typePostalCode(testData.getAddress().getPostalCode())
                .typeCity(testData.getAddress().getCity())
                .typePhone(testData.getContact().getPhone())
                .typeEmail(testData.getContact().getEmailAddress())
                .typeCardNumber(testData.getCard().getNumber())
                .typeCardExpirationDate(testData.getCard().getExpirationDate())
                .typeCvcCode(testData.getCard().getCvc())
                .selectAcceptTerms()
                .order();
        boolean isOrderSuccessful = orderReceivedPage.isOrderSuccessful();
        assertTrue(isOrderSuccessful, "No order successful message found");
    }

}
