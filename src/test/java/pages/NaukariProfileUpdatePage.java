package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.GenericMethods;

import java.awt.*;
import java.time.Duration;
import java.util.List;

import static utility.GenericMethods.*;

public class NaukariProfileUpdatePage extends GenericMethods {


    public NaukariProfileUpdatePage() {
        PageFactory.initElements(getDriver(), this);
    }


    @FindBy(id = "usernameField")
    WebElement email;
    @FindBy(id = "passwordField")
    WebElement passWord;
    @FindBy(xpath = "//button[.='Login']")
    WebElement loginBtn;

    @FindBy(xpath = "//span[@class='text' and .='Key skills']")
    WebElement keySkillsLink;

    @FindBy(xpath = "(//span[text()='Key skills'])[2]/../span[@class='edit icon']")
    WebElement editKeySkills;

    @FindBy(xpath = "//*[@id='keySkillSugg']")
    WebElement addNewSkill;

    @FindBy(id = "saveKeySkills")
    WebElement saveBtn;

    @FindBy(xpath = "//img[@class='nI-gNb-icon-img']")
    WebElement profileImg;
    @FindBy(xpath = "//a[@class='nI-gNb-list-cta' and .='Logout']")
    WebElement logoutButton;

    @FindBy(xpath = "//input[@class='dummyUpload typ-14Bold']")
    WebElement fileUpload;

    @FindBy(xpath = "//div[@class='truncate exten']")
    WebElement textOfResume;
    @FindBy(xpath = "//div[@title='GIT']//a[@class='material-icons close'][normalize-space()='Cross']")
    WebElement crossMark;
    //form/div[2]/div[1]/div/div[1]/div[1]/div[1]/div[13]/a
    @FindBy(xpath = "//*[.='GIT']")
    WebElement isGitTextIsDisplaying;
    @FindBy(xpath = "//a[@href='/mnjuser/profile' and .='View profile']")
    WebElement viewProfile;
    @FindBy(xpath = "//div[@id='lazyKeySkills']//child::span[@class='chip typ-14Medium']")
    List<WebElement> lazyKeySkills;
    @FindBy(xpath = "//ul[@class='Sdrop']/li/div")
    List<WebElement> searchSuggestions;


    public void enterLoginDetails() {
        writeLogInfo("user landed on login page");
        waitForElementTobeEnterText(email, getProperty("username"));
        writeLogInfo("user entered user name ");

        assertElementDisplayed(email, email + "Element not Displaying");
        waitForElementTobeEnterText(passWord, getProperty("password"));
        writeLogInfo("user entered passWord ");
        assertElementDisplayed(passWord, passWord + "Element not Displaying");
        click(loginBtn);
        writeLogInfo("user clicked on the login button");

    }

    public void clickOnKeySkillLink() {
        click(viewProfile);
        writeLogInfo("user clicked on the view profile button ");

        click(keySkillsLink);
        writeLogInfo("user clicked on menu keySkill  Link ");

    }


    public void fileUpload() throws AWTException, InterruptedException {

        uploadFileUsingClipboard(fileUpload, ".//src//test//resources//testDataFiles//Resume_chandu_5+years.pdf");

    }

    public void logout() throws InterruptedException {
        Thread.sleep(6000);
        clickWithJS(profileImg);
        click(logoutButton);
    }


    public void updateKeySkills() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            try {
                if (isGitTextIsDisplaying.isDisplayed()) {
                    clickWithJS(editKeySkills);
                    clickWithJS(crossMark);
                    clickWithJS(saveBtn);
                    System.out.println("skill deleted");
                    // Adding a wait to allow the UI to update before continuing
                    Thread.sleep(2000);
                } else {
                    System.out.println("Git skill is not displayed, adding it...");
                }
            } catch (NoSuchElementException e) {
                System.out.println("Element not found, proceeding to add GIT skill.");
            }

            // Add GIT skill
            clickWithJS(editKeySkills);
            addNewSkill.sendKeys("GIT");
            // Click outside to confirm
            getDriver().findElement(By.xpath("//html")).click();
            clickWithJS(saveBtn);
            System.out.println("added new skill");
            // Adding a delay before the next iteration
            Thread.sleep(2000);
        }
    }

}
