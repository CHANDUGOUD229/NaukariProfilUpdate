package pages;

import org.apache.commons.math3.analysis.function.Add;
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

    @FindBy(xpath = "//div[@title='GIT']//a[@class='material-icons close'][normalize-space()='Cross']")
    WebElement crossMark;

    @FindBy(xpath = "//a[@href='/mnjuser/profile' and .='View profile']")
    WebElement viewProfile;
    @FindBy(xpath = "//div[@id='lazyKeySkills']//child::span[@class='chip typ-14Medium']")
    List<WebElement> lazyKeySkills;

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

    public void validateKeySkillSuccessMsg() {
        assertEqualsText(keySkillsSaved, "Key Skills have been successfully saved.");
    }

    public void clickOnKeySkillLink() {
        click(viewProfile);
        writeLogInfo("user clicked on the view profile button ");
        click(keySkillsLink);
    }

    public void updateKeySkill() throws InterruptedException {
        for (WebElement element : lazyKeySkills) {
            String actValue = element.getText();
            if (actValue.equals("GIT")) {
                clickWithJS(editKeySkills);
                Thread.sleep(3000);
                clickWithJS(crossMark);
                Thread.sleep(2000);
                clickWithJS(saveBtn);
                writeLogInfo("Skill removed successfully");
            } else {
                clickWithJS(editKeySkills);
                addNewSkill.sendKeys("GIT");
                getDriver().findElement(By.xpath("//html")).click(); // Confirm skill addition
                Thread.sleep(3000);
                clickWithJS(saveBtn);
                validateKeySkillSuccessMsg();
                writeLogInfo("Skill added successfully");
            }
        }
    }

    public void logout() throws InterruptedException {
        Thread.sleep(6000);
        clickWithJS(profileImg);
        click(logoutButton);
        writeLogInfo("logout Successfully");
    }

}
