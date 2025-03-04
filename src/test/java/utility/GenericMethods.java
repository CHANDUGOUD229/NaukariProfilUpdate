package utility;

import io.cucumber.java.en_old.Ac;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

public class GenericMethods extends DriverFactory {


    public static Logger LOGGER = Logger.getLogger(GenericMethods.class);
//    public static final Logger LOGGER = LogManager.getLogger(GenericMethods.class);

    /**
     * This Method Will Return the screenShot
     *
     * @param name
     * @return
     * @throws IOException
     */
    public static String getScreenshot(String name) throws IOException {
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        String target = System.getProperty("user.dir") + "/Reports/Screenshots/" + name + date + ".png";
        File finalDestination = new File(target);
        FileUtils.copyFile(source, finalDestination);
        LOGGER.info("Took the ScreenShot");
        return target;
    }

    /**
     * This Method Will Return the screenShot
     *
     * @param name
     * @return
     * @throws IOException
     */
    public String getScreenshotAs(String name) throws IOException {
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        String target = System.getProperty("user.dir") + "/Reports/Screenshots/" + name + date + ".png";
        File finalDestination = new File(target);
        FileUtils.copyFile(source, finalDestination);
        LOGGER.info("Took the ScreenShot");
        return target;
    }


    /**
     * This Method Will Return the screenShot
     * @param name
     * @return
     * @throws IOException
     */


    /**
     * This Method Switched Windows Based on the title of the Window
     *
     * @param targetTitle
     */
    public static void switchToWindow(String targetTitle) {
        String parentWindow = getDriver().getWindowHandle();
        boolean isSwitched = false;
        for (String handle : getDriver().getWindowHandles()) {
            String actualTitle = getDriver().switchTo().window(handle).getTitle();
            if (targetTitle.equals(actualTitle)) {
                getDriver().switchTo().window(handle);
                //perform any action here
                isSwitched = true;
                break;
            } else {
                writeLogInfo("Please Enter the Correct Page Title for the proper switching to the desired window");

            }
        }
        if (!isSwitched) {
            writeLogInfo("No window found with the title: " + targetTitle);
            getDriver().switchTo().window(parentWindow); // Switch back if not found
        }
    }


