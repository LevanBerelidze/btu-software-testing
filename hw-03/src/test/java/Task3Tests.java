import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.equalTo;

public class Task3Tests {
    @Test
    public void testOne() {
        given()
            .get("https://bookstore.toolsqa.com/BookStore/v1/Books")
            .then()
            .assertThat()
            .statusCode(equalTo(200))
            .body("books[-1].isbn", equalTo("9781593277574"))
            .body("books[0,1].pages", hasItems(234, 254));
    }
}
