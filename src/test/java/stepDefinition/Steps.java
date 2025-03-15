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

    @Given("The user is on the Naukri login page")
    public void The_user_is_on_the_Naukri_login_page() {

    }

    @When("The user logs in with valid credentials")
    public void The_user_logs_in_with_valid_credentials() {
        np = new NaukariProfileUpdatePage();
        np.enterLoginDetails();
    }

    @When("The user should be redirected to the homepage")
    public void The_user_should_be_redirected_to_the_homepage() {
        np.clickOnKeySkillLink();
    }

    @And("The user deletes the existing resume")
    public void The_user_deletes_the_existing_resume() {
        np.deleteResumeImg();
    }

    @And("The user should see a confirmation message for successful deletion")
    public void The_user_should_see_a_confirmation_message_for_successful_deletion() {
        np.validateDeletedSuccessfully();
    }


    @When("The user updates the key skills")
    public void The_user_updates_the_key_skills() throws InterruptedException {
        np.updateKeySkills();
    }

    @When("The user should see a confirmation that the key skills have been updated")
    public void The_user_should_see_a_confirmation_that_the_key_skills_have_been_updated() throws InterruptedException {
        np.validateKeySkillSuccessMsg();
    }

    @When("The user uploads a new resume")
    public void The_user_uploads_a_new_resume() throws AWTException, InterruptedException {
        np.fileUpload();
    }

    @When("The user should see a success message confirming the upload")
    public void The_user_should_see_a_success_message_confirming_the_upload() throws AWTException, InterruptedException {
        np.validateResumeUploadedSuccessfully();
    }

    @Then("The user logs out successfully")
    public void The_user_logs_out_successfully() throws InterruptedException {
        np.logout();
    }


}