    /**
     * This Methed Will Returns The Text of List of WebElements
     *
     * @param list
     * @return
     */
    public static List<String> getElementsText(List<WebElement> list) {
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : list) {
            elemTexts.add(el.getText());
        }
        return elemTexts;
    }

    /**
     * This Methed Will Returns The Text of List of WebElements,
     * Here we are passing Locator
     *
     * @param locator
     * @return List<String>
     */
    public static List<String> getElementsText(By locator) {

        List<WebElement> elems = getDriver().findElements(locator);
        List<String> elemTexts = new ArrayList<>();

        for (WebElement el : elems) {
            elemTexts.add(el.getText());
        }
        return elemTexts;
    }

    /**
     * This Method will wait for Static Time
     * Here we are used Thread.sleep() Method
     *
     * @param seconds
     */
    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static WebElement waitForElementVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(WebElement element, int timeout) {
        //WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * This Method will Wait until Specific Web Element Visible
     *
     * @param element
     * @param timeInSeconds
     */
    public static void waitForElementToBevisible(WebElement element, int timeInSeconds) {
        int maxAttempts = 5;
        int minattempts = 0;
        boolean visibility = false;
        try {
            for (int i = minattempts; i <= maxAttempts; i++) {
                Wait wait = new FluentWait(getDriver())
                        .withTimeout(Duration.ofSeconds(timeInSeconds))
                        .pollingEvery(Duration.ofSeconds(timeInSeconds))
                        .ignoring(Exception.class);
                wait.until(ExpectedConditions.visibilityOf(element));
                visibility = true;
                if (visibility) {
                    System.out.println(element + " is visible");
                    break;
                } else {
                    System.out.println(element + " is not visible after trying with 60 seconds ");
                }
            }
        } catch (NoSuchElementException e) {
            System.err.println(e);
        }
    }

    /**
     * This Method will wait fot Until web element visible Then It will Perform click Action
     * And When Click action perform It will Highlight the focused web element
     *
     * @param element
     */
    public static void waitForElementTobeClick(WebElement element) {
        int maxAttempts = 5;
        int minattempts = 0;
        boolean visibility = false;
        try {
            for (int i = minattempts; i <= maxAttempts; i++) {
                FluentWait wait = new FluentWait(getDriver())
                        .withTimeout(Duration.ofSeconds(60))
                        .pollingEvery(Duration.ofSeconds(10))
                        .ignoring(TimeoutException.class);
                wait.until(ExpectedConditions.visibilityOf(element));
                visibility = true;
                if (visibility) {
                    scrollToElement(element);
                    clickWithJS(element);

                    break;
                } else {
                    System.out.println(element + " is not visible after trying with 60 seconds ");
                }
            }
        } catch (NoSuchElementException e) {
            System.err.println(e);
        }
    }

    public static void waitForElementTobeJsClick(WebElement element) {
        int maxAttempts = 5;
        int minattempts = 0;
        boolean visibility = false;
        try {
            for (int i = minattempts; i <= maxAttempts; i++) {
                FluentWait wait = new FluentWait(getDriver())
                        .withTimeout(Duration.ofSeconds(60))
                        .pollingEvery(Duration.ofSeconds(10))
                        .ignoring(TimeoutException.class);
                wait.until(ExpectedConditions.visibilityOf(element));
                visibility = true;
                if (visibility) {
                    scrollToElement(element);
                    clickWithJS(element);
                    break;
                } else {
                    System.out.println(" is not visible after trying with 60 seconds ");
                }
            }
        } catch (NoSuchElementException e) {

            System.err.println(e);
        }
    }

    /**
     * This Method will wait until Web element visible then After it will Perform The SendKeys Action
     *
     * @param element
     * @param text
     */
    public static void
    waitForElementTobeEnterText(WebElement element, String text) {
        int maxAttempts = 5;
        int minattempts = 0;
        boolean visibility = false;
        try {
            for (int i = minattempts; i <= maxAttempts; i++) {
                FluentWait wait = new FluentWait(getDriver())
                        .withTimeout(Duration.ofSeconds(60))
                        .pollingEvery(Duration.ofSeconds(10))
                        .ignoring(TimeoutException.class);
                wait.until(ExpectedConditions.visibilityOf(element));
                visibility = true;
                if (visibility) {
                    enterText(element, text);
                    break;
                } else {
                    System.out.println(" is not visible after trying with 60 seconds ");
                }
            }
        } catch (NoSuchElementException e) {

            System.err.println(e);
        }
    }

    /**
     * This Method will click operation using JAVA SCRIPT EXECUTER,
     * This Method Also Hightlight the WebElement
     *
     * @param element
     */
    public static void clickWithJS(WebElement element) {
        scrollToElement(element);
        highlight(element);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
    }

    /**
     * This Method performs Click Atcion And Highlight the Web element
     *
     * @param element
     */
    public static void click(WebElement element) {
        scrollToElement(element);
        highlight(element);
        element.click();
    }

    /**
     * This Method will Scroll in to Particular Web Element using JAVA SCRIPT EXECUTER
     *
     * @param element
     */
    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }


    /**
     * Changes the HTML attribute of a Web Element to the given value using JavaScript
     *
     * @param element
     * @param attributeName
     * @param attributeValue
     */
    public static void setAttribute(WebElement element, String attributeName, String attributeValue) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attributeName, attributeValue);
    }

    /**
     * Its just Highlight the WebElement using JAVA SCRIPT EXECUTER
     *
     * @param element
     */
    public static void highlight(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }

    /**
     * assrtHighlight ..............
     *
     * @param element
     */
    public static void assrtHighlight(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].setAttribute('style', 'background: red; border: 2px solid red;');", element);
    }

    /**
     * This method will Select the unSelected checkBox
     *
     * @param element
     */
    public static void selectCheckBox(WebElement element) {
        try {
            if (!element.isSelected()) {
                element.click();

            } else {
                System.out.println(element + " is already selected");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This method will unSelect the If checkBox is Selected
     *
     * @param element
     */
    public static void unSelectCheckBox(WebElement element) {

        if (element.isSelected()) {
            element.click();
        } else {
            System.out.println(element + " is already Unselected");
        }
    }

    /**
     * This method will execute JS just you need to pass which command you need to execute
     * and pass the particular element
     *
     * @param element
     * @param command
     */
    public static void executeJSCommand(WebElement element, String command) {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript(command, element);

    }

    /**
     * It will enter Text in to text filed
     *
     * @param element
     * @param text
     */
    public static void enterText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    /**
     * It will perform the asertEquals
     *
     * @param element
     * @param actual
     * @param Expected
     */
    public static void assertEquals(WebElement element, String actual, String Expected) {
        try {
            if (!actual.isEmpty() && !Expected.isEmpty()) {
                assrtHighlight(element);
                Assert.assertEquals(actual, Expected);
                System.out.println("Given " + actual + "and " + Expected + "both are same");
            } else {

            }
        } catch (Exception e) {

        }
    }

    /**
     * Assert Not equals it will compare expected and actual strings
     *
     * @param element
     * @param actual
     * @param Expected
     */

    public static void assertNotEquals(WebElement element, String actual, String Expected) {
        try {
            if (!actual.isEmpty() && !Expected.isEmpty()) {
                assrtHighlight(element);
                Assert.assertNotEquals(actual, Expected);
                System.out.println("Given " + actual + "and " + Expected + "both are Not same");
            } else {

            }
        } catch (Exception e) {

        }
    }

    // Generic method to assert that a condition is true
    public void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    // Generic method to assert that a condition is true
    public void assertTrue(WebElement element, boolean condition, String message) {
        Assert.assertTrue(condition, message);
        assrtHighlight(element);

    }

    public static void assertEqualsTitles(String actual, String Expected) {
        try {
            if (!actual.isEmpty() && !Expected.isEmpty()) {
                Assert.assertEquals(actual, Expected);
                System.out.println("Given " + actual + "and " + Expected + "both are same");
            } else {

            }
        } catch (Exception e) {

        }
    }

    public static void assertEqualsText(WebElement element, String Expected) {
        try {
            String actual = element.getText();
            if (!actual.isEmpty() && !Expected.isEmpty()) {
                assrtHighlight(element);
                Assert.assertEquals(actual, Expected);
                System.out.println("Given " + actual + "and " + Expected + "both are same");
            } else {

            }
        } catch (Exception e) {

        }
    }

    // Example usage: Assert that an element is displayed
    public void assertElementDisplayed(WebElement element, String message) {
        assertTrue(element.isDisplayed(), message);
        assrtHighlight(element);
    }


    // Example usage: Assert that the page title contains expected text
    public void assertPageTitleContains(String expectedText, String message) {
        assertTrue(getDriver().getTitle().contains(expectedText), message);
    }

    // Example usage: Assert that the current URL matches expected URL
    public void assertCurrentUrlMatches(String expectedUrl, String message) {
        assertTrue(getDriver().getCurrentUrl().equals(expectedUrl), message);
    }

    /**
     * This method will generate the Random Email
     *
     * @return String
     */
    public static String randomEmailGenerator() {
        return RandomStringUtils.randomAlphanumeric(8) + "@gmail.com";
    }


    public static String getLocalisedDate(int year, int month, int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = new GregorianCalendar(year, month, day);
        Date date = cal.getTime();
        String dateString = dateFormat.format(date);
        return dateString;

    }

    public static boolean looseStringCompare(String stringA, String stringB) {
        if (stringA.length() != stringB.length()) {
            return false;
        }
        boolean result = true;
        for (int i = 0; i < stringA.length(); i++) {
            char charA = stringA.charAt(i);
            char charB = stringB.charAt(i);
            if (Character.isLetterOrDigit(charA) && Character.isLetterOrDigit(charB)) {
                result = result && (charA == charB);
            }
        }
        return result;
    }


    // Mouse Actions methods................

    /**
     * Method to click on a WebElement
     *
     * @param element
     */
    public static void mouseClickAction(WebElement element) {
        new Actions(getDriver()).click(element).build().perform();
    }

    /**
     * This Method will perform the Double click Operation
     *
     * @param element
     */
    public static void doubleClick(WebElement element) {
        new Actions(getDriver()).doubleClick(element).build().perform();
    }

    /**
     * Method to right-click on a WebElement
     *
     * @param element
     */
    public static void rightClick(WebElement element) {
        new Actions(getDriver()).contextClick(element).build().perform();
    }

    /**
     * Mouse Hover option By using Actions Class
     * Method to hover over a WebElement
     *
     * @param element
     */
    public static void hover(WebElement element) {
        new Actions(getDriver()).moveToElement(element).build().perform();
    }

    /**
     * scrollToElementByAction
     *
     * @param element
     */
    public static void scrollToElementByAction(WebElement element) {
        new Actions(getDriver()).scrollToElement(element).build().perform();
    }

    /**
     * sendKeys by using Actions Class
     *
     * @param element
     * @param text
     */
    public static void sendKeysByActions(WebElement element, String text) {
        new Actions(getDriver()).sendKeys(element, text).build().perform();
    }

    /**
     * dragAndDrop using Action class
     *
     * @param sourceElement
     * @param destElement
     */
    public static void dragAndDrop(WebElement sourceElement, WebElement destElement) {
        new Actions(getDriver()).dragAndDrop(sourceElement, destElement).build().perform();
    }

    //Handling DropDown Methods

    public static void selectValueByOptions(WebElement element, String visibleText) {
        Select select = new Select(element);
        List<WebElement> options = select.getOptions();

        for (WebElement option : options) {
            String value = option.getText();
            if (value.equalsIgnoreCase(visibleText)) {
                option.click();
            }
        }
    }

    /**
     * It wii deselect the selected values from dropdown
     *
     * @param element
     */
    public static void deSelectAllOptions(WebElement element) {
        Select select = new Select(element);
        select.deselectAll();

    }

    /**
     * Handling Boostrap DropDown
     *
     * @param webElements
     * @param visibleText
     */
    public static void handleBoostrapDropDown(List<WebElement> webElements, String visibleText) {
        boolean flag = false;
        for (WebElement element : webElements) {
            String option = element.getText();
            System.out.println(option);
            if (option.equals(visibleText)) {
                element.click();
                flag = true;
                break;
            }
        }
        if (flag == false) {
            System.out.println("Please enter correct value");
        }

    }

    /**
     * Method to select an option by visible text
     *
     * @param dropdownElement
     * @param visibleText
     */
    public static void selectByVisibleText(WebElement dropdownElement, String visibleText) throws InterruptedException {
        dropdownElement.click();
        Thread.sleep(5000);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(visibleText);
    }

    /**
     * Method to select an option by value
     *
     * @param dropdownElement
     * @param value
     */
    public static void selectByValue(WebElement dropdownElement, String value) {
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue(value);
    }

    /**
     * Method to select an option by index
     *
     * @param dropdownElement
     * @param index
     */
    public static void selectByIndex(WebElement dropdownElement, int index) {
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByIndex(index);
    }

    /**
     * Method to unselect an option by visible text
     *
     * @param dropdownElement
     * @param visibleText
     */
    public void deselectByVisibleText(WebElement dropdownElement, String visibleText) {
        Select dropdown = new Select(dropdownElement);
        dropdown.deselectByVisibleText(visibleText);
    }

    /**
     * Method to unselect an option by value
     *
     * @param dropdownElement
     * @param value
     */
    public void deselectByValue(WebElement dropdownElement, String value) {
        Select dropdown = new Select(dropdownElement);
        dropdown.deselectByValue(value);
    }

    /**
     * Method to unselect an option by index
     *
     * @param dropdownElement
     * @param index
     */
    public void deselectByIndex(WebElement dropdownElement, int index) {
        Select dropdown = new Select(dropdownElement);
        dropdown.deselectByIndex(index);
    }

    //Logger writer methods

    //below methods are for the Logging purpose
    public static void writeLogInfo(String loginfo) {
        LOGGER.info("*************  " + loginfo + "  *************");
    }

    public void writeLoginfo(String loginfo) {
        LOGGER.info("*************  " + loginfo + "  *************");
    }

    public static void writeLogWarn(String logWarn) {
        LOGGER.warn("*************  " + logWarn + "  *************");
    }

    public void writeLogwarn(String logWarn) {
        LOGGER.warn("*************  " + logWarn + "  *************");
    }

    public static void writeLogDebug(String logWarn) {
        LOGGER.debug("*************  " + logWarn + "  *************");
    }

    public void writeLogdebug(String logWarn) {
        LOGGER.debug("*************  " + logWarn + "  *************");
    }

    public static void writeLogError(String logError) {
        LOGGER.error("*************  " + logError + "  *************");
    }

    public void writeLogerror(String logError) {
        LOGGER.error("*************  " + logError + "  *************");
    }

    public static void selectRadioButtonByValue(String radioButtonGroupName, String value) {
        String radioButtonLocator = "//input[@type='radio' and @name='" + radioButtonGroupName + "' and @value='" + value + "']";
        WebElement radioButton = getDriver().findElement(By.xpath(radioButtonLocator));
        radioButton.click();
    }

    public static void checkBrokenLinks(List<WebElement> links) {
        //driver.findElements(By.tagName("a"));
        System.out.println("Total links found: " + links.size());

        for (WebElement link : links) {
            String url = link.getAttribute("href");
            if (url != null && !url.isEmpty()) {
                try {
                    HttpURLConnection connection = (HttpURLConnection) (new URL(url).openConnection());
                    connection.setRequestMethod("HEAD");
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode >= 400) {
                        System.out.println("Broken link found: " + url + " (Response Code: " + responseCode + ")");
                    } else {
                        System.out.println("Valid link found: " + url);
                    }
                    connection.disconnect();
                } catch (IOException e) {
                    System.out.println("Error while checking link: " + url);
                    e.printStackTrace();
                }
            } else {
                System.out.println("Link with empty or null URL found");
            }
        }
    }


    public static void checkBrokenImages(List<WebElement> images) {
        // driver.findElements(By.tagName("img"));
        System.out.println("Total images found: " + images.size());

        for (WebElement image : images) {
            String imageUrl = image.getAttribute("src");
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    HttpURLConnection connection = (HttpURLConnection) (new URL(imageUrl).openConnection());
                    connection.setRequestMethod("HEAD");
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode >= 400) {
                        System.out.println("Broken image found: " + imageUrl + " (Response Code: " + responseCode + ")");
                    } else {
                        System.out.println("Valid image found: " + imageUrl);
                    }
                    connection.disconnect();
                } catch (IOException e) {
                    System.out.println("Error while checking image: " + imageUrl);
                    e.printStackTrace();
                }
            } else {
                System.out.println("Image with empty or null URL found");
            }
        }
    }

