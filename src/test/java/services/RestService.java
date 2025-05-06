package services;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;

public abstract class RestService {
    private static final String BASE_URl = "http://localhost:8080/";
    protected RequestSpecification REQ_SPEC;

    protected abstract String getBasePath();

    public RestService() {
        REQ_SPEC = new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .setBaseUri(BASE_URl)
                .setBasePath(getBasePath())
                .setContentType(ContentType.JSON)
               // .setAccept(ContentType.ANY)
                .build();
    }
}
