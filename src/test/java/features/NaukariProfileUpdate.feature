Feature: update Naukari profile


  Scenario: Login to Naukri and update profile
    Given User is on Naukri login page
    When User enters valid credentials and click on login
    And User navigates to profile section and click on key Skills
    And User uploads a new resume
    And User updates key skills
    Then user logout

