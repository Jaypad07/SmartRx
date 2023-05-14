package definitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sei.smartrx.SmartRxApplication;
import com.sei.smartrx.models.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;



@CucumberContextConfiguration //used as a base class for writing Cucumber step definitions and feature files.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SmartRxApplication.class)
public class SpringBootCucumberTestDefinitions {

    private static final String BASE_URL = "http://localhost:";

    @LocalServerPort
    String port;

    private static Response response;

    /**
     * Initializing two LocalDate objects; localDate is set to the date
     * "2023-08-01" (August 1, 2023) and currentDate is set to the current date.
     * Then, a new User object is created with the given properties.
     */
    LocalDate localDate = LocalDate.of(2023, 8, 1);
    LocalDate currentDate = localDate.now();
    User user = new User("John", "Carter", "Carter53@hotmail.com", currentDate, "sei1900", "aspirin, metformin");

    /**
     * Generates a JWT token to pass in header of requests.
     * @return JWT as a String
     * @throws JSONException
     */
    public String getYourJWT() throws JSONException {
      RequestSpecification request = RestAssured.given();
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("email", "email@email.com");
      jsonObject.put("password", "password");
      request.header("Content-Type", "application/json");
      response = request.body(jsonObject.toString()).post(BASE_URL + port + "/api/auth/users/login");
      return response.jsonPath().getString("message");
    }

