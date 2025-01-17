import io.qameta.allure.Step;
import io.restassured.response.Response;

public interface ApiRequest
{

    Response sendRequest();
}
