package TestyPOM;
import Drivers.Browser;
import Drivers.DriverFactory;
import Utils.ConfigurationManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
    protected WebDriver driver;
    protected ConfigurationManager configuration;


    @BeforeAll
    public void getConfiguration() {
        configuration = new ConfigurationManager();
    }

    @BeforeEach
    public void testSetUp(){

        DriverFactory driverFactory = new DriverFactory();
        driver = driverFactory.create(configuration);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        driver.manage().window().maximize();
    }

    @AfterEach
    public void closeDriver() {
        driver.quit();
    }
}