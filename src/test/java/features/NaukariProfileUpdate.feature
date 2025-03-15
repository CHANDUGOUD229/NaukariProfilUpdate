Feature: Update Naukri Profile

  Scenario: Successfully update Naukri profile with a new resume and key skills
    Given The user is on the Naukri login page
    When  The user logs in with valid credentials
    Then  The user should be redirected to the homepage
    And   The user deletes the existing resume
    Then  The user should see a confirmation message for successful deletion
    When  The user uploads a new resume
    Then  The user should see a success message confirming the upload
    When  The user updates the key skills
    Then  The user should see a confirmation that the key skills have been updated
    And   The user logs out successfully
