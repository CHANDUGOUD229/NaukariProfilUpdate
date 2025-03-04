package utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class testCvommandPrompt extends DriverFactory {

    public static void main(String[] args) throws IOException {
        initiatingBrowser();
        getDriver().get("cmd://");
        executeCommand("taskkill /f /im chrome.exe /t",getDriver());
        getDriver().quit();
    }

    public static void executeCommand(String command, WebDriver driver) {
        try {
            Runtime.getRuntime().exec(command);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