//Switch between frams

    /**
     * Method to switch to a frame by index
     *
     * @param index
     */
    public static void switchToFrameByIndex(int index) {
        getDriver().switchTo().frame(index);
    }

    /**
     * Method to switch to a frame by name or id
     *
     * @param frameNameOrId
     */
    public static void switchToFrameByNameOrId(String frameNameOrId) {
        getDriver().switchTo().frame(frameNameOrId);
    }

    /**
     * Method to switch to a frame by WebElement
     *
     * @param frameElement
     */
    public static void switchToFrameByWebElement(WebElement frameElement) {
        getDriver().switchTo().frame(frameElement);
    }

    /**
     * Method to switch back to the default content (main page)
     */
    public static void switchToDefaultContent() {
        getDriver().switchTo().defaultContent();
    }

    public static WebElement findElementByShadowDomXPath(String shadowHostCssSelector, String xpath) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        // Find the shadow host element
        WebElement shadowHost = (WebElement) jsExecutor.executeScript("return document.querySelector(arguments[0]);", shadowHostCssSelector);

        // Find the shadow root
        WebElement shadowRoot = (WebElement) jsExecutor.executeScript("return arguments[0].shadowRoot;", shadowHost);

        // Execute the XPath query within the shadow root
        return (WebElement) jsExecutor.executeScript("return arguments[0].querySelector('" + xpath + "');", shadowRoot);
    }

    public static void handleAlert(String action) {
        Alert alert = getDriver().switchTo().alert();
        if (action.equalsIgnoreCase("accept")) {
            alert.accept();
            System.out.println("Alert accepted");
        } else if (action.equalsIgnoreCase("dismiss")) {
            alert.dismiss();
            System.out.println("Alert dismissed");
        } else {
            System.out.println("Invalid action specified. Use 'accept' or 'dismiss'.");
        }
    }


    public static HashMap<String, String> UrlMap = new HashMap<>();

    public HashMap<String, String> getUrl() {
        PropertyFilesLoader pf = new PropertyFilesLoader();

        if (pf.getProperty("Environment").equalsIgnoreCase("sit")) {
            UrlMap.put("url", "https://www.naukri.com/nlogin/login");
        } else if (pf.getProperty("Environment").equalsIgnoreCase("AUT")) {
            UrlMap.put("url", "https://www.dell.com/en-in");
        } else if (pf.getProperty("Environment").equalsIgnoreCase("UAT")) {
            UrlMap.put("url", "https://www.dell.com/en-in");
        }
        return UrlMap;
    }

    /**
     * Robot class for file upload
     *
     * @param filePath
     */


    /**
     * handleWebTable
     *
     * @param table
     * @param tableSelector
     */
    public static void handleWebTable(WebElement table, String tableSelector) {

        // Get all rows of the table
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        // Iterate through each row
        for (WebElement row : rows) {
            // Get all columns of the current row
            List<WebElement> columns = row.findElements(By.tagName("td"));

            // Iterate through each column
            for (WebElement column : columns) {
                // Perform actions with the cell data, for example, print it
                System.out.print(column.getText() + "\t\t");
                // You can also perform other actions like clicking links or interacting with form elements
            }
            System.out.println(); // Move to the next line for the next row
        }
    }

    /**
     * handleMultiSelectDropdown
     *
     * @param dropdownSelector
     * @param optionValues
     */
    public static void handleMultiSelectDropdown(WebElement dropdown, String dropdownSelector, String... optionValues) {

        // Create a new Select object
        Select select = new Select(dropdown);

        // Check if the dropdown supports multiple selection
        if (!select.isMultiple()) {
            System.out.println("Dropdown does not support multiple selection.");
            return;
        }

        // Deselect all options before selecting new ones (optional, depending on your use case)
        select.deselectAll();

        // Iterate through option values to select
        for (String optionValue : optionValues) {
            // Select each option by its value
            select.selectByValue(optionValue);
        }
    }

    /**
     * Using Robot class we can upload File By passing File Directory
     *
     * @param filePath
     * @throws AWTException
     */
    public static void fileUpload(String filePath) throws AWTException {
        StringSelection selection = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
        Robot robot = new Robot();

        robot.setAutoDelay(2000);

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);

        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);

        robot.setAutoDelay(2000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public static void HadlingDyanamicPagenation(WebElement numberOfpagesText) {
        String text = numberOfpagesText.getText();

        int numberOfPages = Integer.parseInt(text.substring(text.indexOf("(") + 1, text.indexOf("Pages") - 1));


    }


    /**
     * This method will destroy the driver from command prompt
     *
     * @param command
     */
    public static void executeCommand(String command) {
        try {
            getDriver().get("cmd://");
            Runtime.getRuntime().exec(command);
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void openNewWindow() {
        getDriver().switchTo().newWindow(WindowType.WINDOW);
    }

    public static void openNewTab() {
        getDriver().switchTo().newWindow(WindowType.TAB);
    }

    public static void uploadFileUsingClipboard(WebElement element, String filePath) throws AWTException, InterruptedException {
        Thread.sleep(3000);
        element.click();
        Thread.sleep(3000);
        StringSelection stringSelection = new StringSelection(filePath);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);

        // Simulate releasing Ctrl + V
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);

        // Optional: Add a short delay for paste action to complete
        robot.delay(500);
    }


}
