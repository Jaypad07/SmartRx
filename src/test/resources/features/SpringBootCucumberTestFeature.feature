# Post
Feature:  Secure Successful Login
  Scenario:
    Given that I am registered
    When I enter my username and password
    Then I should be logged in successfully


  #GET
Feature: GetAllPrescriptions
  Scenario:
    Given A list of prescriptions is available
    When a user searches for their prescription history
    Then a user should see a list of active and inactive prescriptions

  #GET
  Feature: Get Prescription ID
  Scenario:
    Given A specific user Id
    When user enters their medication ID number
    Then user should receive specific information about that medication

  #Post
  Feature: Create a prescription refill request
  Scenario:
    Given A valid prescription status
    When user submits a prescription refill
    Then the prescription is filled

  #Put
  Feature: Update account by ID
  Scenario:
    Given User has an active account
    When user updates their account information by id
    Then user information will be updated

  #Put
  Feature: Update allergy information
  Scenario:
    Given User has an active account
    When user updates allergy information
    Then then the allergy information will be updated

  #Delete
  Feature: Delete account by ID
    Scenario:
    Given User has an active account
    When user removes their account by ID
    Then the account is deleted






