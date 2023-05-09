Feature: Rest API functionalities

  Scenario: view prescription history
    #Given I am logged in
    Given A list of expired and active prescriptions
    When a user searches for their prescription history
    Then a user should see a list of active and inactive prescriptions



# As a user, I want to view my past and present prescriptions, so that I can have all of my medication history in one place.

  #As a user, I want to be able to create a refill request for my prescription by providing valid prescription status and refill, so that I can take medications based on my current health status. POST/CREATE