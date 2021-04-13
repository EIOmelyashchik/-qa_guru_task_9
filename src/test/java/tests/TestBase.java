package tests;

import com.codeborne.selenide.Browsers;
import com.codeborne.selenide.Configuration;
import config.DriverConfig;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentHelper.*;

public class TestBase {
    private static final DriverConfig driverConfig = ConfigFactory.create(DriverConfig.class);

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://demoqa.com";

        addListener("AllureSelenide", new AllureSelenide());

        String remoteServer = System.getProperty("remote.server");
        if (remoteServer != null) {
            String url = driverConfig.remoteWebUrl();
            String user = driverConfig.remoteWebUser();
            String password = driverConfig.remoteWebPassword();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", isVideoEnabled());
            Configuration.browserCapabilities = capabilities;
            Configuration.remote = String.format(url, user, password, remoteServer);
        }
        Configuration.browser = System.getProperty("web.browser", "chrome");
    }

    @AfterEach
    void tearDown() {
        attachScreenshot("Last screenshot");
        attachPageSource();
        if (Configuration.browser.equals(Browsers.CHROME))
            attachAsText("Browser console logs", getConsoleLogs());
        String videoStorage = System.getProperty("video.storage");
        if (isVideoEnabled() && videoStorage != null)
            attachVideo(videoStorage);
        closeWebDriver();
    }

    private static boolean isVideoEnabled() {
        return Boolean.parseBoolean(System.getProperty("video.enabled", "true"));
    }
}
