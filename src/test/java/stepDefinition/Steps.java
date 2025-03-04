package stepDefinition;

import com.github.javafaker.Faker;
import io.cucumber.java.en.*;
import pages.*;
import utility.DriverFactory;
import utility.GenericMethods;

import java.awt.*;


public class Steps extends DriverFactory {
    NaukariProfileUpdatePage np;
    GenericMethods gm = new GenericMethods();

    @Given("User is on Naukri login page")
    public void user_is_on_Naukri_login_page() {

    }

    @When("User enters valid credentials and click on login")
    public void user_enters_valid_credentials_and_click_on_login() {
        np = new NaukariProfileUpdatePage();
        np.enterLoginDetails();
    }

    @When("User navigates to profile section and click on key Skills")
    public void user_navigates_to_profile_section_and_click_on_key_Skills() {
        np.clickOnKeySkillLink();
    }

    @When("User updates key skills")
    public void user_updates_key_skills() throws InterruptedException {
        np.updateKeySkills();
    }

    @When("User uploads a new resume")
    public void user_uploads_a_new_resume() throws AWTException, InterruptedException {
        np.fileUpload();
    }

    @Then("user logout")
    public void user_logout() throws InterruptedException {
        np.logout();
    }


}
