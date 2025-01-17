import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetRequest implements ApiRequest
{
    private String url;
    public GetRequest(String url)
    {
        this.url=url;
    }
    @Step
    @Override
    public Response sendRequest() {
        return (Response) RestAssured
                .given()
                .when()
                .get(url)
                .then()
                .log().all()
                .extract().body();
    }
}
