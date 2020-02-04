package TestyPOM;
import Drivers.DriverFactory;
import Utils.ConfigurationReader;
import Utils.TestDataReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
    protected WebDriver driver;
    protected ConfigurationReader configuration;
    protected TestDataReader testData;
    private String testDataLocation = "src/test/java/TestData.properties";
    private String configurationLocation = "src/configs/Configuration.properties";


    @BeforeAll
    public void getConfiguration() {
        configuration = new ConfigurationReader(configurationLocation);
        testData = new TestDataReader(testDataLocation);
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