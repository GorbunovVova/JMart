package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import stepDefs.BaseStepDefs;

import java.util.Properties;

public class ApiSteps {

    private Properties props = BaseStepDefs.getProperties();
    private String basic_auth_username = props.getProperty("basic_auth_username");
    private String name = props.getProperty("name");
    private String surname = props.getProperty("surname");
    private String password = props.getProperty("player_password");
    private String token;
    private String userName;
    private String email;
    private int id;


    @Step("получается токен гостя")
    public void gettingGuestToken() {

        String token_body = props.getProperty("token_body");

        Response response = RestAssured
                .given()
                .filter(new AllureRestAssured())
                .auth()
                .preemptive()
                .basic(basic_auth_username, "")
                .header("Content-Type", "application/json")
                .body(token_body)
                .when()
                .post("/v2/oauth2/token");

        checkTokenRequest(response);
    }

    @Step("выполняется регистрация игрока")
    public void signUpPlayer() {

        String singUpBody = getSignUpBody();

        Response response = RestAssured
                .given()
                .filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(singUpBody)
                .when()
                .post("/v2/players");

        checkPlayerRequest(response, 201);

    }

    @Step("авторизация под созданным игроком")
    public void auth() {

        String authBody = getAuthBody();

        Response response = RestAssured
                .given()
                .filter(new AllureRestAssured())
                .auth()
                .preemptive()
                .basic(basic_auth_username, "")
                .header("Content-Type", "application/json")
                .body(authBody)
                .when()
                .post("/v2/oauth2/token");

        checkTokenRequest(response);

    }

    @Step("запрашиваются данные игрока")
    public void getPlayer(String negation) {
        int playerId;
        int responseCode;
        if (negation.equals("не")) {
            playerId = Integer.MAX_VALUE;
            responseCode = 404;
        } else {
            playerId = id;
            responseCode = 200;
        }
        Response response = RestAssured
                .given()
                .filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/v2/players/" + playerId);

        checkPlayerRequest(response, responseCode);
    }

    private void checkTokenRequest(Response response) {

        try {
            token = new ObjectMapper().readTree(response.body().asString()).get("access_token").asText();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotEquals("null", token);
    }

    private void checkPlayerRequest(Response response, int responseCode) {

        JsonNode jsonNode = null;
        try {
            jsonNode = new ObjectMapper().readTree(response.body().asString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Assert.fail("не удалось получить тело ответа");
        }

        Assert.assertEquals(responseCode, response.getStatusCode());

        if (responseCode<=201) {
            Assert.assertNotEquals("null", jsonNode.get("id").asText());
            Assert.assertEquals("null", jsonNode.get("country_id").asText());
            Assert.assertEquals("null", jsonNode.get("timezone_id").asText());
            Assert.assertEquals(userName, jsonNode.get("username").asText());
            Assert.assertEquals(email, jsonNode.get("email").asText());
            Assert.assertEquals(name, jsonNode.get("name").asText());
            Assert.assertEquals(surname, jsonNode.get("surname").asText());
            Assert.assertEquals("null", jsonNode.get("gender").asText());
            Assert.assertEquals("null", jsonNode.get("phone_number").asText());
            Assert.assertEquals("null", jsonNode.get("birthdate").asText());
            Assert.assertTrue(jsonNode.get("bonuses_allowed").asBoolean());
            Assert.assertFalse(jsonNode.get("is_verified").asBoolean());

            id = jsonNode.get("id").asInt();
        }
    }

    private String getSignUpBody() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode signUpJson = objectMapper.createObjectNode();
        long rand = System.currentTimeMillis();
        userName = "User" + rand;
        email = rand + "@example.com";
        signUpJson.put("username", userName);
        signUpJson.put("password_change", password);
        signUpJson.put("password_repeat", password);
        signUpJson.put("email", email);
        signUpJson.put("name", name);
        signUpJson.put("surname", surname);
        signUpJson.put("currency", "EUR");
        String singUpBody = null;
        try {
            singUpBody = objectMapper.writeValueAsString(signUpJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return singUpBody;
    }

    private String getAuthBody() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode signUpJson = objectMapper.createObjectNode();
        signUpJson.put("grant_type", "password");
        signUpJson.put("username", userName);
        signUpJson.put("password", password);
        String authBody = null;
        try {
            authBody = objectMapper.writeValueAsString(signUpJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return authBody;
    }
}
