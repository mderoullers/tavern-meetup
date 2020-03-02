package tech.ippon;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class FoodResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/foods")
          .then()
             .statusCode(200);
    }

}
