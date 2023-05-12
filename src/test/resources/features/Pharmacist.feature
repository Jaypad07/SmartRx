Feature: If user has pharmacist role, can create, read, update, and delete prescriptions.
  Scenario:
    Given a User had authorization role of pharmacist
    When a pharmacist searches for a list of prescription
    Then a pharmacist should see a list of prescriptions
#    When a pharmacist adds a prescription
#    Then the prescription is added
    When the pharmacist updates a prescription
    Then the prescription is updated
#    When the pharmacist deletes a prescription
#    Then the prescription is deleted
