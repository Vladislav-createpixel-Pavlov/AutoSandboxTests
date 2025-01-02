import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Food;

public class PostRequest implements ApiRequest
{
    private String url;
    private Food food;
    public PostRequest(String url,Food food)
    {
        this.url=url;
        this.food = food;
    }

    @Override
    public Response sendRequest() {
        return RestAssured.given()
                .disableCsrf()
                .baseUri(url)
                .header("Content-type", "application/json")
                .header("accept", "*/*")
                .and()
                .body("{\"name\": " + "\"" + food.name + "\"" + ", \"type\": " + "\"FRUIT\"" + ", \"exotic\": " + true + "}")
                .when()
                .log()
                .all()
                .post("api/food")
                .then()
                .log().all()
                .extract().response();

    }
}
