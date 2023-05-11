package definitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sei.smartrx.SmartRxApplication;
import com.sei.smartrx.controller.UserController;
import com.sei.smartrx.models.User;
import com.sei.smartrx.service.PrescriptionService;
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
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SmartRxApplication.class)
public class SpringBootCucumberTestDefinitions {

    private static final String BASE_URL = "http://localhost:";

    @LocalServerPort
    String port;

    private static Response response;

    LocalDate localDate = LocalDate.of(2023, 8, 1);
    LocalDate currentDate = localDate.now();
    User user = new User("John", "Carter", "Carter53@hotmail.com", currentDate, "sei1900", "aspirin, metformin");

    public String getYourKey() throws JSONException {
      RequestSpecification request = RestAssured.given();
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("email", "email@email.com");
      jsonObject.put("password", "password");
      request.header("Content-Type", "application/json");
      response = request.body(jsonObject.toString()).post(BASE_URL + port + "/api/auth/users/login");
      return response.jsonPath().getString("message");
    }

    /**
     * FEATURE 1
     * @throws JSONException
     */
    @Given("user is registered")
    public void userIsRegistered() throws JSONException {
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", "email@email.com");
        requestBody.put("password", "password");
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).post(BASE_URL + port +"/api/users/register");
    }

    @When("I enter my username and password")
    public void iEnterMyUsernameAndPassword() {
        JsonPath jsonObject = new JsonPath(response.asString());
        System.out.println(response.getBody().asString());
        Assert.assertEquals(user.getEmail(), jsonObject.get("email"));
        Assert.assertEquals(user.getPassword(), jsonObject.get("password"));
    }

    @Then("I should be logged in successfully")
    public void iShouldBeLoggedInSuccessfully() {
        Assert.assertEquals(200, response.getStatusCode());
    }

    /**
     * FEATURE: a user can view their prescriptions
     */
    @Given("a user has a list of prescriptions")
    public void aUserHasAListOfPrescriptions() throws JSONException{
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer "+ getYourKey());
        response = request.get(BASE_URL+ port + "/api/prescriptions");
//        int userId = JsonPath.from(String.valueOf(jsonResponse.getBody())).get("user");
//        Assert.assertEquals(1, userId);
    }

    @Given("User is logged in")
    public void userHasAnActiveAccount() throws JSONException{
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "email@email.com");
        jsonObject.put("password", "password");
        request.header("Content-Type", "application/json");
        response = request.body(jsonObject.toString()).post(BASE_URL+ port + "/api/auth/users/login");
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.body());
    }

    @When("user updates their account information")
    public void userUpdatesTheirAccountInformation() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("firstName", "Tim");
        requestBody.put("lastName", "Rodriguez");
        requestBody.put("email", "Tim@yahoo.com");
        requestBody.put("dob", "1983-03-06");
        requestBody.put("allergies", "apples");
        requestBody.put("password", "tim123");
        request.header("Content-Type", "application/json");
        String token = getYourKey();
        request.header("Authorization", "Bearer "+ getYourKey());
        response = request.body(requestBody.toString()).put(BASE_URL + port + "/api/users/1");
    }

    @Then("user information will be updated")
    public void userInformationWillBeUpdated() {
        Assert.assertEquals(200, response.getStatusCode());
    }



//    #Put
    @When("user updates allergy information")
    public void userUpdatesAllergyInformation() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("allergy", "watermelon");
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).put(BASE_URL + port + "/api/users/1");
    }

    @Then("the allergy information will be updated")
    public void theAllergyInformationWillBeUpdated() {
        Assert.assertEquals(200, response.getStatusCode());
    }


// #Delete
    @When("user removes their account by ID")
    public void userRemovesTheirAccountByID() {
        try {
            ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL + port + "/api/users/1",
                    HttpMethod.DELETE, null, String.class);
            Assert.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT); //status code 204, no content shown when account is deleted
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    @Then("the account is deleted")
    public void theAccountIsDeleted() {
        try {
            // Send a GET request to the API to retrieve the user by ID and verify that the response status is 404 Not Found
            ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL + port + "/api/users/1",
                    HttpMethod.GET, null, String.class);
            Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);  //404 status, account has been deleted
        } catch (HttpClientErrorException e) {
            Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND); //verifying that the status code is 404
        }
    }


//    @Given("user is registered")
//    public void user_is_registered() {
//
//        User user = new User("user5", "password"); //create user object w/password
//        userService.registerUser(user);
//    }
//
//    @When("I enter my username and password")
//    public void enter_username_password() {
//
//        String username = "user5";
//        String password = "password";
//
//    }
//
//    @Then("I should be logged in successfully")
//    public void should_be_logged_in_successfully() {
//        ResponseEntity<User> response = restTemplate.getForEntity(BASE_URL + port + "/api/user", User.class); //verify if user is logged in
//        User loggedInUser = response.getBody();
//        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
//        Assert.assertEquals("user5", loggedInUser.getUsername());  //may need to add exception handling w/ msg later
//    }

    @Given("A specific medication ID")
    public void aSpecificMedicationID() {
        Long medicationId = 1L;
        Assert.assertTrue(medicationId == 1L);
    }

    @When("a user searches for medication by ID")
    public void aUserSearchesForMedicationByID() throws JSONException {
        try{
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            request.header("Authorization", "Bearer "+ getYourKey());
            response = request.get(BASE_URL + port + "/api/prescriptions/medications/1");
            Assert.assertEquals(200, response.getStatusCode());
        } catch(HttpClientErrorException e){
            e.printStackTrace();
        }
    }

    @Then("user should receive specific information about that medication")
    public void userShouldReceiveSpecificInformationAboutThatMedication() {
        Assert.assertNotNull(response);
    }


    @Given("a user has a unique email")
    public void aUserHasAUniqueEmail() {
        try {
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            JSONObject requestBody = new JSONObject();
            requestBody.put("email", "email@email.com");
            requestBody.put("password", "password");
            request.header("Content-Type", "application/json");
            response = request.body(requestBody.toString()).post(BASE_URL + port + "/api/users/login");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @When("they enter their email and password")
    public void theyEnterTheirEmailAndPassword() {
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Then("the password and their information is stored in database")
    public void thePasswordAndTheirInformationIsStoredInDatabase() {
        Assert.assertNotNull(response.getBody());

    }
}
