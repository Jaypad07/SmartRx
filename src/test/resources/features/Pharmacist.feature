Feature: If user has pharmacist role, can create, read, update, and delete prescriptions.
  Scenario:
    Given a User had authorization role of pharmacist
    When a pharmacist searches for a list of prescription
    Then a pharmacist should see a list of prescriptions
    When a pharmacist searches for a prescription by id
    Then a pharmacist should see that one prescription
    When a pharmacist creates a prescription
    Then the prescription is created
    When the pharmacist updates a prescription
    Then the prescription is updated
    When the pharmacist deletes a prescription
    Then the prescription is deleted
