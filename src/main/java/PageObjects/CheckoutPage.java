package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends BasePage{

    public HeaderPage header;
    public DemoFooterPage demoNotice;
    private WebDriverWait wait;

    By productPageAddToCartButton = By.cssSelector("button[name='add-to-cart']");
    By productPageViewCartButton = By.cssSelector(".woocommerce-message>.button");
    By countryCodeArrow = By.cssSelector(".select2-selection__arrow");
    By firstNameField = By.cssSelector("#billing_first_name");
    By lastNameField = By.cssSelector("#billing_last_name");
    By addressField = By.cssSelector("#billing_address_1");
    By postalCodeField = By.cssSelector("#billing_postcode");
    By cityField = By.cssSelector("#billing_city");
    By phoneField = By.cssSelector("#billing_phone");
    By emailField = By.cssSelector("#billing_email");
    By loadingIconLocator = By.cssSelector(".blockOverlay");
    By cardNumberFrameLocator = By.cssSelector("[name='__privateStripeFrame8']");
    By cardNumberFieldLocator = By.cssSelector("[name='cardnumber']");
    By expirationDateFrameLocator = By.cssSelector("[name='__privateStripeFrame9']");
    By expirationDateFieldLocator = By.cssSelector("[name='exp-date']");
    By cvcFrameLocator = By.cssSelector("[name='__privateStripeFrame10']");
    By cvcFieldLocator = By.cssSelector("[name='cvc']");
    By createNewAccountCheckbox = By.cssSelector("#createaccount");
    By password = By.cssSelector("#account_password");
    By summaryDate = By.cssSelector(".date>strong");
    By summaryPrice = By.cssSelector(".total .amount");
    By summaryPaymentMethod = By.cssSelector(".method>strong");
    By summaryProductRows = By.cssSelector("tbody>tr");
    By summaryProductQuantity = By.cssSelector(".product-quantity");
    By summaryProductName = By.cssSelector(".product-name>a");
    By summaryOrderNumberLocator = By.cssSelector(".order>strong");
    By orderButtonLocator = By.cssSelector("#place_order");
    private By termsCheckboxLocator = By.cssSelector("#terms");

    private String countryCodeCssSelector = "li[id*='-<country_code>']";

    public CheckoutPage(WebDriver driver) {
        super(driver);
        header = new HeaderPage(driver);
        demoNotice = new DemoFooterPage(driver);
        wait = new WebDriverWait(driver, 7);
    }

    public CheckoutPage typeName(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(firstNameField)).sendKeys(name);
        return this;
    }

    public CheckoutPage typeLastName(String lastName) {
        wait.until(ExpectedConditions.elementToBeClickable(lastNameField)).sendKeys(lastName);
        return this;
    }

    public CheckoutPage chooseCountry(String countryCode) {
        wait.until(ExpectedConditions.elementToBeClickable(countryCodeArrow)).click();
        By countyCodeLocator = By.cssSelector(countryCodeCssSelector.replace("<country_code>", countryCode));
        wait.until(ExpectedConditions.elementToBeClickable(countyCodeLocator)).click();
        return this;
    }

    public CheckoutPage typeAddress(String address) {
        wait.until(ExpectedConditions.elementToBeClickable(addressField)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addressField)).sendKeys(address);
        return this;
    }

    public CheckoutPage typePostalCode(String postalCode) {
        wait.until(ExpectedConditions.elementToBeClickable(postalCodeField)).sendKeys(postalCode);
        return this;
    }

    public CheckoutPage typeCity(String city) {
        wait.until(ExpectedConditions.elementToBeClickable(cityField)).sendKeys(city);
        return this;
    }

    public CheckoutPage typePhone(String phone) {
        wait.until(ExpectedConditions.elementToBeClickable(phoneField)).sendKeys(phone);
        return this;
    }

    public CheckoutPage typeEmail(String email) {
        wait.until(ExpectedConditions.elementToBeClickable(emailField)).sendKeys(email);
        return this;
    }

    public CheckoutPage typeCardNumber(String cardNumber){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIconLocator));
        WebElement cardNumberElement = findElementInFrame(cardNumberFrameLocator, cardNumberFieldLocator);
        slowType(cardNumberElement, cardNumber);
        driver.switchTo().defaultContent();
        return this;
    }

    public CheckoutPage typeCardExpirationDate(String expirationDate) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIconLocator));
        WebElement expirationDateElement = findElementInFrame(expirationDateFrameLocator, expirationDateFieldLocator);
        slowType(expirationDateElement, expirationDate);
        driver.switchTo().defaultContent();
        return this;
    }

    public CheckoutPage typeCvcCode(String cvcCode) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIconLocator));
        WebElement cvcElement = findElementInFrame(cvcFrameLocator, cvcFieldLocator);
        slowType(cvcElement,cvcCode);
        driver.switchTo().defaultContent();
        return this;
    }

    public OrderReceivedPage orderAndWaitToComplete(){
        driver.findElement(orderButtonLocator).click();
        return new OrderReceivedPage(driver);
    }

    public CheckoutPage selectAcceptTerms() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIconLocator));
        driver.findElement(termsCheckboxLocator).click();
        return this;
    }

    public OrderReceivedPage order() {
        driver.findElement(orderButtonLocator).click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(summaryOrderNumberLocator));
        return new OrderReceivedPage(driver);
    }

    private WebElement findElementInFrame(By frameLocator, By elementLocator){
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        return element;
    }

    private void slowType(WebElement element, String text) {
        for (int i = 0; i < text.length(); i++) {
            element.sendKeys(Character.toString(text.charAt(i)));
        }
    }



}
