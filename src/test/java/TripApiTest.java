
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TripApiTest extends BaseApiTest {

    @Test
    public void testGetAllTrips() {
        given()
                .spec(requestSpec)
                .when()
                .get("/trips")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    public void testCreateTrip() {
        String newTripJson = """
        {
            "name": "Mountain Hike",
            "price": 499.99
        }
        """;

        given()
                .spec(requestSpec)
                .body(newTripJson)
                .when()
                .post("/trips")
                .then()
                .statusCode(201)
                .body("id", greaterThan(0))
                .body("name", equalTo("Mountain Hike"))
                .body("price", equalTo(499.99f));
    }

    @Test
    public void testGetTripById() {
        int tripId = 1; // Erstat med en gyldig ID fra din database

        given()
                .spec(requestSpec)
                .when()
                .get("/trips/" + tripId)
                .then()
                .statusCode(200)
                .body("id", equalTo(tripId));
    }

    @Test
    public void testUpdateTrip() {
        int tripId = 1; // Erstat med en gyldig ID

        String updatedTripJson = """
        {
            "id": 1,
            "name": "Updated Hike",
            "price": 599.99
        }
        """;

        given()
                .spec(requestSpec)
                .body(updatedTripJson)
                .when()
                .put("/trips/" + tripId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated Hike"))
                .body("price", equalTo(599.99f));
    }

    @Test
    public void testDeleteTrip() {
        int tripId = 2; // Erstat med en gyldig ID

        given()
                .spec(requestSpec)
                .when()
                .delete("/trips/" + tripId)
                .then()
                .statusCode(204); // No Content efter sletning
    }
}
