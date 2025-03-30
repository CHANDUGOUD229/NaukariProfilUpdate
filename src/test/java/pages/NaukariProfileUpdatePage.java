package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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
    @FindBy(xpath = "//div[@title='Automation_5.5yrs.pdf']")
    WebElement successText;

    @FindBy(xpath = "//span[@class='dummyUploadNewCTA']")
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

    @FindBy(xpath = "//i[.='deleteOneTheme']")
    WebElement deleteResumeImg;
    @FindBy(xpath = "//*[.='Resume has been successfully uploaded.']")
    WebElement resumeUploadedSuccessfully;
    @FindBy(xpath = "//*[.='Attached Resume has been successfully deleted.']")
    WebElement resumeDeletedSuccessfully;

    @FindBy(xpath = "//div[@class='action right-align']/following::button[@class='btn-dark-ot']")
    WebElement deleteBtn;
    @FindBy(xpath = "//*[.='Key Skills have been successfully saved.']")
    WebElement keySkillsSaved;

    public void enterLoginDetails() {
        writeLogInfo("user landed on login page");
        waitForElementTobeEnterText(email, getProperty("username"));
        writeLogInfo("user entered user name ");
        waitForElementTobeEnterText(passWord, getProperty("password"));
        writeLogInfo("user entered passWord ");
        click(loginBtn);
        writeLogInfo("user clicked on the login button");

    }

    public void deleteResumeImg() {

        new WebDriverWait(getDriver(), Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(deleteResumeImg));
        clickWithJS(deleteResumeImg);
        new WebDriverWait(getDriver(), Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(deleteBtn));
        clickWithJS(deleteBtn);
    }

    public void validateDeletedSuccessfully() {
        assertEqualsText(resumeDeletedSuccessfully, "Attached Resume has been successfully deleted.");
    }


    public void validateKeySkillSuccessMsg() {
        assertEqualsText(keySkillsSaved, "Key Skills have been successfully saved.");
    }

    public void clickOnKeySkillLink() {
        click(viewProfile);
        writeLogInfo("user clicked on the view profile button ");

    }


    public void fileUpload() throws AWTException, InterruptedException {
        new WebDriverWait(getDriver(), Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(fileUpload));
        fileUpload.click();
        writeLogInfo("user clicked on fil upload button ");
        uploadFileUsingClipboard("file:///D:/Resume/Automation_5.5yrs.pdf");
        new WebDriverWait(getDriver(), Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOf(resumeUploadedSuccessfully));
        assertEqualsText(resumeUploadedSuccessfully, "Resume has been successfully uploaded.");

    }

    public void validateResumeUploadedSuccessfully() {
        assertEqualsText(successText, "Automation_5.5yrs.pdf");

    }

    public void logout() throws InterruptedException {
        Thread.sleep(6000);
        clickWithJS(profileImg);
        click(logoutButton);
        writeLogInfo("logout Successfully");
    }

    private void addSkill(String skill) throws InterruptedException {
        clickWithJS(editKeySkills);
        addNewSkill.sendKeys(skill);
        getDriver().findElement(By.xpath("//html")).click(); // Confirm skill addition
        clickWithJS(saveBtn);
        validateKeySkillSuccessMsg();
        writeLogInfo("Skill added successfully");
    }

    private void removeExistingSkill() throws InterruptedException {
        clickWithJS(editKeySkills);
        clickWithJS(crossMark);
        clickWithJS(saveBtn);
        writeLogInfo("Skill removed successfully");

    }

    public void updateKeySkills() throws InterruptedException {
        for (int i = 0; i <2; i++) {
            try {
                if (isGitTextIsDisplaying.isDisplayed()) {
                    writeLogInfo("skill is displayed, removing it...");
                    removeExistingSkill();
                }
            } catch (NoSuchElementException e) {
                writeLogInfo("Element not found, proceeding to add  skill.");
            }
            addSkill("GIT");
        }

    }


}
