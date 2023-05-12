#  GET Pharmacist
Feature: If user has pharmacist role, can create, read, update, and delete prescriptions.
  Scenario:
    Given a User had authorization role of pharmacist
    When a pharmacist searches for a list of prescriptions
    Then a pharmacist should see a list of prescriptions
    When a pharmacist creates a prescription for a user
    Then then prescription is added
    When a pharmacist updates a prescription
    Then the prescription is updated
    When a pharmacist deletes a prescription
    Then the prescription is deleted