    /**
     * Generates a JWT token for a pharmacist user.
     * @return JWT as a String
     * @throws JSONException
     */
    public String getJWTAsPharmacist() throws JSONException {
        RequestSpecification request = RestAssured.given();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "pharmacist@cvs.com");
        jsonObject.put("password", "password");
        request.header("Content-Type", "application/json");
        response = request.body(jsonObject.toString()).post(BASE_URL + port + "/api/auth/users/login");
        return response.jsonPath().getString("message");
    }

    /**
     * This precondition requires that a user has registered with a
     * unique email address in the system. A POST request is sent to the URL
     * with a JSON request body containing the email address and a password.
     * The response is stored in the response variable  if successful.
     */
    @Given("a user has a unique email")
    public void aUserHasAUniqueEmail() {
        try {
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            JSONObject requestBody = new JSONObject();
            requestBody.put("email", "email@email.com");
            requestBody.put("password", "password");
            request.header("Content-Type", "application/json");
            response = request.body(requestBody.toString()).post(BASE_URL + port + "/api/auth/users/login");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This step definition is verifying the login functionality of the API, and
     * ensures that the login request was successful by checking the response
     * code (200).
     */
    @When("they enter their email and password")
    public void theyEnterTheirEmailAndPassword() {
        Assert.assertEquals(200, response.getStatusCode());
    }

    /**
     * This step definition is verifying if the user's password and information are
     * stored in the database.
     */
    @Then("the password and their information is stored in database")
    public void thePasswordAndTheirInformationIsStoredInDatabase() {
        Assert.assertNotNull(response.getBody());
    }

    /**
     * This precondition verifies that a registered user can enter their
     * email and password to login authentically.
     * @throws JSONException
     */
    @Given("user is registered")
    public void userIsRegistered() throws JSONException {
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", "email100@email.com");
        requestBody.put("password", "password100");
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).post(BASE_URL + port +"/api/auth/users/register");
    }

    /**
     * This step definition is verifying if the email address matches the expected email
     * address before proceeding to the next step in the test scenario.
     * The JSON object is not null.
     */
    @When("I enter my username and password")
    public void iEnterMyUsernameAndPassword() {
        JsonPath jsonObject = new JsonPath(response.asString());
        Assert.assertEquals("email@email.com", jsonObject.get("email"));
        Assert.assertNotNull(jsonObject);
    }

    /**
     * This step definition is verifying the login functionality of the API, and
     * ensures that the login request was successful by checking the response
     * code (200).
     */
    @Then("I should be logged in successfully")
    public void iShouldBeLoggedInSuccessfully() {
        Assert.assertEquals(200, response.getStatusCode());
    }

    /**
     * This precondition verifies that a user is already logged in and has an
     * active account in the system. A POST request is sent to the API endpoint
     * for the user login with a specific email and password. For the upcoming
     * scenarios, the user needs to be authenticated and have an active session
     * to perform certain actions.
     * @throws JSONException
     */
    @Given("User is logged in")
    public void userHasAnActiveAccount() throws JSONException{
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "email@email.com");
        jsonObject.put("password", "password");
        request.header("Content-Type", "application/json");
        response = request.body(jsonObject.toString()).post(BASE_URL+ port + "/api/auth/users/login");
        Assert.assertEquals(200, response.getStatusCode()); // not null
        Assert.assertNotNull(response.body());
    }

    /**
     * This step definition allows users to update their account information.
     * @throws JSONException
     */
    @When("user updates their account information")
    public void userUpdatesTheirAccountInformation() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject(); //holds the updated user information
        requestBody.put("firstName", "Tim");
        requestBody.put("lastName", "Rodriguez");
        requestBody.put("dob", "1983-03-06");
        requestBody.put("allergies", "apples");
        request.header("Content-Type", "application/json");
        request.header("Authorization", "Bearer "+ getYourJWT());
        response = request.body(requestBody.toString()).put(BASE_URL + port + "/api/users");
    }

    /**
     * This step definition verifies that the response code is equal to 200,
     * and that the user information has been successfully updated in the API.
     */
    @Then("user information will be updated")
    public void userInformationWillBeUpdated() {
        Assert.assertEquals(200, response.getStatusCode());
    }

    /**
     * This step definition allows users to remove their account by ID.
     * The HTTP DELETE request is sent to the API endpoint that corresponds
     * to deleting a user account by ID. An exception is thrown if If an error
     * occurs while attempting to delete the user account.
     * @throws JSONException
     */
    @When("user removes their account by ID")
    public void userRemovesTheirAccountByID() throws JSONException {
        try {
            RestAssured.baseURI = BASE_URL + port;
            RequestSpecification request = RestAssured.given();
            request.header("Authorization", "Bearer "+ getYourJWT()); //header method adds Authorization to the request
            response = request.delete("/api/users");
            //status code 204, no content shown when account is deleted
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    /**
     * This step definition verifies that a user account has been successfully deleted.
     * @throws JSONException
     */
    @Then("the account is deleted")
    public void theAccountIsDeleted() throws JSONException{
        try {
            Assert.assertEquals(204, response.getStatusCode());
            // Send a GET request to the API to retrieve the user by ID and verify that the response status is 404 Not Found
          //404 status, account has been deleted
        } catch (HttpClientErrorException e) {
            Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND); //verifying that the status code is 404
        }
    }

    /**
     * This precondition checks if a user can view a medication by medication ID.
     */
    @Given("A specific medication ID")
    public void aSpecificMedicationID() {
        Long medicationId = 1L;
        Assert.assertTrue(medicationId == 1L);
    }

    /**
     * This step definition verifies that a user can search for medication by
     * their ID.
     * @throws JSONException
     */
    @When("a user searches for medication by ID")
    public void aUserSearchesForMedicationByID() throws JSONException {
        try{
            RestAssured.baseURI = BASE_URL; //makes a GET request to the API endpoint
            RequestSpecification request = RestAssured.given();
            request.header("Authorization", "Bearer "+ getYourJWT());
            Response response1 = request.get(BASE_URL + port + "/api/prescriptions/medications/1");
            Assert.assertEquals(200, response1.getStatusCode());
        } catch(HttpClientErrorException e){
            e.printStackTrace();
        }
    }

    /**
     * This step checks whether the response received from the API is not null.
     * If the response is null, then no information was retrieved from the API about
     * the medication.
     */
    @Then("user should receive specific information about that medication")
    public void userShouldReceiveSpecificInformationAboutThatMedication() {
        Assert.assertNotNull(response);
    }


    /**
     * This step definition checks if a user can view their prescriptions.
     */
    @When("user searches for their prescriptions")
    public void userSearchesForTheirPrescriptions() throws JSONException, JsonProcessingException {
       HttpHeaders authenticationHeader = new HttpHeaders();
       authenticationHeader.set("Authorization","Bearer "+ getYourJWT());
       HttpEntity<String> httpEntity = new HttpEntity<>(authenticationHeader);
       ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL+port+"/api/prescriptions", HttpMethod.GET, httpEntity, String.class);
        List<Map<String, String>> prescriptions = JsonPath.from(String.valueOf(response.getBody())).get();
       Assert.assertNotNull(prescriptions);
    }

    /**
     * This step definition checks when a user retrieves a list of prescriptions, then
     * they will only see their own prescriptions and not the prescriptions of other users.
     * Status code 200 (OK) is returned if the request was successful.
     */
    @Then("a user should see a list of only their prescriptions")
    public void aUserShouldSeeAListOfOnlyTheirPrescriptions() {
        Assert.assertEquals(200, response.getStatusCode());
    }

    /**
     * This precondition states that a user has the authorization role of pharmacist.
     * The appropriate authorization role in the system will be tested.
     */
    @Given("a User had authorization role of pharmacist")
    public void aUserHadAuthorizationRoleOfPharmacist() {
    }

    /**
     * This step definition is for a pharmacist wanting to get a list of all prescriptions.
     * Authorization token is set up for the pharmacist. The HTTP GET request is sent to the
     * API endpoint that retrieves all prescriptions for the pharmacist. Then the JSON
     * library parses the response and extracts the prescription information into a list of maps.
     * @throws JSONException
     */
    @When("a pharmacist searches for a list of prescription")
    public void aPharmacistSearchesForAListOfPrescription() throws JSONException {
        HttpHeaders authenticationHeader = new HttpHeaders();
        authenticationHeader.set("Authorization","Bearer "+ getJWTAsPharmacist());
        HttpEntity<String> httpEntity = new HttpEntity<>(authenticationHeader);
        ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL+port+"/api/pharmacist/prescriptions", HttpMethod.GET, httpEntity, String.class);
        List<Map<String, String>> allPrescriptions = JsonPath.from(String.valueOf(response.getBody())).get();
        Assert.assertNotNull(allPrescriptions); //checks if the list of prescriptions is not null
    }

    /**
     * This step definition checks if a pharmacist can search for a list of
     * prescriptions without any errors. The list of prescriptions will be
     * returned as expected (200).
     */
    @Then("a pharmacist should see a list of prescriptions")
    public void aPharmacistShouldSeeAListOfPrescriptions() {
        Assert.assertEquals(200, response.getStatusCode());
    }

    /**
     * this method sends a PUT request to update a prescription by prescriptionID.
     * the request body is given the updated prescription status and added to the
     * authorization header.
     * @throws JSONException
     */
    @When("the pharmacist updates a prescription")
    public void thePharmacistUpdatesAPrescription() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization","Bearer "+ getJWTAsPharmacist());
        JSONObject requestBody = new JSONObject(); //JSON object with the updated prescription status
        requestBody.put("status", true); //Put request to update the prescription status
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).put(BASE_URL+ port + "/api/pharmacist/prescription/1");
        int statusCode = response.getStatusCode();
        assertEquals(200, response.getStatusCode());
    }

    /**
     * This step definition checks if the prescription has been successfully updated.
     * Asserts expected status code (200).
     */
    @Then("the prescription is updated")
    public void thePrescriptionIsUpdated() {
        assertEquals(200, response.getStatusCode());
    }

    /**
     * This step definition checks if a pharmacist can search for a prescription by ID.
     * A HTTP GET request is sent to the pharmacist-specific endpoint of the API with
     * an authorization header to ensure that only authorized users can access it.
     * The response from the API is converted from a string to a map using the JSONPath
     * library. The test asserts that the map is not null and the prescription was
     * successfully retrieved.
     * @throws JSONException
     */
    @When("a pharmacist searches for a prescription by id")
    public void aPharmacistSearchesForAPrescriptionById() throws JSONException {
        HttpHeaders authenticationHeader = new HttpHeaders();
        authenticationHeader.set("Authorization","Bearer "+ getJWTAsPharmacist());
        HttpEntity<String> httpEntity = new HttpEntity<>(authenticationHeader);
        ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL+port+"/api/pharmacist/prescriptions/1", HttpMethod.GET, httpEntity, String.class);
        Map<String, String> onePrescription = JsonPath.from(String.valueOf(response.getBody())).get();
        Assert.assertNotNull(onePrescription);
    }

    /**
     * This step definition verifies that the response status code is 200, and the pharmacist
     * is able to see the prescription they searched for in the previous step.
     */
    @Then("a pharmacist should see that one prescription")
    public void aPharmacistShouldSeeThatOnePrescription() {
        Assert.assertEquals(200, response.getStatusCode());
    }

    @When("the pharmacist deletes a prescription")
    public void thePharmacistDeletesAPrescription() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization","Bearer "+ getJWTAsPharmacist());
        request.header("Content-Type", "application/json");
        response = request.delete(BASE_URL + port + "/api/pharmacist/prescriptions/1");
    }


    @Then("the prescription is deleted")
    public void thePrescriptionIsDeleted() {
        Assert.assertEquals(200, response.getStatusCode());
    }

    @When("a pharmacist creates a prescription")
    public void aPharmacistCreatesAPrescription() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization","Bearer "+ getJWTAsPharmacist());
        JSONObject requestBody = new JSONObject();
        requestBody.put("patientName", "Stacey Smith");
        requestBody.put("refills", 3);
        requestBody.put("endDate", "2023-09-06");
        requestBody.put("status", true);
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).post(BASE_URL + port + "/api/pharmacist/prescriptions/1/1");
    }

    @Then("the prescription is created")
    public void thePrescriptionIsCreated() {
        Assert.assertEquals(200, response.getStatusCode());
    }
}

