package TestyPOM;
import Drivers.Browser;
import Drivers.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import java.net.MalformedURLException;

public class BaseTest {
    protected WebDriver driver;
    @BeforeEach
    public void testSetUp() throws MalformedURLException{

        DriverFactory driverFactory = new DriverFactory();
        driver = driverFactory.create(Browser.FIREFOX);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);



        driver.manage().window().maximize();
    }
    @AfterEach
    public void closeDriver() {
        driver.quit();
    }
}