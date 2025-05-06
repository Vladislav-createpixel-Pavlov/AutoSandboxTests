package services;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojos.GoodsPojo;
import java.util.List;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;

public class FoodService extends RestService{

    private static final String BASE_PATH="/api/food";

    public FoodService() {
        super();
    }

    @Override
    protected String getBasePath() {
        return BASE_PATH;
    }

    @Step("Создание товара с именем {gp.name}")
    public Response createFood(GoodsPojo gp){
        return given().spec(REQ_SPEC).body(gp).post();
    }

//    @Step("Получение списка товаров")
//    //@Attachment()
//    public List<GoodsPojo> getBookings(){
//        return given()
//                .spec(REQ_SPEC)
//                .log().all()
//                .get()
//                .jsonPath().getList("$", GoodsPojo.class);
//    }
    @Step("Получение списка товаров")
   // @Attachment()
    public RestResponse<List<GoodsPojo>> getFoodList(){
        return new RestResponse<>(given()
                .spec(REQ_SPEC)
                .log().all()
                .get(),resp -> resp.body().jsonPath().getList("$", GoodsPojo.class));
    }
}
