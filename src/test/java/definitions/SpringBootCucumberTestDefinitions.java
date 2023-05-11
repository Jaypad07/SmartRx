package definitions;

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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SmartRxApplication.class)
public class SpringBootCucumberTestDefinitions {

    private static final String BASE_URL = "http://localhost:";

    @LocalServerPort
    String port;

    private static Response response;

    LocalDate localDate = LocalDate.of(2023, 8, 1);
    LocalDate currentDate = localDate.now();
    User user = new User(1L, "John", "Carter", "Carter53@hotmail.com", currentDate, "sei1900", "aspirin, metformin");

    /**
     * FEATURE 1
     * @throws JSONException
     */
    @Given("user is registered")
    public void userIsRegistered() throws JSONException {
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", user.getEmail());
        requestBody.put("password", user.getPassword());
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
     * FEATURE 2
     */

    @Given("a user has a list of prescriptions")
    public void aUserHasAListOfPrescriptions() {
        Long userId = 1L;
        try {
            ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL + port + "/api/prescriptions/1", HttpMethod.GET, null, String.class);
            List<Map<String, String>> prescriptions = JsonPath.from(String.valueOf(response.getBody())).get("data");
            System.out.println(prescriptions);
            Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
            Assert.assertTrue(prescriptions.size() > 0);
        }catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }


    @Given("User has an active account")
    public void userHasAnActiveAccount(){
        try {
            ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL + port + "/api/users/1",
                    HttpMethod.GET, null, String.class);   //checking to see if an account exists
            Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
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
            Assert.assertEquals(e.getStatusCode(), HttpStatus.NO_CONTENT); //verifying that the status code is 404
        }
    }
}
