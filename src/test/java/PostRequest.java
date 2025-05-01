import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.data.Food;

public class PostRequest implements ApiRequest
{
    private String url;
    private Food food;
    public PostRequest(String url,Food food)
    {
        this.url=url;
        this.food = food;
    }
    @Step
    @Override
    public Response sendRequest() {
        return RestAssured.given()
                .filter(new AllureRestAssured())
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
