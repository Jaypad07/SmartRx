package definitions;

import com.sei.smartrx.models.Prescription;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PharmacistTest {
    @Given("a User had authorization role of pharmacist")
    public void aUserHadAuthorizationRoleOfPharmacist() {
    }

    @When("the pharmacist deletes a prescription")
    public void thePharmacistDeletesAPrescription() {
        // Implement the logic to delete a prescription
        Prescription prescriptionToDelete = // Get the prescription to delete
                deletedPrescription = prescriptionService.deletePrescription(prescriptionToDelete);
        
    }

    @Then("the prescription is deleted")
    public void thePrescriptionIsDeleted() {
    }
}
