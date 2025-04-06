import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApiTest {

    protected static RequestSpecification requestSpec;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:7070/api"; // Justér efter behov
        requestSpec = RestAssured.given()
                .contentType("application/json")
                .accept("application/json");
    }
}
