package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderReceivedPage extends BasePage {

    public HeaderPage header;
    public DemoFooterPage demoNotice;
    private WebDriverWait wait;

    private By orderReceivedMessageLocator = By.cssSelector(".woocommerce-thankyou-order-received");

    public OrderReceivedPage (WebDriver driver) {
        super(driver);
        header = new HeaderPage(driver);
        demoNotice = new DemoFooterPage(driver);
        wait = new WebDriverWait(driver, 20);
    }

    public boolean isOrderSuccessful() {
        wait.until(ExpectedConditions.urlContains("/zamowienie/zamowienie-otrzymane/"));
        int numberOfSuccessMessages = driver.findElements(orderReceivedMessageLocator).size();
        if(numberOfSuccessMessages == 1) {
            return true;
        } else if (numberOfSuccessMessages == 0) {
            return false;
        } else {
            throw new IllegalArgumentException("Wrong number of shop table elements: there can be only one or none");
        }
    }
}
