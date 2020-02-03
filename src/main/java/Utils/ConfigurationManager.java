package Utils;

import org.openqa.selenium.remote.BrowserType;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {


    private String hubUrl;
    private String baseUrl;
    private String browser;
    private final String configurationLocation = "src/configs/Configuration.properties";

    public ConfigurationManager() {
        loadData();
    }

    public void loadData() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getConfigurationLocation()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There is something wrong with the configuration file. Doest it exist?" +
                    getConfigurationLocation());
        }
        hubUrl = properties.getProperty("hubUrl");
        baseUrl = properties.getProperty("baseUrl");
        browser = properties.getProperty("browser");
    }

    public String getBrowser() {
        return browser;
    }

    public String getHubUrl() {
        return hubUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getConfigurationLocation() {
        return configurationLocation;
    }

}