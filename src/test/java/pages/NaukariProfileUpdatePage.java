package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utility.GenericMethods;
import java.util.List;

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

    @FindBy(xpath = "//button[@id='saveKeySkills']")
    WebElement saveBtn;

    @FindBy(xpath = "//img[@class='nI-gNb-icon-img']")
    WebElement profileImg;
    @FindBy(xpath = "//a[@class='nI-gNb-list-cta' and .='Logout']")
    WebElement logoutButton;
    @FindBy(xpath = "//div[@title='Automation_5.5yrs.pdf']")
    WebElement successText;

    @FindBy(xpath = "//div[@title='GIT']//a[@class='material-icons close'][normalize-space()='Cross']")
    WebElement crossMark;
    //form/div[2]/div[1]/div/div[1]/div[1]/div[1]/div[13]/a
    @FindBy(xpath = "//*[.='GIT']")
    WebElement isGitTextIsDisplaying;
    @FindBy(xpath = "//a[@href='/mnjuser/profile' and .='View profile']")
    WebElement viewProfile;

    @FindBy(xpath = "//*[.='Key Skills have been successfully saved.']")
    WebElement keySkillsSaved;

    @FindBy(xpath = "//div[@class='widgetHead typ-16Bold']/following-sibling::div/div/span")
    List<WebElement> lazyKeySkills;

    public void enterLoginDetails() {
        writeLogInfo("user landed on login page");
        waitForElementTobeEnterText(email, getProperty("username"));
        writeLogInfo("user entered user name ");
        waitForElementTobeEnterText(passWord, getProperty("password"));
        writeLogInfo("user entered passWord ");
        click(loginBtn);
        writeLogInfo("user clicked on the login button");

    }





    public void clickOnKeySkillLink() {
        click(viewProfile);
        writeLogInfo("user clicked on the view profile button ");
        click(keySkillsLink);

    }
    public void validateKeySkillSuccessMsg() {
        assertEqualsText(keySkillsSaved, "Key Skills have been successfully saved.");
    }


    public void updateKeySkill() throws InterruptedException {
        for(WebElement element:lazyKeySkills){
           String actValue= element.getText();
           if(actValue.equals("GIT")){
               clickWithJS(editKeySkills);
               Thread.sleep(5000);
               clickWithJS(crossMark);
               Thread.sleep(2000);
               clickWithJS(saveBtn);
               writeLogInfo("Skill removed successfully");
           }else{
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



//    private void addSkill(String skill) throws InterruptedException {
//        clickWithJS(editKeySkills);
//        addNewSkill.sendKeys(skill);
//        getDriver().findElement(By.xpath("//html")).click(); // Confirm skill addition
//        Thread.sleep(3000);
//        clickWithJS(saveBtn);
//        validateKeySkillSuccessMsg();
//        writeLogInfo("Skill added successfully");
//    }

//    private void removeExistingSkill() throws InterruptedException {
//        clickWithJS(editKeySkills);
//        Thread.sleep(3000);
//        clickWithJS(crossMark);
//        clickWithJS(saveBtn);
//        writeLogInfo("Skill removed successfully");

//    }

//    public void updateKeySkills() throws InterruptedException {
//        for (int i = 0; i <2; i++) {
//            try {
//                if (isGitTextIsDisplaying.isDisplayed()) {
//                    writeLogInfo("skill is displayed, removing it...");
//                    removeExistingSkill();
//                }
//            } catch (NoSuchElementException e) {
//                writeLogInfo("Element not found, proceeding to add  skill.");
//            }
//            addSkill("GIT");
//        }

//    }

    public void logout() throws InterruptedException {
        Thread.sleep(2000);
        clickWithJS(profileImg);
        click(logoutButton);
        writeLogInfo("logout Successfully");
    }


}
