Feature: User able to register, login, update their information including their allergies as well as delete their account
  Scenario:
  Given User is logged in
    When user searches for their prescriptions
    Then a user should see a list of only their prescriptions
    When a user searches for medication by ID
    Then user should receive specific information about that medication
    When user updates their account information
    Then user information will be updated
    When user removes their account by ID
    Then the account is deleted




