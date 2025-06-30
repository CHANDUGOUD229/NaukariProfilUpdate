package stepDefinition;

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






    @When("The user updates the key skills")
    public void The_user_updates_the_key_skills() throws InterruptedException {
        np.updateKeySkill();
    }

    @When("The user should see a confirmation that the key skills have been updated")
    public void The_user_should_see_a_confirmation_that_the_key_skills_have_been_updated() throws InterruptedException {
        np.validateKeySkillSuccessMsg();
    }



    @Then("The user logs out successfully")
    public void The_user_logs_out_successfully() throws InterruptedException {
        np.logout();
    }


}
