package definitions;

import com.sei.smartrx.SmartRxApplication;
import com.sei.smartrx.models.Prescription;
import com.sei.smartrx.repository.PrescriptionRepository;
import com.sei.smartrx.service.PrescriptionService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SmartRxApplication.class)
public class PharmacistTest {

    private static final String BASE_URL = "http://localhost:";

    @LocalServerPort
    String port;

    @Autowired
    private PrescriptionService prescriptionService;
private Prescription prescriptionToDelete;
private Prescription prescriptionToAdd;
    private PrescriptionRepository prescriptionRepository;
    @Before
    public void setup() {
        // Initialize the prescriptionService instance
        prescriptionService = new PrescriptionService(prescriptionRepository);
        prescriptionToDelete = new Prescription();
        prescriptionToAdd = new Prescription();

    }

    @Given("a User had authorization role of pharmacist")
    public void aUserHadAuthorizationRoleOfPharmacist() {
    }

//    @Given("a prescription exists")
//    public void aPrescriptionExists() {
//        // Add a prescription to the database or use a test helper method to create a prescription
//        prescriptionToAdd = prescriptionService.addPrescription(new Prescription());
//    }

    @When("the pharmacist deletes a prescription")
    public void thePharmacistDeletesAPrescription() {

        prescriptionService.deletePrescription(prescriptionToAdd.getPrescriptionId());


    }

    @Then("the prescription is deleted")
    public void thePrescriptionIsDeleted() {
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        Assert.assertFalse(prescriptions.contains(prescriptionToAdd));


    }


}
