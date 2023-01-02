import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

@Epic("Regression Tests")
@Feature("Registration")
public class ApiTests {
    @Test
    @Story("Calling the API without passing parameters")
    public void createUserTest_ReturnsBadRequest_WhenParamsAreNotPassed() {
        given()
            .body("{}")
            .post("https://bookstore.toolsqa.com/Account/v1/User")
            .then()
            .assertThat()
            .statusCode(equalTo(400))
            .body("code", equalTo("1200"))
            .body("message", equalTo("UserName and Password required."));
    }

    @Test
    @Story("Passing a weak password")
    public void createUserTest_ReturnsBadRequest_WhenPasswordIsNotStrongEnough() {
        given()
            .body("""
                {
                "userName": "user-one",
                "password": "1"
                }
                """)
            .contentType("application/json")
            .post("https://bookstore.toolsqa.com/Account/v1/User")
            .then()
            .assertThat()
            .statusCode(equalTo(400))
            .body("code", equalTo("1300"))
            .body("message", equalTo("Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer."));
    }

    @Test
    @Story("Passing valid arguments")
    public void createUserTest_ReturnsCreated_WhenArgumentsAreValid() {
        String randomUserName = generateRandomUserName();
        String body = String.format("""
                {
                "userName": "%s",
                "password": "str0ng-P@ssword"
                }
                """,
            randomUserName);

        given()
            .body(body)
            .contentType("application/json")
            .post("https://bookstore.toolsqa.com/Account/v1/User")
            .then()
            .assertThat()
            .statusCode(equalTo(201))
            .body("username", equalTo(randomUserName));
    }

    private static String generateRandomUserName() {
        Random r = new Random();
        StringBuilder builder = new StringBuilder();
        while (builder.length() < 10) {
            builder.append(Integer.toHexString(r.nextInt()));
        }

        return builder.toString();
    }
}
