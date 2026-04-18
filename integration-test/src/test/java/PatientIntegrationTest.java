import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class PatientIntegrationTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4044";
    }

    @Test
    public void shouldReturnPatientsWithValidToken() {
        String loginPayload = """
                {
                    "email": "user@test.com",
                    "password": "shoaib"
                }
                """;

        String token = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/patients")
                .then()
                .statusCode(200)
                .body("patients", notNullValue());
    }

    @Test
    public void shouldReturn401WhenTokenIsInvalid(){
        String loginPayload = """
                {
                    "email": "user@test.com",
                    "password": "shoaib"
                }
                """;

        String token = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token");

        String invalidToken = token + "d";

        given()
                .header("Authorization","Bearer " + invalidToken)
                .when()
                .get("/api/patients")
                .then()
                .statusCode(401);

    }

    @Test
    public void shouldReturn401WhenTokenIsMissing(){
        String loginPayload = """
                {
                    "email": "user@test.com",
                    "password": "shoaib"
                }
                """;

        String invalidToken = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token");

        given()
                .when()
                .get("/api/patients")
                .then()
                .statusCode(401);
    }
}
